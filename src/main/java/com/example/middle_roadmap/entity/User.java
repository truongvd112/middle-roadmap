package com.example.middle_roadmap.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "bank_account")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "bank_account_Number")
    private String bankAccountNumber;
    @Column(name = "bank_name")
    private String bankName;
    @Column(name = "money")
    private Long money;

    public void deposit(Long moneyDeposit){
        this.money += moneyDeposit;
    }

    public void withdraw(Long moneyDeposit){
        this.money -= moneyDeposit;
    }
}
