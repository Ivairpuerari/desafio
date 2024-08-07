package com.caju.desafio.domain.usecases;

import com.caju.desafio.domain.entities.BalanceCategory;
import com.caju.desafio.domain.enums.CategoryBalanceEnum;

import java.util.Optional;

public interface GetBalanceByAccountIdAndCategoryUseCase {
    Optional<BalanceCategory> execute(Integer accountId, CategoryBalanceEnum categoryBalanceEnum);
}
