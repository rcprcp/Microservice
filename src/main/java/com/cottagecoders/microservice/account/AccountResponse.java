package com.cottagecoders.microservice.account;

public class AccountResponse {
  private long accountNumber = -1;
  private String name = "";
  private String address = "";
  private String city = "";
  private String state = "";
  private String catname = "";
  private String ssn = "";
  private String cellphone = "";
  private String email = "";

  public AccountResponse() {
  }

  public AccountResponse(
      long accountNumber,
      String name,
      String address,
      String city,
      String state,
      String catname,
      String ssn,
      String cellphone,
      String email
  ) {
    this.accountNumber = accountNumber;
    this.name = name;
    this.address = address;
    this.city = city;
    this.state = state;
    this.catname = catname;
    this.ssn = ssn;
    this.cellphone = cellphone;
    this.email = email;
  }

  public long getAccountNumber() {
    return accountNumber;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getCatname() {
    return catname;
  }

  public String getSsn() {
    return ssn;
  }

  public String getCellphone() {
    return cellphone;
  }

  public String getEmail() {
    return email;
  }


}
