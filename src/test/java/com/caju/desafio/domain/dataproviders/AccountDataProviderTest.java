package com.caju.desafio.domain.dataproviders;

import com.caju.desafio.app.providers.AccountProvider;
import com.caju.desafio.app.providers.repositories.AccountRepository;
import com.caju.desafio.app.providers.repositories.BalanceCategoryRepository;
import com.caju.desafio.domain.entities.Account;
import com.caju.desafio.domain.entities.BalanceCategory;
import com.caju.desafio.domain.enums.CategoryBalanceEnum;
import com.caju.desafio.domain.exceptions.DataAccessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountDataProviderTest {

    private AccountDataProvider accountDataProvider;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private BalanceCategoryRepository balanceCategoryRepository;
    private String number;
    private Account account;
    private BalanceCategory balanceCategory;

    @BeforeEach
    void setUp() {
        account = new Account(123, "123", "Ivair Puerari");
        number = "123";
        accountDataProvider = new AccountProvider(
                accountRepository,
                balanceCategoryRepository
        );

        balanceCategory = new BalanceCategory();
        balanceCategory.setCategoryName(CategoryBalanceEnum.FOOD.name());
        balanceCategory.setAmount(BigDecimal.valueOf(100));
        balanceCategory.setAccount(account);
        balanceCategory.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    void shouldReturnAccountWhenFindAccountByNumber() {
        when(accountRepository.findAccountByNumber(number)).thenReturn(account);

        Optional<Account> account = this.accountDataProvider.findAccountByNumber(number);

        assertTrue(account.isPresent());
    }

    @Test
    void shouldReturnEmptyWhenNotFindAccountByNumber() {
        when(accountRepository.findAccountByNumber(number)).thenReturn(null);

        Optional<Account> account = this.accountDataProvider.findAccountByNumber(number);

        assertTrue(account.isEmpty());
    }

    @Test
    void shouldThrowDataAccessExceptionWhenThrowAnyException() {
        when(accountRepository.findAccountByNumber(number)).thenThrow(RuntimeException.class);

        Assertions.assertThrows(DataAccessException.class, () -> {
            this.accountDataProvider.findAccountByNumber(number);
        });
    }


    @Test
    void shouldReturnBalanceCategoryWhenExists() {
        when(balanceCategoryRepository.findBalanceCategoryByAccount_IdAndCategoryName(1, CategoryBalanceEnum.FOOD.name()))
                .thenReturn(balanceCategory);

        Optional<BalanceCategory> result = accountDataProvider.getBalanceCategoryByAccountIdAndCategoryBalanceEnum(1, CategoryBalanceEnum.FOOD);

        assertTrue(result.isPresent());
        assertEquals(balanceCategory, result.get());
    }


    @Test
    void shouldReturnEmptyWhenBalanceCategoryDoesNotExist() {
        when(balanceCategoryRepository.findBalanceCategoryByAccount_IdAndCategoryName(1, CategoryBalanceEnum.FOOD.name()))
                .thenReturn(null);

        Optional<BalanceCategory> result = accountDataProvider.getBalanceCategoryByAccountIdAndCategoryBalanceEnum(1, CategoryBalanceEnum.FOOD);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldUpdateBalanceAccount() {
        accountDataProvider.updateBalanceAccount(balanceCategory);
        verify(balanceCategoryRepository).save(balanceCategory);
    }
}