package com.caju.desafio.app.entrypoints.restconverters;

import com.caju.desafio.app.entrypoints.restmodels.TransactionRestModel;
import com.caju.desafio.domain.entities.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionRestConverter implements RestConverter<TransactionRestModel, Transaction> {

    @Override
    public Transaction map(TransactionRestModel arg) {
        Transaction transaction = new Transaction();

        transaction.setMerchant(arg.merchant());
        transaction.setTotalAmount(arg.totalAmount());
        transaction.setMcc(arg.mcc());


        return transaction;
    }
}
