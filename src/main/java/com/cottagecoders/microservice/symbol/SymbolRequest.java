package com.cottagecoders.microservice.symbol;

public class SymbolRequest {
  public String ticker;
  public double price ;
  public long timeStamp;

  public SymbolRequest() {

  }
  public SymbolRequest(String ticker, double price, long timeStamp) {
    this.ticker = ticker;
    this.price = price;
    this.timeStamp = timeStamp;

  }
}
