package org.pos.toolrentalapp.repository;

import org.pos.toolrentalapp.entity.RentalAgreement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RentalAgreementRepositoryPaging extends PagingAndSortingRepository<RentalAgreement, Integer> {
}
