package com.cottagecoders.microservice.portfolio;


import java.sql.Timestamp;

public class PandLRequest {
  public long accountNumber;
  public java.sql.Timestamp startDate;
  public java.sql.Timestamp endDate;

  PandLRequest(long accountNumber, Timestamp startDate, Timestamp endDate) {
    this.accountNumber = accountNumber;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public long getAccountNumber() {
    return accountNumber;
  }

  public Timestamp getStartDate() {
    return startDate;
  }

  public Timestamp getEndDate() {
    return endDate;
  }
}
