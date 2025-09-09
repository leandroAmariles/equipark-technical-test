package io.paymeter.assessment.infraestructure.controller.dto.in;

import jakarta.validation.constraints.NotNull;

public record CalculationRequest(

        @NotNull
        String parkingId,

        @NotNull
        String from

) {
}
