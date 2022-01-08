package com.cottagecoders.microservice.portfolio;

import com.cottagecoders.microservice.Database;
import com.cottagecoders.microservice.HistoricalPrice;
import com.cottagecoders.microservice.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PandLService {
  private static final Logger LOG = LoggerFactory.getLogger(PandLService.class);

  PandLService() {
    // nothing
  }

  Holding checkOrAddToMap(Map<String, Holding> holdings, String symbol) {

    if (!holdings.containsKey(symbol)) {
      holdings.put(symbol, new Holding(symbol));
    }
    return holdings.get(symbol);
  }

  boolean isDepositOrWithdraw(String transactionType) {
    return transactionType.equals(TransactionType.DEPOSIT.name()) || transactionType.equals(TransactionType.WITHDRAW.name());
  }


  PandLResponse calculatePandLInTimeRange(PandLRequest request, List<Transaction> transactions) {
    Map<String, Holding> holdings = new TreeMap<>();

    for (Transaction t : transactions) {
      if (isDepositOrWithdraw(t.getTransactionType())) {
        continue;
      }

      // Is this record after the window? We're done.
      if (t.getTimestamp().after(request.endDate)) {
        break;
      }

      LOG.debug("Transaction:symbol {} shares {} price {} trans {} timestamp {}",
          t.getSymbol(),
          t.getShares(),
          t.getPrice(),
          t.getTransactionType(),
          t.getTimestamp()
      );

      //add or update holding with new transactions here.
      Holding h = checkOrAddToMap(holdings, t.getSymbol());

      // before window start.
      if (t.getTimestamp().before(request.startDate)) {
        if (t.getTransactionType().equals(TransactionType.SELL.name())) {
          h.setStartWindowShares(h.getStartWindowShares() - t.getShares());
        } else {
          h.setStartWindowShares(h.getStartWindowShares() + t.getShares());
        }

        // in window...
      } else {
        if (t.getTransactionType().equals(TransactionType.BUY.name())) {
          h.setRunningShares(h.getRunningShares() + t.getShares());
          h.setRunningTotal(h.getRunningTotal() - t.getPrice() * t.getShares());

        } else {
          double perShare = h.getRunningTotal() + t.getPrice() * t.getShares();
          h.setRealizedPandL(h.getRealizedPandL() + perShare / (h.getRunningShares() * t.getShares()));

          h.setRunningShares(h.getRunningShares() - t.getShares());
          h.setRunningTotal(h.getRunningTotal() + t.getPrice() * t.getShares());
        }
      }
    }

    // no position at this time?
    List<String> toDelete = new ArrayList<>();
    for (Holding h : holdings.values()) {
      if (h.getStartWindowShares() == 0.0D && h.getRunningShares() == 0.0D && h.getRunningTotal() == 0.0D) {
        toDelete.add(h.getSymbol());
      }
    }
    for (String s : toDelete) {
      holdings.remove(s);
    }

    HistoricalPrice hp = new HistoricalPrice();
    PandLResponse response = new PandLResponse(request.getAccountNumber());

    //-- post processing.
    for (Holding h : holdings.values()) {
      h.setStartWindowPrice(hp.getPrice(h.getSymbol(), request.startDate));
      h.setEndWindowPrice(hp.getPrice(h.getSymbol(), request.getEndDate()));

      // Determine realized P and L - sells within the window.  This is a symbol with transactions in the window.
      if(h.getRunningShares() == 0.0d) {
        h.setRealizedPandL(h.getRunningTotal());
      }

      // determine unrealized P and L.
      if(h.getRunningShares() != 0.0D || h.getStartWindowShares() != 0.0D) {
        h.setUnrealizedPandL((h.getEndWindowPrice() * h.getStartWindowShares()) - ((h.getStartWindowPrice() * h.getStartWindowShares())));
        h.setUnrealizedPandL((h.getUnrealizedPandL()) + (h.getEndWindowPrice() * h.getRunningShares()) + h.getRunningTotal());
      }
      response.holdings.add(h);
    }

    return response;
  }

}
