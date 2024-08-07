package com.caju.desafio.app.providers.repositories;

import com.caju.desafio.domain.entities.MerchantToMCCOverride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantToMCCOverrideRepository extends JpaRepository<MerchantToMCCOverride, String> {
    MerchantToMCCOverride findMerchantToMCCOverrideByMerchant(String merchant);
}
