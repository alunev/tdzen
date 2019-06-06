package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.typesafe.config.Config;
import play.Logger;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.util.Map;
import java.util.concurrent.CompletionStage;

import static java.util.concurrent.CompletableFuture.completedStage;

/**
 * @author red
 * @since 0.0.1
 */
public class ZmAuthController extends Controller {
    private static final Logger.ALogger log = Logger.of(ZmAuthController.class);

    private final WSClient ws;

    private final Config config;

    @Inject
    public ZmAuthController(WSClient ws, Config config) {
        this.ws = ws;
        this.config = config;
    }

    public CompletionStage<Result> authCallback(Http.Request request) {
        String code = request.getQueryString("code");

        if (code != null) {
            log.info("Success with code = " + code);

            WSRequest wsRequest = ws.url("https://api.zenmoney.ru/oauth2/token/")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded");

            String body = "grant_type=authorization_code"
                    + "&client_id=" + config.getString("zm.client_id")
                    + "&client_secret=" + config.getString("zm.client_secret")
                    + "&code=" + code
                    + "&redirect_uri=http%3A%2F%2Flocalhost%3A9000%2Fzm%2Fcallback";

            return wsRequest.post(body).thenApply(wsResponse -> {
                log.info("Got token response: {}", wsResponse.asJson());

                JsonNode token = wsResponse.asJson().get("access_token");

                return redirect(routes.HomeController.index())
                        .addingToSession(request, Map.of(SessionParams.ZM_TOKEN, token.asText()));
            });
        } else {
            return completedStage(unauthorized());
        }
    }
}
