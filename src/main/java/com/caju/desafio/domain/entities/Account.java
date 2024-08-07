package com.caju.desafio.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "number", nullable = false, unique = true, length = 20)
    private String number;

    @Column(name = "holder_name", nullable = false, length = 255)
    private String holderName;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public Account(Integer id, String number, String holderName) {
        this.id = id;
        this.number = number;
        this.holderName = holderName;
    }

    public Account() {

    }

    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
