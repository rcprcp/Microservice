package com.cottagecoders.microservice;

import com.github.oscerd.finnhub.client.FinnhubClient;
import com.github.oscerd.finnhub.model.Candle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.util.Date;
import java.util.Locale;

public class HistoricalPrice {
  Logger LOG = LoggerFactory.getLogger(HistoricalPrice.class);
  FinnhubClient finnhub;

  public HistoricalPrice() {
    finnhub = new FinnhubClient(System.getenv("FINNHUB_TOKEN"));
  }

  private static Long adjustBusinessDay(Timestamp ts) {
    DayOfWeek dow = ts.toLocalDateTime().getDayOfWeek();
    long epochSeconds = ts.getTime() / 1000;
    if (dow == DayOfWeek.SATURDAY) {
      return epochSeconds - 86400L;
    } else if (dow == DayOfWeek.SUNDAY) {
      return epochSeconds - 86400L * 2L;
    } else {
      return epochSeconds;
    }
  }

  public double getPrice(String symbol, Timestamp ts) {
    Candle candle = new Candle();
    Date date = new Date(ts.getTime());
    int tries = 0;
    try {
      while(tries < 10) {
        candle = finnhub.getCandle(symbol.toLowerCase(Locale.ROOT), "D", date.getTime()/1000, date.getTime()/1000);
        if (candle.getS().equalsIgnoreCase("no_data")) {
          LOG.error("getCandle() returned \"no_data\" date time {} ", date);
          date = new Date(date.getTime() - 86400L * 1000L);  // subtract one day's worth of milliseconds.
          tries++;

        } else {
          break;
        }
      }

    } catch (IOException ex) {
      LOG.error("symbol {} ts {}/businessDay {} Exception accessing Finnhub {}",
          symbol,
          ts,
          date,
          ex.getMessage(),
          ex
      );
      System.exit(56);
    }
    LOG.info(
        "getPrice(): symbol {} epoch {} businessday {}  price {}",
        symbol,
        ts,
        date,
        candle.getC()[0]
    );
    return candle.getC()[0];
  }

}
