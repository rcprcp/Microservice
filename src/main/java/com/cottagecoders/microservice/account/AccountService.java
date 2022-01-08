package com.cottagecoders.microservice.account;

import com.cottagecoders.microservice.Database;

public class AccountService {
  private final Database database = new Database(System.getenv("DATABASE_URL"),
      System.getenv("DATABASE_USERNAME"),
      System.getenv("DATABASE_PASSWORD")
  );

  public AccountService() {
  }

  public AccountResponse getAccountDetails(AccountRequest request) {

    return database.getUserDetails(request.getAccountNumber());
  }
}
