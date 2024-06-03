package org.pos.toolrentalapp.service.impl;

import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.pos.toolrentalapp.entity.Checkout;
import org.pos.toolrentalapp.entity.Tool;
import org.pos.toolrentalapp.entity.ToolType;
import org.pos.toolrentalapp.exception.ToolsException;
import org.pos.toolrentalapp.repository.ToolsRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class CheckoutServiceImplTest {

    @Mock
    private ToolsRespository toolsRespository;
    @InjectMocks
    private CheckoutServiceImpl checkoutService;

    @Autowired
    private Validator validator;

    Tool chns, ladw, jakd, jakr;
    Checkout checkoutTest1, checkoutTest2, checkoutTest3, checkoutTest4, checkoutTest5, checkoutTest6;

    @BeforeEach
    void setUp() {

        ToolType ladder = new ToolType();
        ladder.setToolTypeName("Ladder");
        ladder.setDailyCharge(1.99);
        ladder.setWeekDayCharge(true);
        ladder.setWeekEndCharge(true);
        ladder.setHolidayCharge(false);


        ToolType chainSaw = new ToolType();
        chainSaw.setToolTypeName("Chainsaw");
        chainSaw.setDailyCharge(1.49);
        chainSaw.setWeekDayCharge(true);
        chainSaw.setWeekEndCharge(false);
        chainSaw.setHolidayCharge(true);


        ToolType jackHammer = new ToolType();
        jackHammer.setToolTypeName("Jackhammer");
        jackHammer.setDailyCharge(2.99);
        jackHammer.setWeekDayCharge(true);
        jackHammer.setWeekEndCharge(false);
        jackHammer.setHolidayCharge(false);


        chns = new Tool(null, "CHNS", "Stihl", chainSaw);
        ladw = new Tool(null, "LADW", "Werner", ladder);
        jakd = new Tool(null, "JAKD", "DeWalt", jackHammer);
        jakr = new Tool(null, "JAKR", "Ridgid", jackHammer);


        // Checkout Payloads for each Test

        checkoutTest1 = Checkout.builder()
                .toolCode("JAKR")
                .checkOutDate(LocalDate.of(2015, 9, 3))
                .rentalDayCount(5)
                .discountPercent(101)
                .build();

        checkoutTest2 = Checkout.builder()
                .toolCode("LADW")
                .checkOutDate(LocalDate.of(2020, 7, 2))
                .rentalDayCount(3)
                .discountPercent(10)
                .build();


        checkoutTest3 = Checkout.builder()
                .toolCode("CHNS")
                .checkOutDate(LocalDate.of(2015, 7, 2))
                .rentalDayCount(5)
                .discountPercent(25)
                .build();

        checkoutTest4 = Checkout.builder()
                .toolCode("JAKD")
                .checkOutDate(LocalDate.of(2015, 9, 3))
                .rentalDayCount(6)
                .discountPercent(0)
                .build();


        checkoutTest5 = Checkout.builder()
                .toolCode("JAKR")
                .checkOutDate(LocalDate.of(2015, 7, 2))
                .rentalDayCount(9)
                .discountPercent(0)
                .build();

        checkoutTest6 = Checkout.builder()
                .toolCode("JAKR")
                .checkOutDate(LocalDate.of(2020, 7, 2))
                .rentalDayCount(4)
                .discountPercent(50)
                .build();
    }

    @Test
    void test1() throws ToolsException {
        //checkout1 has a discountPercent of 101
        var violations = validator.validate(checkoutTest1);

        String expectedMessage = "Discount percent must be at most 100";
        String actualMessage = violations.stream().findFirst().get().getMessage();

        assertNotNull(violations);
        assertEquals(expectedMessage, actualMessage);
    }


    @Test
    void test2() throws ToolsException {
        Mockito.when(toolsRespository.findByToolCode(Mockito.anyString()))
                .thenReturn(Optional.of(ladw));

        var actual = checkoutService.checkout(checkoutTest2);

        assertNotNull(actual);
        assertEquals(checkoutTest2.getToolCode(), actual.getToolCode());
    }


    @Test
    void test3() throws ToolsException {
        Mockito.when(toolsRespository.findByToolCode(Mockito.anyString()))
                .thenReturn(Optional.of(chns));

        var actual = checkoutService.checkout(checkoutTest3);

        assertNotNull(actual);
        assertEquals(checkoutTest3.getToolCode(), actual.getToolCode());
    }


    @Test
    void test4() throws ToolsException {
        Mockito.when(toolsRespository.findByToolCode(Mockito.anyString()))
                .thenReturn(Optional.of(jakd));

        var actual = checkoutService.checkout(checkoutTest4);

        assertNotNull(actual);
        assertEquals(checkoutTest4.getToolCode(), actual.getToolCode());
    }


    @Test
    void test5() throws ToolsException {
        Mockito.when(toolsRespository.findByToolCode(Mockito.anyString()))
                .thenReturn(Optional.of(jakr));

        var actual = checkoutService.checkout(checkoutTest5);

        assertNotNull(actual);
        assertEquals(checkoutTest5.getToolCode(), actual.getToolCode());
    }


    @Test
    void test6() throws ToolsException {
        Mockito.when(toolsRespository.findByToolCode(Mockito.anyString()))
                .thenReturn(Optional.of(jakr));

        var actual = checkoutService.checkout(checkoutTest6);

        assertNotNull(actual);
        assertEquals(checkoutTest6.getToolCode(), actual.getToolCode());
    }


    //Testing ToolsException exception
    @Test
    void checkoutThrowsException() throws ToolsException {
        Mockito.when(toolsRespository.findByToolCode(Mockito.anyString()))
                .thenReturn(Optional.empty());

        Exception exception = assertThrows(ToolsException.class, () -> {
            checkoutService.checkout(checkoutTest1);
        });

        String expectedMessage = "Tool with Code: " + checkoutTest1.getToolCode() + " Not Found!";
        String actualMessage = exception.getMessage();

        assertNotNull(exception);
        assertEquals(actualMessage, expectedMessage);
    }
}
