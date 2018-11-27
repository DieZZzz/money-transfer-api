package service;

import com.google.inject.ImplementedBy;
import model.Account;

import java.util.List;
import java.util.Optional;

@ImplementedBy(AccountServiceImpl.class)
public interface AccountService {

    List<Account> getAll();

    Optional<Account> getAccountById(Long id);

    void saveOrUpdate(Account account);
}
