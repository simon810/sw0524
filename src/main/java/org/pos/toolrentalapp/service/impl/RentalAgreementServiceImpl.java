package org.pos.toolrentalapp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.pos.toolrentalapp.entity.RentalAgreement;
import org.pos.toolrentalapp.exception.ToolsException;
import org.pos.toolrentalapp.repository.RentalAgreementRepository;
import org.pos.toolrentalapp.repository.RentalAgreementRepositoryPaging;
import org.pos.toolrentalapp.requestDto.RentalAgreementRequest;
import org.pos.toolrentalapp.responseDto.RentalAgreementResponse;
import org.pos.toolrentalapp.service.RentalAgreementService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalAgreementServiceImpl implements RentalAgreementService {
    private final RentalAgreementRepository rentalAgreementRepository;
    private final RentalAgreementRepositoryPaging rentalAgreementRepositoryPaging;
    private final ObjectMapper objectMapper;

    public RentalAgreementServiceImpl(RentalAgreementRepository rentalAgreementRepository, RentalAgreementRepositoryPaging rentalAgreementRepositoryPaging, ObjectMapper objectMapper) {
        this.rentalAgreementRepository = rentalAgreementRepository;
        this.rentalAgreementRepositoryPaging = rentalAgreementRepositoryPaging;
        this.objectMapper = objectMapper;
    }

    @Override
    public void saveRentalAgreement(RentalAgreementRequest request) {
        RentalAgreement rentalAgreement = objectMapper.convertValue(request, RentalAgreement.class);
        rentalAgreementRepository.save(rentalAgreement);
    }

    @Override
    public RentalAgreementResponse findRentalAgreement(Integer rentalAgreementId) throws ToolsException {
        var rentalAgreementEntity = rentalAgreementRepository.findById(rentalAgreementId)
                .orElseThrow(() -> new ToolsException("Rental Agreement with id: " + rentalAgreementId + " Not Found!"));
        return objectMapper.convertValue(rentalAgreementEntity, RentalAgreementResponse.class);
    }

    @Override
    public List<RentalAgreementResponse> findAllRentalAgreement() {
        return rentalAgreementRepository
                .findAll().stream()
                .map(agreement -> objectMapper.convertValue(agreement, RentalAgreementResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<RentalAgreementResponse> findAllRentalAgreement(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return rentalAgreementRepositoryPaging
                .findAll(pageable)
                .map(agreement -> objectMapper.convertValue(agreement, RentalAgreementResponse.class));
    }
}
