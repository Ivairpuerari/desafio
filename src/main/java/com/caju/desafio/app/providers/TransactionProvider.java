package com.caju.desafio.app.providers;

import com.caju.desafio.app.providers.repositories.MerchantToMCCOverrideRepository;
import com.caju.desafio.app.providers.repositories.TransactionRepository;
import com.caju.desafio.domain.dataproviders.TransactionDataProvider;
import com.caju.desafio.domain.entities.MerchantToMCCOverride;
import com.caju.desafio.domain.entities.Transaction;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TransactionProvider implements TransactionDataProvider {

    private final TransactionRepository transactionRepository;
    private final MerchantToMCCOverrideRepository merchantToMCCOverrideRepository;

    public TransactionProvider(TransactionRepository transactionRepository, MerchantToMCCOverrideRepository merchantToMCCOverrideRepository) {
        this.transactionRepository = transactionRepository;
        this.merchantToMCCOverrideRepository = merchantToMCCOverrideRepository;
    }

    @Override
    public Optional<String> findMCCByMerchant(String merchant) {
        MerchantToMCCOverride merchantToMCCOverride = this.merchantToMCCOverrideRepository.findMerchantToMCCOverrideByMerchant(merchant);
        return Optional.ofNullable(merchantToMCCOverride == null ? null : merchantToMCCOverride.getMcc());
    }

    @Override
    public void save(Transaction transaction) {
        this.transactionRepository.save(transaction);
    }
}
