package com.caju.desafio.domain.dataproviders;

import com.caju.desafio.domain.entities.Account;
import com.caju.desafio.domain.entities.BalanceCategory;
import com.caju.desafio.domain.enums.CategoryBalanceEnum;
import com.caju.desafio.domain.exceptions.DataAccessException;

import java.util.Optional;

public interface AccountDataProvider {
    Optional<Account> findAccountByNumber(String name) throws DataAccessException;

    Optional<BalanceCategory> getBalanceCategoryByAccountIdAndCategoryBalanceEnum(Integer accountId, CategoryBalanceEnum categoryBalanceEnum);

    void updateBalanceAccount(BalanceCategory balanceCategory);
}
