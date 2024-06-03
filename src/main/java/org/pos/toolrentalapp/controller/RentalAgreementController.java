package org.pos.toolrentalapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.pos.toolrentalapp.exception.ToolsException;
import org.pos.toolrentalapp.requestDto.RentalAgreementRequest;
import org.pos.toolrentalapp.responseDto.RentalAgreementResponse;
import org.pos.toolrentalapp.service.RentalAgreementService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/rental-agreement")
@Tag(name = "Rental Agreement")
public class RentalAgreementController {
    private final RentalAgreementService rentalAgreementService;

    public RentalAgreementController(RentalAgreementService rentalAgreementService) {
        this.rentalAgreementService = rentalAgreementService;
    }

    @PostMapping("/save-agreement")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveRentalAgreement(@RequestBody RentalAgreementRequest request) {
        rentalAgreementService.saveRentalAgreement(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public RentalAgreementResponse findRentalAgreement(@RequestParam Integer id) throws ToolsException {
        return rentalAgreementService.findRentalAgreement(id);
    }


    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<RentalAgreementResponse> findRentalAllAgreement() {
        return rentalAgreementService.findAllRentalAgreement();
    }

    @GetMapping("/pages")
    @ResponseStatus(HttpStatus.OK)
    public Page<RentalAgreementResponse> findRentalAllAgreementPages(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return rentalAgreementService.findAllRentalAgreement(page, size);
    }

}
