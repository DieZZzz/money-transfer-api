package service;

import exception.MoneyTransferException;
import model.Account;
import org.javamoney.moneta.Money;

import javax.inject.Inject;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import java.math.BigDecimal;
import java.util.Objects;

public class TransferServiceImpl implements TransferService {

    private final AccountService accountService;

    @Inject
    public TransferServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void transfer(Long from, Long to, Double amount) {
        Objects.requireNonNull(from, "From parameter may not be null");
        Objects.requireNonNull(to, "To parameter may not be null");
        Objects.requireNonNull(amount, "Amount parameter may not be null");
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount may not be negative");
        }

        Account fromAccount = accountService.getAccountById(from)
                .orElseThrow(() -> new MoneyTransferException("Account with id " + from + " not found"));
        Account toAccount = accountService.getAccountById(to)
                .orElseThrow(() -> new MoneyTransferException("Account with id " + to + " not found"));

        if (amount > fromAccount.getAmount().doubleValue()) {
            throw new MoneyTransferException("Insufficient funds for " + from + " account");
        }

        MonetaryAmount exchangedAmount = getExchangedAmount(fromAccount.getCurrency(), toAccount.getCurrency(), amount);
        fromAccount.setAmount(fromAccount.getAmount().subtract(new BigDecimal(amount)));
        toAccount.setAmount(toAccount.getAmount().add(new BigDecimal(exchangedAmount.getNumber().doubleValueExact())));

        accountService.saveOrUpdate(fromAccount);
        accountService.saveOrUpdate(toAccount);
    }

    private MonetaryAmount getExchangedAmount(String fromCurrency, String toCurrency, Double amount) {
        CurrencyConversion currencyConversion = MonetaryConversions.getConversion(toCurrency);
        MonetaryAmount fromMoney = Money.of(amount, fromCurrency);
        return fromMoney.with(currencyConversion);
    }
}
