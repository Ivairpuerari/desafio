package com.caju.desafio.domain.usecases;

import com.caju.desafio.domain.dataproviders.AccountDataProvider;
import com.caju.desafio.domain.entities.BalanceCategory;
import com.caju.desafio.domain.enums.CategoryBalanceEnum;
import com.caju.desafio.domain.services.GetBalanceByAccountIdAndCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetBalanceByAccountIdAndCategoryUseCaseTest {

    @Mock
    private AccountDataProvider accountDataProvider;
    private GetBalanceByAccountIdAndCategoryUseCase getBalanceByAccountIdAndCategoryUseCase;
    private BalanceCategory balanceCategory;

    @BeforeEach
    void setUp() {
        this.getBalanceByAccountIdAndCategoryUseCase = new GetBalanceByAccountIdAndCategoryService(
                this.accountDataProvider
        );

        this.balanceCategory = new BalanceCategory();
    }

    @Test
    public void shouldReturnBalanceCategoryWhenFindBalanceCategoryByAccountIdAndCategoryBalanceEnum() {
        Integer accountId = 1;
        CategoryBalanceEnum categoryBalanceEnum = CategoryBalanceEnum.FOOD;

        when(this.accountDataProvider.getBalanceCategoryByAccountIdAndCategoryBalanceEnum(accountId, categoryBalanceEnum)).thenReturn(Optional.of(this.balanceCategory));

        Optional<BalanceCategory> balanceCategoryResult = this.getBalanceByAccountIdAndCategoryUseCase.execute(accountId, categoryBalanceEnum);

        assertTrue(balanceCategoryResult.isPresent());
    }

    @Test
    public void shouldReturnEmptyWhenNotFoundBalanceCategoryByAccountIdAndCategoryBalanceEnum() {
        Integer accountId = 1;
        CategoryBalanceEnum categoryBalanceEnum = CategoryBalanceEnum.FOOD;

        when(this.accountDataProvider.getBalanceCategoryByAccountIdAndCategoryBalanceEnum(accountId, categoryBalanceEnum)).thenReturn(Optional.empty());

        Optional<BalanceCategory> balanceCategoryResult = this.getBalanceByAccountIdAndCategoryUseCase.execute(accountId, categoryBalanceEnum);

        assertTrue(balanceCategoryResult.isEmpty());
    }
}