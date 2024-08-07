package com.caju.desafio.domain.services;

import com.caju.desafio.domain.dataproviders.AccountDataProvider;
import com.caju.desafio.domain.entities.BalanceCategory;
import com.caju.desafio.domain.enums.CategoryBalanceEnum;
import com.caju.desafio.domain.usecases.GetBalanceByAccountIdAndCategoryUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetBalanceByAccountIdAndCategoryService implements GetBalanceByAccountIdAndCategoryUseCase {

    private final AccountDataProvider accountDataProvider;

    public GetBalanceByAccountIdAndCategoryService(AccountDataProvider accountDataProvider) {
        this.accountDataProvider = accountDataProvider;
    }

    @Override
    public Optional<BalanceCategory> execute(Integer accountId, CategoryBalanceEnum categoryBalanceEnum) {
        return this.accountDataProvider.getBalanceCategoryByAccountIdAndCategoryBalanceEnum(accountId, categoryBalanceEnum);
    }
}
