package com.cottagecoders.microservice;

import com.cottagecoders.microservice.account.AccountController;
import com.cottagecoders.microservice.portfolio.PandLController;
import com.cottagecoders.microservice.symbol.SymbolController;
import io.javalin.Javalin;
import io.javalin.plugin.openapi.OpenApiOptions;
import io.javalin.plugin.openapi.OpenApiPlugin;
import io.javalin.plugin.openapi.ui.SwaggerOptions;
import io.swagger.v3.oas.models.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class Microservice {
  private static final Logger LOG = LoggerFactory.getLogger(Microservice.class);

  public static void main(String[] args) {
    Javalin.create(config -> {
      config.registerPlugin(getConfiguredOpenApiPlugin());
      config.defaultContentType = "application/json";
      config.requestLogger((ctx, ms) -> {
        LOG.info("requestTiming: path {} ms {}", ctx.path(), ms);
      });
    }).routes(() -> {

      path("symbols", () -> {
        path("/info/{symbol}", () -> {
          get(SymbolController::getProfile);
        });
        path("/quote/{symbol}", () -> {
          get(SymbolController::getQuote);
        });
      });
      path("account", () -> {
        path("/details/{accountNumber}", () -> {
          get(AccountController::getAccountDetails);
        });
      });

      path("portfolio", () -> {
        path("/pandl/{accountNumber}/{startDate}/{endDate}", () -> {
          get(PandLController::getPandL);
        });

        path("/positions/{accountNumber}", () -> {
          get(PandLController::getPositions);
        });
      });  //routes.
    }).start(7002);

    LOG.info("REST API docs: http://localhost:7002/swagger-ui");
  }

  private static OpenApiPlugin getConfiguredOpenApiPlugin() {
    Info info = new Info().version("1.0").title("Microservice API").description("Demo API2");
    OpenApiOptions options = new OpenApiOptions(info).activateAnnotationScanningFor(
            "com.cottagecoders.microservice.Microservice").path("/swagger-docs") // endpoint for OpenAPI json
        .swagger(new SwaggerOptions("/swagger-ui").title("MicroService REST API Documentation")).defaultDocumentation(
            doc -> {
              doc.json("500", com.cottagecoders.microservice.ErrorResponse.class);
              doc.json("503", com.cottagecoders.microservice.ErrorResponse.class);
            });
    return new OpenApiPlugin(options);
  }

}
