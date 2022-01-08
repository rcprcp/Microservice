package com.cottagecoders.microservice.account;

public class AccountRequest {
  private long accountNumber;

  public AccountRequest(long accountNumber) {
    this.accountNumber = accountNumber;
  }

  public long getAccountNumber() {
    return accountNumber;
  }
}
