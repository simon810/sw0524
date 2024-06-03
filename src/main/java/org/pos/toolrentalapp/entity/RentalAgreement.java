package org.pos.toolrentalapp.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class RentalAgreement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer rentalAgreementId;
    private String toolCode;
    private String toolType;
    private String brand;
    private Integer rentalDays;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private BigDecimal dailyRentalCharge;
    private Integer chargeDays;
    private BigDecimal preDiscountCharge;
    private Integer discountPercent;
    private BigDecimal discountAmount;
    private BigDecimal finalCharge;
}
