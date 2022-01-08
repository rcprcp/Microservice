package com.cottagecoders.microservice.symbol;

public class Symbol {
  public String ticker;
  public double price;
  public long timeStamp;

  public Symbol(String ticker, double price, long timeStamp) {
    this.ticker = ticker;
    this.price = price;
    this.timeStamp = timeStamp;
  }

}
