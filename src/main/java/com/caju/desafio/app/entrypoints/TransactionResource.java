package com.caju.desafio.app.entrypoints;

import com.caju.desafio.app.entrypoints.restmodels.ResponseDataRestModel;
import com.caju.desafio.app.entrypoints.restmodels.TransactionRestModel;
import org.springframework.http.ResponseEntity;

public interface TransactionResource {
     ResponseEntity<ResponseDataRestModel> execute(TransactionRestModel transactionRestModel);
}
