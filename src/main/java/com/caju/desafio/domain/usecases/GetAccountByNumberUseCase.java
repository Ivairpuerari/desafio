package com.caju.desafio.domain.usecases;

import com.caju.desafio.domain.entities.Account;

import java.util.Optional;

public interface GetAccountByNumberUseCase {
    Optional<Account> execute(String number);
}
