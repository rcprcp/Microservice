package com.cottagecoders.microservice.portfolio;

import java.util.ArrayList;
import java.util.List;

public class PandLResponse {
  public long accountNumber;
  public List<Holding> holdings;

  public PandLResponse(long accountNumber) {
    this.accountNumber = accountNumber;
    holdings = new ArrayList<>();
  }

  public long getAccountNumber() {
    return accountNumber;
  }

}
