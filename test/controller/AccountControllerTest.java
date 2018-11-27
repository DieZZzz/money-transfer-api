package controller;

import model.Account;
import org.junit.Test;
import play.Application;
import play.db.jpa.JPAApi;
import play.inject.guice.GuiceApplicationBuilder;
import play.mvc.Http;
import play.mvc.Result;
import play.test.WithApplication;
import repository.AccountRepository;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static play.test.Helpers.*;

public class AccountControllerTest extends WithApplication {

    private static final String ACCOUNTS_ROUTE = "/rest/v1/accounts";

    @Override
    protected Application provideApplication() {
        return new GuiceApplicationBuilder().build();
    }

    @Test
    public void testGetAccounts() {
        AccountRepository repository = app.injector().instanceOf(AccountRepository.class);
        JPAApi jpaApi = app.injector().instanceOf(JPAApi.class);
        jpaApi.withTransaction(() -> repository.save(new Account(null, BigDecimal.TEN, "USD")));

        Http.RequestBuilder request = new Http.RequestBuilder().method(GET).uri(ACCOUNTS_ROUTE);

        Result result = route(app, request);
        assertThat(result.status(), equalTo(OK));
        assertNotNull(contentAsString(result));
    }
}
