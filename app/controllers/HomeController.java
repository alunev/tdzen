package controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.typesafe.config.Config;
import model.Transaction;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static controllers.SessionParams.ZM_TOKEN;


public class HomeController extends Controller {
    private static final Logger.ALogger log = Logger.of(HomeController.class);

    private final Config config;

    @Inject
    public HomeController(Config config) {
        this.config = config;
    }

    public Result index(Http.Request request) throws IOException {
        Optional<String> token = request.session().getOptional(ZM_TOKEN);
        Optional<String> tdJson = request.session().getOptional(SessionParams.TD_TRANSACTIONS);
        String zmLoginUrl = "https://api.zenmoney.ru/oauth2/authorize/?response_type=code" +
                "&client_id=" + config.getString("zm.client_id") +
                "&redirect_uri=http%3A%2F%2Flocalhost%3A9000%2Fzm%2Fcallback";

        List<Transaction> tdTransactions = Collections.emptyList();
        if (tdJson.isPresent() && !Strings.isNullOrEmpty(tdJson.get())) {
            tdTransactions = new ObjectMapper().readValue(tdJson.get(), new TypeReference<List<Transaction>>() {});
        }

        return ok(views.html.index.render(tdTransactions, token, zmLoginUrl));
    }
}
