package com.caju.desafio.app.providers;

import com.caju.desafio.app.providers.repositories.AccountRepository;
import com.caju.desafio.app.providers.repositories.BalanceCategoryRepository;
import com.caju.desafio.domain.dataproviders.AccountDataProvider;
import com.caju.desafio.domain.entities.Account;
import com.caju.desafio.domain.entities.BalanceCategory;
import com.caju.desafio.domain.enums.CategoryBalanceEnum;
import com.caju.desafio.domain.exceptions.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountProvider implements AccountDataProvider {

    private final AccountRepository accountRepository;
    private final BalanceCategoryRepository balanceCategoryRepository;

    public AccountProvider(AccountRepository accountRepository, BalanceCategoryRepository balanceCategoryRepository) {
        this.accountRepository = accountRepository;
        this.balanceCategoryRepository = balanceCategoryRepository;
    }

    @Override
    public Optional<Account> findAccountByNumber(String number) throws DataAccessException {
        try {
            return Optional.ofNullable(this.accountRepository.findAccountByNumber(number));
        } catch (Exception e) {
            throw new DataAccessException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<BalanceCategory> getBalanceCategoryByAccountIdAndCategoryBalanceEnum(Integer accountId, CategoryBalanceEnum categoryBalanceEnum) {
        return Optional.ofNullable(this.balanceCategoryRepository.findBalanceCategoryByAccount_IdAndCategoryName(accountId, categoryBalanceEnum.name()));
    }

    @Override
    public void updateBalanceAccount(BalanceCategory balanceCategory) {
        this.balanceCategoryRepository.save(balanceCategory);
    }
}
