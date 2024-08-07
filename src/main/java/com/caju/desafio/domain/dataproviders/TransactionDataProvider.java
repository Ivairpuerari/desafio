package com.caju.desafio.domain.dataproviders;

import com.caju.desafio.domain.entities.Transaction;

import java.util.Optional;

public interface TransactionDataProvider {
    Optional<String> findMCCByMerchant(String merchant);
    void save(Transaction transaction);
}
