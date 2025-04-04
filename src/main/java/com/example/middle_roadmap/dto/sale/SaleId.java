package com.example.middle_roadmap.dto.sale;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Embeddable
public class SaleId {
    private Long id;
    private LocalDate saleDate;
}
