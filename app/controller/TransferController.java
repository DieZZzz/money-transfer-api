package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.base.Strings;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import service.TransferService;

import javax.inject.Inject;

public class TransferController extends Controller {

    private final TransferService transferService;

    @Inject
    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result transferMoney() {
        JsonNode json = request().body().asJson();
        String from = json.findPath("from").asText();
        String to = json.findPath("to").asText();
        String amount = json.findPath("amount").asText();
        Result validationResult = validateParams(from, to, amount);
        if (validationResult == null) {
            transferService.transfer(Long.valueOf(from), Long.valueOf(to), Double.valueOf(amount));
            return ok(Json.newObject().put("message", "Successfully transfered from " + from + " to " + to));
        } else {
            return validationResult;
        }
    }

    private Result validateParams(String from, String to, String amount) {
        if (Strings.isNullOrEmpty(from)) {
            return badRequest(Json.newObject().put("message", "Missing some of required json keys [from]"));
        }
        if (Strings.isNullOrEmpty(to)) {
            return badRequest(Json.newObject().put("message", "Missing some of required json keys [to]"));
        }
        if (Strings.isNullOrEmpty(amount)) {
            return badRequest(Json.newObject().put("message", "Missing some of required json keys [amount]"));
        }
        return null;
    }
}
