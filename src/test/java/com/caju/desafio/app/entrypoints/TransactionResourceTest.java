package com.caju.desafio.app.entrypoints;

import com.caju.desafio.app.entrypoints.restconverters.TransactionRestConverter;
import com.caju.desafio.app.entrypoints.restmodels.ResponseDataRestModel;
import com.caju.desafio.app.entrypoints.restmodels.TransactionRestModel;
import com.caju.desafio.domain.entities.Account;
import com.caju.desafio.domain.entities.Transaction;
import com.caju.desafio.domain.enums.StatusCodeApplicationEnum;
import com.caju.desafio.domain.usecases.GetAccountByNumberUseCase;
import com.caju.desafio.domain.usecases.ProcessTransactionUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionResourceTest {

    @Mock
    private TransactionRestConverter transactionRestConverter;
    @Mock
    private GetAccountByNumberUseCase getAccountByNumberUseCase;
    @Mock
    private ProcessTransactionUseCase processTransactionUseCase;

    private TransactionResource transactionResource;

    private TransactionRestModel transactionRestModel;
    private Account account;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        transactionRestModel = new TransactionRestModel("123", BigDecimal.valueOf(100.00), "5811", "PADARIA DO ZE               SAO PAULO BR");
        account = new Account(123, "123", "Ivair Puerari");
        transaction = new Transaction(null, null, BigDecimal.valueOf(100.00), "5811", "PADARIA DO ZE               SAO PAULO BR");

        transactionResource = new TransactionController(
                this.transactionRestConverter,
                this.getAccountByNumberUseCase,
                this.processTransactionUseCase
        );
    }

    @Test
    public void shouldReturnErrorWhenNotFoundAccountByNumber() {
        when(getAccountByNumberUseCase.execute(transactionRestModel.account())).thenReturn(Optional.empty());

        ResponseEntity<ResponseDataRestModel> response = transactionResource.execute(transactionRestModel);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(StatusCodeApplicationEnum.ERROR.value, Objects.requireNonNull(response.getBody()).code());
    }

    @Test
    public void shouldReturnRejectWhenNotSufficientBalance() {
        when(getAccountByNumberUseCase.execute(transactionRestModel.account())).thenReturn(Optional.of(account));
        when(processTransactionUseCase.execute(transaction)).thenReturn(StatusCodeApplicationEnum.REJECT);
        when(transactionRestConverter.map(transactionRestModel)).thenReturn(transaction);

        ResponseEntity<ResponseDataRestModel> response = transactionResource.execute(transactionRestModel);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(StatusCodeApplicationEnum.REJECT.value, Objects.requireNonNull(response.getBody()).code());
    }

    @Test
    public void shouldReturnSuccessWhenProcessTransaction() {
        when(getAccountByNumberUseCase.execute(transactionRestModel.account())).thenReturn(Optional.of(account));
        when(processTransactionUseCase.execute(transaction)).thenReturn(StatusCodeApplicationEnum.SUCCESS);
        when(transactionRestConverter.map(transactionRestModel)).thenReturn(transaction);

        ResponseEntity<ResponseDataRestModel> response = transactionResource.execute(transactionRestModel);

        assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
        assertEquals(StatusCodeApplicationEnum.SUCCESS.value, Objects.requireNonNull(response.getBody()).code());
    }
}