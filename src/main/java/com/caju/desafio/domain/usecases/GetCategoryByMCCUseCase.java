package com.caju.desafio.domain.usecases;

import com.caju.desafio.domain.enums.CategoryBalanceEnum;

public interface GetCategoryByMCCUseCase {
    CategoryBalanceEnum execute(String mcc);
}
