package com.cottagecoders.microservice;

import com.cottagecoders.microservice.account.AccountResponse;
import com.cottagecoders.microservice.portfolio.PandLRequest;
import com.cottagecoders.microservice.portfolio.Position;
import com.cottagecoders.microservice.portfolio.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Database implements AutoCloseable {

  private static final String SQLEXCEPTION = "SQLException: {}";
  private static final String TRANSACTIONS_SQL = "SELECT account_number, symbol, price, transaction_type, timestamp, shares FROM transactions WHERE " + "account_number = ? ORDER BY " + "account_number, " + "timestamp";
  private static final String GET_USER_DETAILS_SQL = "SELECT account_number, name, address, city, state, catname, ssn, cellphone, email FROM account_holders " + "WHERE account_number = ?";

  private static final Logger LOG = LoggerFactory.getLogger(Database.class);
  private PreparedStatement transactions;
  private PreparedStatement userDetails;
  private Connection conn;

  public Database(String url, String username, String password) {

    try {
      conn = DriverManager.getConnection(url, username, password);
      transactions = conn.prepareStatement(TRANSACTIONS_SQL);
      userDetails = conn.prepareStatement(GET_USER_DETAILS_SQL);

    } catch (SQLException ex) {
      LOG.error("Error connecting to database {} {}", url, ex.getMessage(), ex);
      System.exit(7);
    }
  }

  public void close() {
    try {
      conn.close();
    } catch (SQLException ex) {
      LOG.error(SQLEXCEPTION, ex.getMessage(), ex);
    }
  }

  /**
   * Get a list of transactions for a given account in chronological order. The list is from the earliest
   * transaction until the end date.
   *
   * @param PandLRequest
   * @return List of transactions in chronological order.
   */
  public List<Transaction> getallTransactions(PandLRequest request) {
    List<Transaction> account_transactions = new ArrayList<>();

    try {
      transactions.setLong(1, request.accountNumber);
    } catch (SQLException ex) {
      LOG.error(SQLEXCEPTION, ex.getMessage());
      System.exit(5);
    }

    try (ResultSet rs = transactions.executeQuery()) {
      while (rs.next()) {
        String symbol = rs.getString("symbol");
        double shares = rs.getDouble("shares");
        double price = rs.getDouble("price");
        String transactionType = rs.getString("transaction_type");
        Timestamp timestamp = rs.getTimestamp("timestamp");

        Transaction t = new Transaction(symbol, shares, price, transactionType, timestamp);
        account_transactions.add(t);
      }

    } catch (SQLException ex) {
      LOG.error(SQLEXCEPTION, ex.getMessage(), ex);
      System.exit(10);
    }
    return account_transactions;
  }

  public Map<String, Position> getPositions(long accountNumber) {
    Map<String, Position> positions = new TreeMap<>();

    try {
      transactions.setLong(1, accountNumber);

      try (ResultSet rs = transactions.executeQuery()) {
        while (rs.next()) {
          String symbol = rs.getString("symbol");
          double shares = rs.getDouble("shares");
          if (rs.getString("transaction_type").equalsIgnoreCase(TransactionType.SELL.name())) {
            shares = -(shares);
          }
          if (positions.containsKey(symbol)) {
            Position p = positions.get(symbol);
            p.setShares(p.getShares() + shares);
            // if the Position size is 0, delete it.
            if (p.getShares() == 0.0D) {
              positions.remove(symbol);
            } else {
              positions.put(symbol, p);
            }
          } else {
            positions.put(symbol, new Position(symbol, rs.getDouble("shares")));
          }
        }
      } catch (SQLException ex) {
        LOG.error("SQLException {}", ex.getMessage(), ex);
        System.exit(9);
      }
    } catch (SQLException ex) {
      LOG.error("SQLException {}", ex.getMessage());
      System.exit(5);
    }

    HistoricalPrice hp = new HistoricalPrice();
    for (Position p : positions.values()) {
      p.setPrice(hp.getPrice(p.getSymbol(), new Timestamp(System.currentTimeMillis())));
    }

    return positions;
  }

  public AccountResponse getUserDetails(long num) {
    AccountResponse response;
    try {
      userDetails.setLong(1, num);

      try (ResultSet rs = userDetails.executeQuery()) {
        while (rs.next()) {
          long accountNumber = rs.getLong("account_number");
          String name = rs.getString("name");
          String address = rs.getString("address");
          String city = rs.getString("city");
          String state = rs.getString("state");
          String catname = rs.getString("catname");
          String ssn = rs.getString("ssn");
          String cellphone = rs.getString("cellphone");
          String email = rs.getString("email");
          response = new AccountResponse(accountNumber, name, address, city, state, catname, ssn, cellphone, email);
          return response;
        }

      } catch (SQLException ex) {
        LOG.error(SQLEXCEPTION, ex.getMessage(), ex);
        System.exit(52);
      }
    } catch (SQLException ex) {
      LOG.error(SQLEXCEPTION, ex.getMessage(), ex);
      System.exit(52);
    }

    return new AccountResponse();
  }
}
