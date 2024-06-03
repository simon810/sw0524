package org.pos.toolrentalapp.repository;

import org.pos.toolrentalapp.entity.RentalAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalAgreementRepository extends JpaRepository<RentalAgreement, Integer> {
}
