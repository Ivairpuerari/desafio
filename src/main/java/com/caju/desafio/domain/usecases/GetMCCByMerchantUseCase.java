package com.caju.desafio.domain.usecases;

import java.util.Optional;

public interface GetMCCByMerchantUseCase {
    Optional<String> execute(String merchant);
}
