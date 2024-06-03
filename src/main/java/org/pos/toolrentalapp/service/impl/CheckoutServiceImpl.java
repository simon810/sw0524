package org.pos.toolrentalapp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.pos.toolrentalapp.entity.Checkout;
import org.pos.toolrentalapp.entity.RentalAgreement;
import org.pos.toolrentalapp.exception.ToolsException;
import org.pos.toolrentalapp.repository.ToolsRespository;
import org.pos.toolrentalapp.requestDto.RentalAgreementRequest;
import org.pos.toolrentalapp.responseDto.RentalAgreementResponse;
import org.pos.toolrentalapp.service.CheckoutService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Service
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {

    private final ToolsRespository toolsRespository;
    private final ObjectMapper objectMapper;

    public CheckoutServiceImpl(ToolsRespository toolsRespository, ObjectMapper objectMapper) {
        this.toolsRespository = toolsRespository;
        this.objectMapper = objectMapper;
    }

    @Override
    public RentalAgreementResponse checkout(Checkout checkout) throws ToolsException {
        return rentalAgreementResponseBuilder(checkout);
    }



    private RentalAgreementResponse rentalAgreementResponseBuilder(Checkout checkout) throws ToolsException {
        var tool = toolsRespository.findByToolCode(checkout.getToolCode())
                .orElseThrow(() -> new ToolsException("Tool with Code: " + checkout.getToolCode() + " Not Found!"));

        var toolType = tool.getToolType();

        LocalDate checkoutDate = checkout.getCheckOutDate();
        LocalDate dueDate = checkoutDate.plusDays(checkout.getRentalDayCount());

        log.info("Checkout Date: {} , Due Date: {}", checkoutDate, dueDate);

        //1. Independence Day, July 4
        //Evaluate Independence Day only if the checkout or due dates are in July of the year.

        LocalDate closestHoliday = null;
        if (checkoutDate.getMonth().equals(Month.JULY) || dueDate.getMonth().equals(Month.JULY)) {
            var july4 = Year.of(checkoutDate.getYear()).atMonthDay(MonthDay.of(Month.JULY, 4));
            if (july4.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
                closestHoliday = july4.minusDays(1);
            } else if (july4.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                closestHoliday = july4.plusDays(1);
            } else {
                closestHoliday = july4;
            }
        }


        //2. Labor Day- First Monday in September
        // Evaluate Labor Day only if the checkout or due dates are in September of the year.

        LocalDate laborDay = null;
        if (checkoutDate.getMonth().equals(Month.SEPTEMBER) || dueDate.getMonth().equals(Month.SEPTEMBER)) {
            for (int i = 1; i <= 7; i++) {
                var date = Year.of(checkoutDate.getYear()).atMonth(Month.SEPTEMBER).atDay(i);
                if (date.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
                    laborDay = date;
                    break;
                }
            }
        }


        Set<LocalDate> rentalDates = new LinkedHashSet<>();
        for (int i = 1; i <= checkout.getRentalDayCount(); i++) {
            var currentDate = checkoutDate.plusDays(i);


            if ((Objects.nonNull(closestHoliday) && currentDate.isEqual(closestHoliday)) && tool.getToolType().getHolidayCharge())
                rentalDates.add(currentDate);

            else if ((currentDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) || currentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) &&
                    tool.getToolType().getWeekEndCharge())
                rentalDates.add(currentDate);

            else if ((Objects.nonNull(laborDay) && currentDate.isEqual(laborDay)) && tool.getToolType().getHolidayCharge())
                rentalDates.add(currentDate);

            else if (!currentDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) && toolType.getWeekDayCharge()) {
                if (!currentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                    if (Objects.isNull(closestHoliday) || !currentDate.isEqual(closestHoliday)) {
                        if (Objects.isNull(laborDay) || !currentDate.isEqual(laborDay)) {
                            rentalDates.add(currentDate);
                        }
                    }
                }
            }
        }


        BigDecimal preDiscountCharge = BigDecimal.valueOf(rentalDates.size() * toolType.getDailyCharge()).setScale(2, RoundingMode.HALF_UP);

        BigDecimal discountAmount = preDiscountCharge.multiply(BigDecimal.valueOf(checkout.getDiscountPercent() / 100.0)).setScale(2, RoundingMode.HALF_UP);

        BigDecimal finalCharge = preDiscountCharge.subtract(discountAmount);

        var rentalAgreement = RentalAgreementResponse.builder()
                .toolCode(checkout.getToolCode())
                .toolType(toolType.getToolTypeName())
                .brand(tool.getBrand())
                .rentalDays(checkout.getRentalDayCount())
                .checkoutDate(checkoutDate)
                .dueDate(dueDate)
                .dailyRentalCharge(BigDecimal.valueOf(toolType.getDailyCharge()))
                .chargeDays(rentalDates.size())
                .preDiscountCharge(preDiscountCharge)
                .discountPercent(checkout.getDiscountPercent())
                .discountAmount(discountAmount)
                .finalCharge(finalCharge)
                .build();


        //Print Rental Agreement values as text to the console
        printToConsole(rentalAgreement);

        return rentalAgreement;
    }

    private void printToConsole(RentalAgreementResponse renetalAgreement) {


        System.out.printf("----------------------------------------%n");
        System.out.printf("    Rental Agreement Report             %n");
        System.out.printf("----------------------------------------%n");


        System.out.printf("| %-20s | %-13s |%n", "Name", "Value");

        System.out.printf("----------------------------------------%n");


        System.out.printf("| %-20s | %-13s |%n", "Tool code", renetalAgreement.getToolCode());
        System.out.printf("| %-20s | %-13s |%n", "Tool type", renetalAgreement.getToolType());
        System.out.printf("| %-20s | %-13s |%n", "Brand", renetalAgreement.getBrand());
        System.out.printf("| %-20s | %-13s |%n", "Rental days", renetalAgreement.getRentalDays());
        System.out.printf("| %-20s | %-13s |%n", "Checkout date", renetalAgreement.getCheckoutDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        System.out.printf("| %-20s | %-13s |%n", "Due date", renetalAgreement.getDueDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
        System.out.printf("| %-20s | %-13s |%n", "Daily rental charge", "$" + renetalAgreement.getDailyRentalCharge());
        System.out.printf("| %-20s | %-13s |%n", "Charge days", renetalAgreement.getChargeDays());
        System.out.printf("| %-20s | %-13s |%n", "Pre-discount charge", "$" + renetalAgreement.getPreDiscountCharge());
        System.out.printf("| %-20s | %-13s |%n", "Discount percent", renetalAgreement.getDiscountPercent() + "%");
        System.out.printf("| %-20s | %-13s |%n", "Discount amount", "$" + renetalAgreement.getDiscountAmount());
        System.out.printf("| %-20s | %-13s |%n", "Final charge", "$" + renetalAgreement.getFinalCharge());


        System.out.printf("----------------------------------------%n");
    }
}
