package com.caju.desafio.domain.dataproviders;

import com.caju.desafio.app.providers.TransactionProvider;
import com.caju.desafio.app.providers.repositories.MerchantToMCCOverrideRepository;
import com.caju.desafio.app.providers.repositories.TransactionRepository;
import com.caju.desafio.domain.entities.MerchantToMCCOverride;
import com.caju.desafio.domain.entities.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionDataProviderTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private MerchantToMCCOverrideRepository merchantToMCCOverrideRepository;

    private TransactionDataProvider transactionDataProvider;

    private MerchantToMCCOverride merchantToMCCOverride;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        merchantToMCCOverride = new MerchantToMCCOverride();
        merchantToMCCOverride.setMerchant("IFOOD");
        merchantToMCCOverride.setMcc("5411");

        transactionDataProvider = new TransactionProvider(
                this.transactionRepository,
                this.merchantToMCCOverrideRepository
        );
    }

    @Test
    void shouldReturnMCCWhenMerchantExists() {
        when(merchantToMCCOverrideRepository.findMerchantToMCCOverrideByMerchant("IFOOD"))
                .thenReturn(merchantToMCCOverride);

        Optional<String> result = transactionDataProvider.findMCCByMerchant("IFOOD");

        assertTrue(result.isPresent());
        assertEquals("5411", result.get());
    }

    @Test
    void shouldReturnEmptyWhenMerchantDoesNotExist() {
        when(merchantToMCCOverrideRepository.findMerchantToMCCOverrideByMerchant("UNKNOWN"))
                .thenReturn(null);

        Optional<String> result = transactionDataProvider.findMCCByMerchant("UNKNOWN");

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldSaveTransaction() {
        transactionDataProvider.save(transaction);

        verify(transactionRepository).save(transaction);
    }
}