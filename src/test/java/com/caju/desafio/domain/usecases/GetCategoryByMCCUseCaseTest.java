package com.caju.desafio.domain.usecases;

import com.caju.desafio.domain.enums.CategoryBalanceEnum;
import com.caju.desafio.domain.services.GetCategoryByMCCService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetCategoryByMCCUseCaseTest {

    private GetCategoryByMCCUseCase getCategoryByMCCUseCase;

    @BeforeEach
    void setUp() {
        this.getCategoryByMCCUseCase = new GetCategoryByMCCService();
    }

    @Test
    public void shouldReturnMCCFOODWhenExecute5411() {
        assertEquals(CategoryBalanceEnum.FOOD, getCategoryByMCCUseCase.execute("5411"));
    }

    @Test
    public void shouldReturnMCCFOODWhenExecute5412() {
        assertEquals(CategoryBalanceEnum.FOOD, getCategoryByMCCUseCase.execute("5412"));
    }

    @Test
    public void shouldReturnMCCFOODWhenExecute5811() {
        assertEquals(CategoryBalanceEnum.MEAL, getCategoryByMCCUseCase.execute("5811"));
    }

    @Test
    public void shouldReturnMCCFOODWhenExecute5812() {
        assertEquals(CategoryBalanceEnum.MEAL, getCategoryByMCCUseCase.execute("5812"));
    }

    @Test
    public void shouldReturnMCCCASHWhenExecuteAnyOtherNumber() {
        assertEquals(CategoryBalanceEnum.CASH, getCategoryByMCCUseCase.execute("9999"));
        assertEquals(CategoryBalanceEnum.CASH, getCategoryByMCCUseCase.execute("1234"));
    }
}