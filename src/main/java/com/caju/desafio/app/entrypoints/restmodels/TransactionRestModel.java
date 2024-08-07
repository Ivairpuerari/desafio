package com.caju.desafio.app.entrypoints.restmodels;

import java.math.BigDecimal;

public record TransactionRestModel(String account, BigDecimal totalAmount, String mcc, String merchant) {
}
