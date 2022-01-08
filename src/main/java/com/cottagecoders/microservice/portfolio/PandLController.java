package com.cottagecoders.microservice.portfolio;

import com.cottagecoders.microservice.Database;
import com.cottagecoders.microservice.ErrorResponse;
import com.cottagecoders.microservice.Utils;
import io.javalin.http.Context;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiParam;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PandLController {
  private static final Logger LOG = LoggerFactory.getLogger(PandLController.class);
  private static final Database database = new Database(System.getenv("DATABASE_URL"),
      System.getenv("DATABASE_USERNAME"),
      System.getenv("DATABASE_PASSWORD")
  );

  @OpenApi(summary = "Get profit and loss in a date range for an account", operationId = "getProfitAndLossforAccount"
      , path = "/" + "/pandl/{accountNumber}/{startDate}/{endDate}", method = HttpMethod.GET, pathParams = {
      @OpenApiParam(name = "accountNumber", description = "Account Number"),
      @OpenApiParam(name = "startDate", type = java.sql.Date.class, description = "Start Date"),
      @OpenApiParam(name = "endDate", type = java.sql.Date.class, description = "End Date")
  }, tags = {"PandL"}, responses = {
      @OpenApiResponse(status = "200", content = {@OpenApiContent(from = PandLResponse.class)}),
      @OpenApiResponse(status = "400", content = {@OpenApiContent(from = ErrorResponse.class)}),
      @OpenApiResponse(status = "404", content = {@OpenApiContent(from = ErrorResponse.class)})
  })
  public static void getPandL(Context ctx) throws SQLException {

    PandLService pandlService = new PandLService();
    PandLRequest request = new PandLRequest(
        Utils.validPathParamAccountNumber(ctx),
        new Timestamp(Utils.validateDate(ctx, "startDate").getTime()),
        new Timestamp(Utils.validateDate(ctx, "endDate").getTime())
    );
    List<Transaction> transactions = database.getallTransactions(request);
    ctx.json(pandlService.calculatePandLInTimeRange(request, transactions));
  }

  @OpenApi(summary = "Get positions for an account", operationId = "getPositionsForAccount", path = "/" + "/positions"
      + "/{accountNumber}", method = HttpMethod.GET, pathParams = {
      @OpenApiParam(name = "accountNumber", description = "Account Number")
  }, tags = {"Portfolio"}, responses = {
      @OpenApiResponse(status = "200", content = {@OpenApiContent(from = Holding[].class)}),
      @OpenApiResponse(status = "400", content = {@OpenApiContent(from = ErrorResponse.class)}),
      @OpenApiResponse(status = "404", content = {@OpenApiContent(from = ErrorResponse.class)})
  })
  public static void getPositions(Context ctx) {
    long accountNumber = Utils.validPathParamAccountNumber(ctx);
   ctx.json(database.getPositions(accountNumber));
  }




}
