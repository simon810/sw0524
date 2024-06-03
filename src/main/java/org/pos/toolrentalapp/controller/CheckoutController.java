package org.pos.toolrentalapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.pos.toolrentalapp.entity.Checkout;
import org.pos.toolrentalapp.exception.ToolsException;
import org.pos.toolrentalapp.responseDto.RentalAgreementResponse;
import org.pos.toolrentalapp.service.CheckoutService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/checkout")
@Tag(name = "Checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public RentalAgreementResponse checkout(@RequestBody @Valid Checkout checkout) throws ToolsException {
        return checkoutService.checkout(checkout);
    }
}
