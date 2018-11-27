package service;

import model.Account;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.AccountRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account());
        when(accountRepository.findAll()).thenReturn(accounts);

        assertEquals(1, accountService.getAll().size());
        verify(accountRepository).findAll();
    }

    @Test
    public void getAccountById() {
        when(accountRepository.findById(any(Long.class)))
                .thenReturn(Optional.of(new Account(1L, BigDecimal.ONE, "USD")));

        Long idToFind = 1L;

        assertTrue(accountService.getAccountById(idToFind).isPresent());
        verify(accountRepository).findById(idToFind);
    }

    @Test
    public void testSaveOrUpdate() {
        Account account = new Account(null, BigDecimal.ONE, "USD");

        accountService.saveOrUpdate(account);
        verify(accountRepository).save(account);

        account.setId(1L);

        accountService.saveOrUpdate(account);
        verify(accountRepository).update(account);
    }
}
