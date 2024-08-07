package com.caju.desafio.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "merchant_to_mcc_overrides")
public class MerchantToMCCOverride {
    @Id
    @Column(name = "merchant", length = 255)
    private String merchant;

    @Column(name = "mcc", nullable = false, length = 4)
    private String mcc;

    public MerchantToMCCOverride() {
    }

    public MerchantToMCCOverride(String merchant, String mcc) {
        this.merchant = merchant;
        this.mcc = mcc;
    }

    // Getters e Setters
    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }
}
