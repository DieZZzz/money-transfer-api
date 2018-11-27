package controller;

import com.fasterxml.jackson.databind.JsonNode;
import model.Account;
import org.junit.Test;
import play.Application;
import play.db.jpa.JPAApi;
import play.inject.guice.GuiceApplicationBuilder;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import service.AccountService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static play.test.Helpers.*;

public class TransferControllerTest extends WithApplication {

    private static final String TRANSFER_ROUTE = "/rest/v1/transfer";

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testTransferMoneyInvalidRequest() {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("from", "1");
        JsonNode json = Json.toJson(requestMap);
        Http.RequestBuilder request = new Http.RequestBuilder().method(POST).bodyJson(json).uri(TRANSFER_ROUTE);

        Result result = route(app, request);

        assertThat(result.status(), equalTo(BAD_REQUEST));
    }

    @Test
    public void testTransferMoney() {
        AccountService service = app.injector().instanceOf(AccountService.class);
        JPAApi jpaApi = app.injector().instanceOf(JPAApi.class);
        jpaApi.withTransaction(() -> service.saveOrUpdate(new Account(1L, BigDecimal.TEN, "USD")));
        jpaApi.withTransaction(() -> service.saveOrUpdate(new Account(2L, BigDecimal.ONE, "EUR")));
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("from", "1");
        requestMap.put("to", "2");
        requestMap.put("amount", "1");
        JsonNode json = Json.toJson(requestMap);

        Http.RequestBuilder request = new Http.RequestBuilder().method(POST).bodyJson(json).uri(TRANSFER_ROUTE);

        Result result = route(app, request);

        assertThat(result.status(), equalTo(OK));
        assertNotNull(contentAsString(result));
    }
}
