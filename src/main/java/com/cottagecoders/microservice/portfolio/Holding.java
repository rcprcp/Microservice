package com.cottagecoders.microservice.portfolio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;

public class Holding {
  private static final Logger LOG = LoggerFactory.getLogger(Holding.class);

  private final String symbol;

  private double startWindowPrice;
  private double startWindowShares;

  private double endWindowPrice;
  private double endWindowShares;

  private double runningTotal;
  private double runningShares;

  private double realizedPandL;
  private double unrealizedPandL;

  public Holding(String symbol) {
    this.symbol = symbol;
  }

  public String getSymbol() {
    return symbol;
  }

  public double getStartWindowPrice() {
    return startWindowPrice;
  }

  public void setStartWindowPrice(double startWindowPrice) {
    this.startWindowPrice = startWindowPrice;
  }

  public double getEndWindowPrice() {
    return endWindowPrice;
  }

  public void setEndWindowPrice(double endWindowPrice) {
    this.endWindowPrice = endWindowPrice;
  }

  public double getStartWindowShares() {
    return startWindowShares;
  }

  public void setStartWindowShares(double startWindowShares) {
    this.startWindowShares = startWindowShares;
  }

  public double getEndWindowShares() {
    return endWindowShares;
  }

  public void setEndWindowShares(double endWindowShares) {
    this.endWindowShares = endWindowShares;
  }

  public double getRunningTotal() {
    return runningTotal;
  }

  public void setRunningTotal(double runningTotal) {
    this.runningTotal = runningTotal;
  }

  public double getRunningShares() {
    return runningShares;
  }

  public void setRunningShares(double runningShares) {
    this.runningShares = runningShares;
  }

  public double getRealizedPandL() {
    return realizedPandL;
  }

  public void setRealizedPandL(double realizedPandL) {
    this.realizedPandL = realizedPandL;
  }

  public double getUnrealizedPandL() {
    return unrealizedPandL;
  }

  public void setUnrealizedPandL(double unrealizedPandL) {
    this.unrealizedPandL = unrealizedPandL;
  }
}
