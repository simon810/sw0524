package org.pos.toolrentalapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Operation(
            summary = "Checkout tool",
            description = "Checkout a tool with the specified details",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Checkout request",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Checkout.class),
                            examples = @ExampleObject(
                                    name = "example",
                                    value = "{ \"toolCode\": \"CHNS\", \"rentalDayCount\": 5, \"discountPercent\": 10, \"checkOutDate\": \"09/02/2015\" }"
                            )
                    )
            )
    )
    public RentalAgreementResponse checkout(@RequestBody @Valid Checkout checkout) throws ToolsException {
        return checkoutService.checkout(checkout);
    }
}
