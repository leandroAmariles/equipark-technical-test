package io.paymeter.assessment.infraestructure.controller.dto.in;

public record CalculationRequest(

        String parkingId,

        String from,

        String to

) {
}
