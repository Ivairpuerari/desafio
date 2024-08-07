package com.caju.desafio.domain.usecases;

import com.caju.desafio.domain.entities.Account;
import com.caju.desafio.domain.entities.BalanceCategory;
import com.caju.desafio.domain.entities.Transaction;
import com.caju.desafio.domain.enums.CategoryBalanceEnum;
import com.caju.desafio.domain.enums.StatusCodeApplicationEnum;
import com.caju.desafio.domain.services.ProcessTransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessTransactionUseCaseTest {

    @Mock
    private GetMCCByMerchantUseCase getMCCByMerchantUseCase;

    @Mock
    private GetCategoryByMCCUseCase getCategoryByMCCUseCase;

    @Mock
    private GetBalanceByAccountIdAndCategoryUseCase getBalanceByAccountIdAndCategoryUseCase;

    @Mock
    private DebitBalanceTransactionUseCase debitBalanceTransactionUseCase;

    private ProcessTransactionUseCase processTransactionUseCase;

    @BeforeEach
    void setUp() {
        this.processTransactionUseCase = new ProcessTransactionService(
                this.getMCCByMerchantUseCase,
                this.getCategoryByMCCUseCase,
                this.getBalanceByAccountIdAndCategoryUseCase,
                this.debitBalanceTransactionUseCase
        );
    }


    @Test
    public void shouldReturnSuccessWithInitialCategoryBalance() {
        Transaction transaction = createTransaction("merchant1", "5411", BigDecimal.valueOf(50));

        BalanceCategory balanceCategory = new BalanceCategory();

        balanceCategory.setAmount(BigDecimal.valueOf(100.00));
        balanceCategory.setCategoryName(CategoryBalanceEnum.FOOD.name());
        balanceCategory.setUpdatedAt(LocalDateTime.now());
        balanceCategory.setAccount(transaction.getAccount());

        when(getMCCByMerchantUseCase.execute(anyString())).thenReturn(Optional.of("5411"));
        when(getCategoryByMCCUseCase.execute("5411")).thenReturn(CategoryBalanceEnum.FOOD);
        when(getBalanceByAccountIdAndCategoryUseCase.execute(transaction.getAccount().getId(), CategoryBalanceEnum.FOOD))
                .thenReturn(Optional.of(balanceCategory));

        StatusCodeApplicationEnum result = processTransactionUseCase.execute(transaction);

        verify(debitBalanceTransactionUseCase).execute(transaction, balanceCategory);

        assertEquals(StatusCodeApplicationEnum.SUCCESS, result);
    }

    @Test
    void shouldReturnSuccessWithCashCategoryBalanceFallback() {
        Transaction transaction = createTransaction("merchant1", "5411", BigDecimal.valueOf(50));

        BalanceCategory balanceCategoryFood = new BalanceCategory();

        balanceCategoryFood.setAmount(BigDecimal.valueOf(40.00));
        balanceCategoryFood.setCategoryName(CategoryBalanceEnum.FOOD.name());
        balanceCategoryFood.setUpdatedAt(LocalDateTime.now());
        balanceCategoryFood.setAccount(transaction.getAccount());

        BalanceCategory balanceCategoryCash = new BalanceCategory();

        balanceCategoryCash.setAmount(BigDecimal.valueOf(100.00));
        balanceCategoryCash.setCategoryName(CategoryBalanceEnum.CASH.name());
        balanceCategoryCash.setUpdatedAt(LocalDateTime.now());
        balanceCategoryCash.setAccount(transaction.getAccount());

        when(getMCCByMerchantUseCase.execute(anyString())).thenReturn(Optional.of("5411"));
        when(getCategoryByMCCUseCase.execute("5411")).thenReturn(CategoryBalanceEnum.FOOD);
        when(getBalanceByAccountIdAndCategoryUseCase.execute(transaction.getAccount().getId(), CategoryBalanceEnum.FOOD))
                .thenReturn(Optional.of(balanceCategoryFood));
        when(getBalanceByAccountIdAndCategoryUseCase.execute(transaction.getAccount().getId(), CategoryBalanceEnum.CASH))
                .thenReturn(Optional.of(balanceCategoryCash));

        StatusCodeApplicationEnum result = processTransactionUseCase.execute(transaction);

        verify(debitBalanceTransactionUseCase).execute(transaction, balanceCategoryCash);
        assertEquals(StatusCodeApplicationEnum.SUCCESS, result);
    }

    @Test
    void shouldReturnRejectDueToInsufficientFunds() {
        Transaction transaction = createTransaction("merchant1", "5411", BigDecimal.valueOf(50));

        BalanceCategory balanceCategoryFood = new BalanceCategory();

        balanceCategoryFood.setAmount(BigDecimal.valueOf(40.00));
        balanceCategoryFood.setCategoryName(CategoryBalanceEnum.FOOD.name());
        balanceCategoryFood.setUpdatedAt(LocalDateTime.now());
        balanceCategoryFood.setAccount(transaction.getAccount());

        BalanceCategory balanceCategoryCash = new BalanceCategory();

        balanceCategoryCash.setAmount(BigDecimal.valueOf(10.00));
        balanceCategoryCash.setCategoryName(CategoryBalanceEnum.CASH.name());
        balanceCategoryCash.setUpdatedAt(LocalDateTime.now());
        balanceCategoryCash.setAccount(transaction.getAccount());

        when(getMCCByMerchantUseCase.execute(anyString())).thenReturn(Optional.of("5411"));
        when(getCategoryByMCCUseCase.execute("5411")).thenReturn(CategoryBalanceEnum.FOOD);
        when(getBalanceByAccountIdAndCategoryUseCase.execute(transaction.getAccount().getId(), CategoryBalanceEnum.FOOD))
                .thenReturn(Optional.of(balanceCategoryFood));
        when(getBalanceByAccountIdAndCategoryUseCase.execute(transaction.getAccount().getId(), CategoryBalanceEnum.CASH))
                .thenReturn(Optional.of(balanceCategoryCash));

        StatusCodeApplicationEnum result = processTransactionUseCase.execute(transaction);

        verify(debitBalanceTransactionUseCase, never()).execute(any(Transaction.class), any(BalanceCategory.class));
        assertEquals(StatusCodeApplicationEnum.REJECT, result);
    }

    private Transaction createTransaction(String merchant, String mcc, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setMerchant(merchant);
        transaction.setMcc(mcc);
        transaction.setTotalAmount(amount);
        Account account = new Account();
        account.setId(1);
        transaction.setAccount(account);
        return transaction;
    }


}