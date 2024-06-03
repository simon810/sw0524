package org.pos.toolrentalapp.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Checkout {
    private String toolCode;
    @Min(value = 1, message = "Rental day count must be 1 or greater")
    private Integer rentalDayCount;
    @Min(value = 0, message = "Discount percent must be at least 0")
    @Max(value = 100, message = "Discount percent must be at most 100")
    private Integer discountPercent;
    private LocalDate checkOutDate;
}
