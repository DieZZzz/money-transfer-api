package service;

import model.Account;
import repository.AccountRepository;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Inject
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Optional<Account> getAccountById(Long id) {
        Objects.requireNonNull(id, "Id may not be null");

        return accountRepository.findById(id);
    }

    @Override
    public void saveOrUpdate(Account account) {
        Objects.requireNonNull(account, "Account may not be null");

        if (account.getId() == null) {
            accountRepository.save(account);
        } else {
            accountRepository.update(account);
        }
    }
}
