package com.cottagecoders.microservice.portfolio;

import java.sql.Timestamp;

public class Transaction {
  private String symbol;
  private double shares;
  private double price;
  private String transactionType;
  private Timestamp timestamp;

  public Transaction(String symbol, double shares, double price, String transactionType, Timestamp timestamp) {
    this.symbol = symbol;
    this.shares = shares;
    this.price = price;
    this.transactionType = transactionType;
    this.timestamp = timestamp;

  }

  public String getSymbol() {
    return symbol;
  }

  public double getShares() {
    return shares;
  }

  public double getPrice() {
    return price;
  }

  public String getTransactionType() {
    return transactionType;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }
}
