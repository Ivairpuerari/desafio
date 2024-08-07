package com.caju.desafio.app.providers.repositories;

import com.caju.desafio.domain.entities.BalanceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceCategoryRepository extends JpaRepository<BalanceCategory, Integer> {
    BalanceCategory findBalanceCategoryByAccount_IdAndCategoryName(Integer accountId, String categoryName);
}
