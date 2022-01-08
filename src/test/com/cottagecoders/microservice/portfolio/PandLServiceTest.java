package com.cottagecoders.microservice.portfolio;

import com.cottagecoders.microservice.TransactionType;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PandLServiceTest {

  @Test
  public void TestTransactionsBeforeTimeWindow() {
    List<Transaction> transactions = new ArrayList<>();
    transactions.add(new Transaction(
        "AAPL",
        1.0D,
        100.0D,
        TransactionType.BUY.name(),
        Timestamp.valueOf("2021-11-23 00:00:00+00")
    ));

    PandLService pandl = new PandLService();


  }
}
