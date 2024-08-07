package com.caju.desafio.domain.services;

import com.caju.desafio.domain.dataproviders.AccountDataProvider;
import com.caju.desafio.domain.dataproviders.TransactionDataProvider;
import com.caju.desafio.domain.entities.BalanceCategory;
import com.caju.desafio.domain.entities.Transaction;
import com.caju.desafio.domain.usecases.DebitBalanceTransactionUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class DebitBalanceTransactionService implements DebitBalanceTransactionUseCase {

    private final AccountDataProvider accountDataProvider;
    private final TransactionDataProvider transactionDataProvider;

    public DebitBalanceTransactionService(AccountDataProvider accountDataProvider, TransactionDataProvider transactionDataProvider) {
        this.accountDataProvider = accountDataProvider;
        this.transactionDataProvider = transactionDataProvider;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public void execute(Transaction transaction, BalanceCategory balanceCategory) {
        balanceCategory.setAmount(balanceCategory.getAmount().subtract(transaction.getTotalAmount()));
        balanceCategory.setUpdatedAt(LocalDateTime.now());

        this.accountDataProvider.updateBalanceAccount(balanceCategory);

        this.transactionDataProvider.save(transaction);
    }
}
