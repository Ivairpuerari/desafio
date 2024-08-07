package com.caju.desafio.domain.services;


import com.caju.desafio.domain.enums.CategoryBalanceEnum;
import com.caju.desafio.domain.usecases.GetCategoryByMCCUseCase;
import org.springframework.stereotype.Service;

@Service
public class GetCategoryByMCCService implements GetCategoryByMCCUseCase {
    @Override
    public CategoryBalanceEnum execute(String mcc) {
        return switch (mcc) {
            case "5411", "5412" -> CategoryBalanceEnum.FOOD;
            case "5811", "5812" -> CategoryBalanceEnum.MEAL;
            default -> CategoryBalanceEnum.CASH;
        };
    }
}
