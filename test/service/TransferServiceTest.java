package service;

import exception.MoneyTransferException;
import model.Account;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class TransferServiceTest {

    @Mock
    private AccountService accountService;

    @InjectMocks
    private TransferServiceImpl transferService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransferWithNegativeAmount() {
        Double negativeAmount = -1D;

        transferService.transfer(1L, 2L, negativeAmount);
    }

    @Test(expected = MoneyTransferException.class)
    public void testTransferWithAccountNotFound() {
        when(accountService.getAccountById(any(Long.class))).thenReturn(Optional.empty());

        transferService.transfer(1L, 2L, 3D);
    }

    @Test(expected = MoneyTransferException.class)
    public void testTransferWithInsufficientFunds() {
        Long id =  1L;

        when(accountService.getAccountById(id))
                .thenReturn(Optional.of(new Account(id, BigDecimal.ONE, "USD")));

        transferService.transfer(id, id, 10D);
    }

    @Test
    public void testTransfer() {
        Long id = 1L;
        Account account = new Account(id, BigDecimal.TEN, "USD");

        when(accountService.getAccountById(1L)).thenReturn(Optional.of(account));

        transferService.transfer(id, id, 1D);
        verify(accountService, times(2)).getAccountById(id);
        verify(accountService, times(2)).saveOrUpdate(account);
    }
}
