package io.paymeter.assessment.domain.pricing.ports;

import io.paymeter.assessment.infraestructure.controller.dto.in.CalculationRequest;
import io.paymeter.assessment.infraestructure.controller.dto.out.CalculationResponse;

public interface ICalculatingService {

    CalculationResponse calculateTicket(CalculationRequest request);


}
