package org.pos.toolrentalapp.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentalAgreementRequest {
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
