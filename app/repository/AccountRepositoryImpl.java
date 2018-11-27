package repository;

import model.Account;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class AccountRepositoryImpl implements AccountRepository {

    private final JPAApi jpaApi;

    @Inject
    public AccountRepositoryImpl(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    @Override
    public List<Account> findAll() {
        return jpaApi.em().createQuery("select a from Account a", Account.class).getResultList();
    }

    @Override
    public Optional<Account> findById(Long id) {
        return Optional.ofNullable(jpaApi.em().getReference(Account.class, id));
    }

    @Override
    public void save(Account account) {
        jpaApi.em().persist(account);
    }

    @Override
    public void update(Account account) {
        jpaApi.em().merge(account);
    }
}
