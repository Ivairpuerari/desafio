package com.caju.desafio.domain.usecases;

import com.caju.desafio.domain.dataproviders.TransactionDataProvider;
import com.caju.desafio.domain.entities.BalanceCategory;
import com.caju.desafio.domain.enums.CategoryBalanceEnum;
import com.caju.desafio.domain.services.GetMCCByMerchantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetMCCByMerchantUseCaseTest {

    @Mock
    private TransactionDataProvider transactionDataProvider;
    private GetMCCByMerchantUseCase getMCCByMerchantUseCase;

    @BeforeEach
    void setUp() {
        this.getMCCByMerchantUseCase = new GetMCCByMerchantService(
                this.transactionDataProvider
        );
    }

    @Test
    public void shouldReturnMCCWhenFindMCCByMerchant() {
        String merchant = "IFOOD";
        String mcc = "5512";

        when(this.transactionDataProvider.findMCCByMerchant(merchant)).thenReturn(Optional.of(mcc));

        Optional<String> mccResult = this.getMCCByMerchantUseCase.execute(merchant);

        assertTrue(mccResult.isPresent());
    }

    @Test
    public void shouldEmptyWhenFindMCCByMerchant() {
        String merchant = "IFOOD";

        when(this.transactionDataProvider.findMCCByMerchant(merchant)).thenReturn(Optional.empty());

        Optional<String> mccResult = this.getMCCByMerchantUseCase.execute(merchant);

        assertTrue(mccResult.isEmpty());
    }
}