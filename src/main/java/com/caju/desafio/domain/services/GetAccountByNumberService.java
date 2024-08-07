package com.caju.desafio.domain.services;

import com.caju.desafio.domain.dataproviders.AccountDataProvider;
import com.caju.desafio.domain.entities.Account;
import com.caju.desafio.domain.exceptions.DataAccessException;
import com.caju.desafio.domain.usecases.GetAccountByNumberUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetAccountByNumberService implements GetAccountByNumberUseCase {

    private final AccountDataProvider accountDataProvider;

    public GetAccountByNumberService(AccountDataProvider accountDataProvider) {
        this.accountDataProvider = accountDataProvider;
    }

    @Override
    public Optional<Account> execute(String number) {
        try {
            return this.accountDataProvider.findAccountByNumber(number);
        }catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
