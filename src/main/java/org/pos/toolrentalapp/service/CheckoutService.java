package org.pos.toolrentalapp.service;

import org.pos.toolrentalapp.entity.Checkout;
import org.pos.toolrentalapp.exception.ToolsException;
import org.pos.toolrentalapp.responseDto.RentalAgreementResponse;

public interface CheckoutService {
    RentalAgreementResponse checkout(Checkout checkout) throws ToolsException;
}
