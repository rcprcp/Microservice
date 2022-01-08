package com.cottagecoders.microservice;

import com.cottagecoders.microservice.portfolio.PandLController;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
  private static final Logger LOG = LoggerFactory.getLogger(Utils.class);
  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public static long validPathParamAccountNumber(Context ctx) {
    return ctx.pathParamAsClass("accountNumber", Long.class).check(accountNumber -> !accountNumber.equals(""),
        "Account number must not be zero"
    ).get();
  }

  public static Date validateDate(Context ctx, String name) {
    String dt = ctx.pathParam(name);
    try {
      return sdf.parse(dt);
    } catch (ParseException ex) {
      LOG.error("Invalid date format '{}'  {}", dt, ex.getMessage(), ex);
      System.exit(10);
    }

    //TODO: this is here to prevent the IntelliJ warning (does not understand System.exit() in catch block)
    return new Date();
  }
}
