package com.caju.desafio.domain.usecases;

import com.caju.desafio.domain.entities.Transaction;
import com.caju.desafio.domain.enums.StatusCodeApplicationEnum;

public interface ProcessTransactionUseCase {
    StatusCodeApplicationEnum execute(Transaction transaction);
}
