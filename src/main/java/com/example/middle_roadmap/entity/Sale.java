package com.example.middle_roadmap.entity;

import com.example.middle_roadmap.dto.sale.SaleId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Getter
@Setter
@Table(name = "sales")
public class Sale {
    @EmbeddedId
    private SaleId saleId;
    @NotNull
    @Column(name = "amount")
    private BigInteger amount;
}
