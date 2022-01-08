package com.cottagecoders.microservice.account;

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

import java.text.SimpleDateFormat;

public class AccountController {
  private static final Logger LOG = LoggerFactory.getLogger(AccountController.class);
  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  AccountController() {
  }

  @OpenApi(summary = "Get account details for a specified account", operationId = "accountDetails", path = "/" +
      "/account/details/{accountNumber}", method = HttpMethod.GET, pathParams = {
      @OpenApiParam(name = "accountNumber", description = "Account Number")
  }, tags = {"Account"}, responses = {
      @OpenApiResponse(status = "200", content = {@OpenApiContent(from = AccountResponse.class)}),
      @OpenApiResponse(status = "400", content = {@OpenApiContent(from = ErrorResponse.class)}),
      @OpenApiResponse(status = "404", content = {@OpenApiContent(from = ErrorResponse.class)})
  })
  public static void getAccountDetails(Context ctx) {

    AccountService accountService = new AccountService();
    AccountRequest request = new AccountRequest(Utils.validPathParamAccountNumber(ctx));

    ctx.json(accountService.getAccountDetails(request));
    //    ctx.json(database.pAndLInDateRange(accountNumber, startDate, endDate));
  }
}
