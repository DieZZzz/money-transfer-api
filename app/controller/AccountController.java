package controller;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import service.AccountService;

import javax.inject.Inject;

import static play.libs.Json.toJson;

public class AccountController extends Controller {

    private final AccountService accountService;

    @Inject
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @Transactional(readOnly = true)
    public Result getAccounts() {
        return ok(toJson(accountService.getAll()));
    }

}
