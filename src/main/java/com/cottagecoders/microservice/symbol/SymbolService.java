package com.cottagecoders.microservice.symbol;

import com.github.oscerd.finnhub.client.FinnhubClient;
import com.github.oscerd.finnhub.model.Quote;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// This is a service, it should be independent.
public class SymbolService {

  private static final Map<String, Symbol> symbols = new HashMap<>();

  static {
    symbols.put("AAPL", new Symbol("AAPL", 56.78, System.currentTimeMillis()));
    symbols.put("TSLA", new Symbol("TSLA", 456.78, System.currentTimeMillis()));
    symbols.put("GOOG", new Symbol("GOOG", 1122.33, System.currentTimeMillis()));
    symbols.put("IBM", new Symbol("IBM", 123.45, System.currentTimeMillis()));
  }

  public static void save(String symbol, double price, long timeStamp) {
    symbols.put(symbol, new Symbol(symbol, price, timeStamp));
  }

  public static Collection<Symbol> getAll() {
    return symbols.values();
  }

  //here.
  public static void update(String symbol, double price, long timeStamp) {
    symbols.put(symbol, new Symbol(symbol, price, timeStamp));
  }

  public static Symbol findBySymbol(String symbol) {
    FinnhubClient finnhub = new FinnhubClient("c6jg0fqad3ieecon5skg");
    try {
      System.out.println("finnhub " + finnhub.getQuote(symbol));
      Quote q = finnhub.getQuote(symbol);
      Symbol s = new Symbol(symbol, Double.parseDouble(q.getCurrentPrice()), System.currentTimeMillis());

    } catch(Exception ex) {
      System.out.println("Exception: " + ex.getMessage());
      ex.printStackTrace();
      System.exit(12);
    }
    return symbols.get(symbol);
  }

  public static void delete(String symbol) {
    symbols.remove(symbol);
  }

}
