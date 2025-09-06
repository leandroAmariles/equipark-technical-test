package io.paymeter.assessment.applications.services;

import io.paymeter.assessment.domain.pricing.DiscountRule;
import io.paymeter.assessment.domain.pricing.Pricing;
import io.paymeter.assessment.domain.pricing.ports.ICalculatingService;
import io.paymeter.assessment.infraestructure.controller.dto.in.CalculationRequest;
import io.paymeter.assessment.infraestructure.controller.dto.out.CalculationResponse;
import io.paymeter.assessment.infraestructure.databaseadapter.IParkingAdapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CalculatingService implements ICalculatingService {

    private final IParkingAdapter parkingAdapter;


    @Override
    public CalculationResponse calculateTicket(CalculationRequest request) {
        LocalDateTime dateTimeFrom = LocalDateTime.parse(request.from(), DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime dateTimeTo = LocalDateTime.parse(request.to(), DateTimeFormatter.ISO_DATE_TIME);
        Pricing parking = parkingAdapter.getPricingById(request.parkingId());
        int minutes = calculateMinutes(dateTimeFrom, dateTimeTo);

        BigDecimal price = BigDecimal.ZERO;
        BigDecimal basePrice = BigDecimal.ZERO;

        parking.getDiscountRule().forEach(discountRule -> {
            price.add(discountRule.apply(basePrice, minutes, ));
        });

        return null;
    }

    private int calculateMinutes(LocalDateTime from, LocalDateTime to) {
        return (int) java.time.Duration.between(from, to).toHours();
    }
}


