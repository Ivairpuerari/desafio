package com.caju.desafio.domain.services;

import com.caju.desafio.domain.dataproviders.TransactionDataProvider;
import com.caju.desafio.domain.usecases.GetMCCByMerchantUseCase;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetMCCByMerchantService implements GetMCCByMerchantUseCase {

    private final TransactionDataProvider transactionDataProvider;

    public GetMCCByMerchantService(TransactionDataProvider transactionDataProvider) {
        this.transactionDataProvider = transactionDataProvider;
    }

    @Override
    public Optional<String> execute(String merchant) {
        return this.transactionDataProvider.findMCCByMerchant(merchant);
    }
}
