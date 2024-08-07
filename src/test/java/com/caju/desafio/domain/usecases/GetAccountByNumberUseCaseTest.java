package com.caju.desafio.domain.usecases;

import com.caju.desafio.domain.dataproviders.AccountDataProvider;
import com.caju.desafio.domain.entities.Account;
import com.caju.desafio.domain.exceptions.DataAccessException;
import com.caju.desafio.domain.services.GetAccountByNumberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAccountByNumberUseCaseTest {
    @Mock
    private AccountDataProvider accountDataProvider;
    private GetAccountByNumberUseCase getAccountByNumberUseCase;
    private Account account;
    private String number;

    @BeforeEach
    void setUp() {
        account = new Account(123,"123", "Ivair Puerari");
        number = "123";

        getAccountByNumberUseCase = new GetAccountByNumberService(
                this.accountDataProvider
        );
    }

    @Test
    public void shouldReturnOptionalEmptyWhenThrowDataAccessException() {
        when(accountDataProvider.findAccountByNumber(number)).thenThrow(DataAccessException.class);

        Optional<Account> account = this.getAccountByNumberUseCase.execute(number);

        assertTrue(account.isEmpty());
    }

    @Test
    public void shouldReturnOptionalEmptyWhenNotFoundAccountByNumber() {
        when(accountDataProvider.findAccountByNumber(number)).thenReturn(Optional.empty());

        Optional<Account> account = this.getAccountByNumberUseCase.execute(number);

        assertTrue(account.isEmpty());
    }

    @Test
    public void shouldReturnAccountWhenFindAccountByNumber() {
        when(accountDataProvider.findAccountByNumber(number)).thenReturn(Optional.of(account));

        Optional<Account> account = this.getAccountByNumberUseCase.execute(number);

        assertTrue(account.isPresent());
    }
}