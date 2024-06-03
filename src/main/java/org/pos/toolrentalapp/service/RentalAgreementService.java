package org.pos.toolrentalapp.service;

import org.pos.toolrentalapp.exception.ToolsException;
import org.pos.toolrentalapp.requestDto.RentalAgreementRequest;
import org.pos.toolrentalapp.responseDto.RentalAgreementResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RentalAgreementService {
    void saveRentalAgreement(RentalAgreementRequest request);

    RentalAgreementResponse findRentalAgreement(Integer rentalAgreementId) throws ToolsException;

    List<RentalAgreementResponse> findAllRentalAgreement();

    Page<RentalAgreementResponse> findAllRentalAgreement(int page, int size);
}
