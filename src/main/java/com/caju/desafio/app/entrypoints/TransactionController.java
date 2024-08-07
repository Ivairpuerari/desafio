package com.caju.desafio.app.entrypoints;

import com.caju.desafio.app.entrypoints.restconverters.TransactionRestConverter;
import com.caju.desafio.app.entrypoints.restmodels.ResponseDataRestModel;
import com.caju.desafio.app.entrypoints.restmodels.TransactionRestModel;
import com.caju.desafio.domain.entities.Account;
import com.caju.desafio.domain.entities.Transaction;
import com.caju.desafio.domain.enums.StatusCodeApplicationEnum;
import com.caju.desafio.domain.usecases.GetAccountByNumberUseCase;
import com.caju.desafio.domain.usecases.ProcessTransactionUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController implements TransactionResource {

    private final TransactionRestConverter transactionRestConverter;
    private final GetAccountByNumberUseCase getAccountByNumberUseCase;
    private final ProcessTransactionUseCase processTransactionUseCase;

    public TransactionController(TransactionRestConverter transactionRestConverter, GetAccountByNumberUseCase getAccountByNumberUseCase, ProcessTransactionUseCase processTransactionUseCase) {
        this.transactionRestConverter = transactionRestConverter;
        this.getAccountByNumberUseCase = getAccountByNumberUseCase;
        this.processTransactionUseCase = processTransactionUseCase;
    }

    @PostMapping
    @Operation(summary = "Authorize a transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS when code is 00",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDataRestModel.class)) })})
    @Override
    public ResponseEntity<ResponseDataRestModel> execute(@RequestBody TransactionRestModel transactionRestModel) {
        final Optional<Account> account = this.getAccountByNumberUseCase.execute(transactionRestModel.account());

        if (account.isEmpty())
            return ResponseEntity.ok(new ResponseDataRestModel(StatusCodeApplicationEnum.ERROR.value));

        final Transaction transaction = this.transactionRestConverter.map(transactionRestModel);
        transaction.setAccount(account.get());

        StatusCodeApplicationEnum statusCodeApplicationEnum;

        try {
            statusCodeApplicationEnum = this.processTransactionUseCase.execute(transaction);
        } catch (Exception e) {
            statusCodeApplicationEnum = StatusCodeApplicationEnum.ERROR;
        }

        return ResponseEntity.ok(new ResponseDataRestModel(statusCodeApplicationEnum.value));
    }
}
