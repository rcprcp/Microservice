package com.cottagecoders.microservice.portfolio;

public class PandL {
  public long accountNumber;
  public java.sql.Date startDate;
  public java.sql.Date endDate;
  public double profitAndLoss;

  PandL(long accountNumber, java.sql.Date startDate, java.sql.Date endDate, double profitAndLoss) {
    this.accountNumber = accountNumber;
    this.startDate = startDate;
    this.endDate = endDate;
    this.profitAndLoss = profitAndLoss;
  }

}
