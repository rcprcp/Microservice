package com.cottagecoders.microservice.portfolio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Position {

  Logger LOG = LoggerFactory.getLogger(Position.class);

  private String symbol;
  private double shares;
  private double price;
  private double value;

  public Position(String symbol, double shares) {
    this.symbol = symbol;
    this.shares = shares;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public void setShares(double shares) {
    this.shares = shares;
    this.value = price * shares;
  }

  public void setPrice(double price) {
    this.price = price;
    this.value = price * shares;
  }

  public double getPrice() {
    return price;
  }
  public String getSymbol() {
    return symbol;
  }

  public double getShares() {
    return shares;
  }

  public double getValue() {
    return value;
  }

  public void setShares(Double shares){
    this.shares = shares;
  }
}
