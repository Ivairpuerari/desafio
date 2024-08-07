package com.caju.desafio.domain.usecases;

import com.caju.desafio.domain.entities.BalanceCategory;
import com.caju.desafio.domain.entities.Transaction;

public interface DebitBalanceTransactionUseCase {
    void execute(Transaction transaction, BalanceCategory balanceCategory);
}
