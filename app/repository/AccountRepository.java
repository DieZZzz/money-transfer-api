package repository;

import com.google.inject.ImplementedBy;
import model.Account;

import java.util.List;
import java.util.Optional;

@ImplementedBy(AccountRepositoryImpl.class)
public interface AccountRepository {

    List<Account> findAll();

    Optional<Account> findById(Long id);

    void save(Account account);

    void update(Account account);
}
