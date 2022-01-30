package com.cottagecoders.microservice.symbol;

import com.cottagecoders.microservice.ErrorResponse;
import com.github.oscerd.finnhub.client.FinnhubClient;
import com.github.oscerd.finnhub.model.CompanyProfile;
import com.github.oscerd.finnhub.model.Quote;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiParam;
import io.javalin.plugin.openapi.annotations.OpenApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SymbolController {
  private static final Logger LOG = LoggerFactory.getLogger(SymbolController.class);
  private static final FinnhubClient finnhub = new FinnhubClient(System.getenv("FINNHUB_TOKEN"));

  @OpenApi(summary = "Get company profile for a symbol", operationId = "getCompanyProfileBySymbol", path = "/symbols" +
      "/profile/{symbolId}", method = HttpMethod.GET, pathParams = {
      @OpenApiParam(name = "symbolId", type = String.class, description = "The Symbol")
  }, tags = {"Symbol"}, responses = {
      @OpenApiResponse(status = "200", content = {@OpenApiContent(from = CompanyProfile.class)}),
      @OpenApiResponse(status = "400", content = {@OpenApiContent(from = ErrorResponse.class)}),
      @OpenApiResponse(status = "404", content = {@OpenApiContent(from = ErrorResponse.class)})
  })
  public static void getProfile(Context ctx) {

    String symbol = validPathParamSymbol(ctx);
    if (StringUtils.isEmpty(symbol)) {
      throw new NotFoundResponse("Invalid request.  Symbol not found in request.");
    }
    LOG.info("getProfile() for Symbol:  {}", symbol);

    CompanyProfile profile;
    try {
      profile = finnhub.getCompanyProfile(symbol);
    } catch (IOException ex) {
      throw new NotFoundResponse("Invalid request. Company profile error." + ex.getMessage());
    }

    ctx.json(profile);
  }

  @OpenApi(summary = "Get quote for a symbol", operationId = "getQuoteBySymbol", path = "/symbols" +
      "/quote/{symbolId}", method = HttpMethod.GET, pathParams = {
      @OpenApiParam(name = "symbolId", type = String.class, description = "The Symbol")
  }, tags = {"Symbol"}, responses = {
      @OpenApiResponse(status = "200", content = {@OpenApiContent(from = Quote.class)}),
      @OpenApiResponse(status = "400", content = {@OpenApiContent(from = ErrorResponse.class)}),
      @OpenApiResponse(status = "404", content = {@OpenApiContent(from = ErrorResponse.class)})
  })
  public static void getQuote(Context ctx) {
    LOG.info("getQuote() for Symbol:  {}", validPathParamSymbol(ctx));

    String symbol = validPathParamSymbol(ctx);
    if (StringUtils.isEmpty(symbol)) {
      throw new NotFoundResponse("Invalid request.  Symbol not found.");
    }

    Quote quote;
    try {
      quote = finnhub.getQuote(symbol);
    } catch (IOException ex) {
      throw new NotFoundResponse("Invalid request.  Error accessing quote." + ex.getMessage());
    }

    ctx.json(quote);
  }


  private static String validPathParamSymbol(Context ctx) {
    return ctx.pathParamAsClass("symbol", String.class)
        .check(symbol -> !symbol.equals(""), "Symbol must not be blank")
        .get();
  }

}
