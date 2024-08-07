package com.caju.desafio.domain.usecases;

import com.caju.desafio.domain.dataproviders.AccountDataProvider;
import com.caju.desafio.domain.dataproviders.TransactionDataProvider;
import com.caju.desafio.domain.entities.Account;
import com.caju.desafio.domain.entities.BalanceCategory;
import com.caju.desafio.domain.entities.Transaction;
import com.caju.desafio.domain.enums.CategoryBalanceEnum;
import com.caju.desafio.domain.services.DebitBalanceTransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class DebitBalanceTransactionUseCaseTest {

    private DebitBalanceTransactionUseCase debitBalanceTransactionUseCase;
    @Mock
    private TransactionDataProvider transactionDataProvider;
    @Mock
    private AccountDataProvider accountDataProvider;
    private Transaction transaction;
    private BalanceCategory balanceCategory;


    @BeforeEach
    void setUp() {
        Account account = new Account();
        account.setNumber("12300");
        account.setHolderName("John Kenney");
        account.setUpdatedAt(LocalDateTime.now());

        balanceCategory = new BalanceCategory();

        balanceCategory.setAccount(account);
        balanceCategory.setCategoryName(CategoryBalanceEnum.CASH.name());
        balanceCategory.setUpdatedAt(LocalDateTime.now());
        balanceCategory.setAmount(BigDecimal.valueOf(100.00));

        transaction = new Transaction();

        transaction.setAccount(account);
        transaction.setMcc(CategoryBalanceEnum.CASH.name());
        transaction.setMerchant("IFOOD");
        transaction.setTotalAmount(BigDecimal.valueOf(50.00));

        this.debitBalanceTransactionUseCase = new DebitBalanceTransactionService(
                accountDataProvider,
                transactionDataProvider
        );
    }

    @Test
    public void shouldExecuteWithSuccessWhenDebitBalanceTransaction() {
        Assertions.assertDoesNotThrow(() -> {
            this.debitBalanceTransactionUseCase.execute(transaction, balanceCategory);
        });
    }

}