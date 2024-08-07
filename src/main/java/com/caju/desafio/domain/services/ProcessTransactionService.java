package com.caju.desafio.domain.services;

import com.caju.desafio.domain.entities.BalanceCategory;
import com.caju.desafio.domain.entities.Transaction;
import com.caju.desafio.domain.enums.CategoryBalanceEnum;
import com.caju.desafio.domain.enums.StatusCodeApplicationEnum;
import com.caju.desafio.domain.usecases.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProcessTransactionService implements ProcessTransactionUseCase {

    private final GetMCCByMerchantUseCase getMCCByMerchantUseCase;
    private final GetCategoryByMCCUseCase getCategoryByMCCUseCase;
    private final GetBalanceByAccountIdAndCategoryUseCase getBalanceByAccountIdAndCategoryUseCase;
    private final DebitBalanceTransactionUseCase debitBalanceTransactionUseCase;

    public ProcessTransactionService(GetMCCByMerchantUseCase getMCCByMerchantUseCase,
                                     GetCategoryByMCCUseCase getCategoryByMCCUseCase,
                                     GetBalanceByAccountIdAndCategoryUseCase getBalanceByAccountIdAndCategoryUseCase,
                                     DebitBalanceTransactionUseCase debitBalanceTransactionUseCase
    ) {
        this.getMCCByMerchantUseCase = getMCCByMerchantUseCase;
        this.getCategoryByMCCUseCase = getCategoryByMCCUseCase;
        this.getBalanceByAccountIdAndCategoryUseCase = getBalanceByAccountIdAndCategoryUseCase;
        this.debitBalanceTransactionUseCase = debitBalanceTransactionUseCase;
    }


    @Override
    public StatusCodeApplicationEnum execute(Transaction transaction) {

        this.getMCCByMerchantUseCase.execute(transaction.getMerchant()).ifPresent(
                transaction::setMcc
        );

        CategoryBalanceEnum categoryBalanceEnum = this.getCategoryByMCCUseCase.execute(transaction.getMcc());

        Optional<BalanceCategory> balanceCategory = this.getBalanceByAccountIdAndCategoryUseCase.execute(
                transaction.getAccount().getId(), categoryBalanceEnum
        );

        if (balanceCategory.isPresent() && balanceCategory.get().getAmount().compareTo(transaction.getTotalAmount()) >= 0) {
            this.debitBalanceTransactionUseCase.execute(transaction, balanceCategory.get());

            return StatusCodeApplicationEnum.SUCCESS;
        }

        if (balanceCategory.isEmpty() || !balanceCategory.get().getCategoryName().equals(CategoryBalanceEnum.CASH.name())) {
            Optional<BalanceCategory> cashBalanceOpt = this.getBalanceByAccountIdAndCategoryUseCase.execute(transaction.getAccount().getId(), CategoryBalanceEnum.CASH);
            if (cashBalanceOpt.isPresent() && cashBalanceOpt.get().getAmount().compareTo(transaction.getTotalAmount()) >= 0) {
                this.debitBalanceTransactionUseCase.execute(transaction, cashBalanceOpt.get());

                return StatusCodeApplicationEnum.SUCCESS;
            }
        }

        return StatusCodeApplicationEnum.REJECT;
    }
}
