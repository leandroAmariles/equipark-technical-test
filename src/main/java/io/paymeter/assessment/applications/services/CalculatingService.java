package io.paymeter.assessment.applications.services;

import io.paymeter.assessment.domain.pricing.DiscountRule;
import io.paymeter.assessment.domain.pricing.Money;
import io.paymeter.assessment.domain.pricing.Pricing;
import io.paymeter.assessment.domain.pricing.ports.ICalculatingService;
import io.paymeter.assessment.infraestructure.controller.dto.in.CalculationRequest;
import io.paymeter.assessment.infraestructure.controller.dto.out.CalculationResponse;
import io.paymeter.assessment.infraestructure.databaseadapter.IParkingAdapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CalculatingService implements ICalculatingService {

    private final IParkingAdapter parkingAdapter;


    @Override
    public CalculationResponse calculateTicket(CalculationRequest request) {
        LocalDateTime dateTimeFrom = LocalDateTime.parse(request.from(), DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime dateTimeTo = LocalDateTime.now();
        Pricing parking = parkingAdapter.getPricingById(request.parkingId());
        Duration duration = calculateDuration(dateTimeFrom, dateTimeTo);

        BigDecimal hourlyPrice = parking.getHourlyPrice();
        long minutes = duration.toMinutes();

        BigDecimal finalPrice;
        if (minutes < 1) {
            finalPrice = BigDecimal.ZERO;
        } else {
            BigDecimal basePrice = hourlyPrice
                    .multiply(BigDecimal.valueOf(minutes))
                    .divide(BigDecimal.valueOf(60), 8, RoundingMode.HALF_UP);

            List<BigDecimal> candidates = new ArrayList<>();
            if (parking.getDiscountRule() != null) {
                for (DiscountRule rule : parking.getDiscountRule()) {
                    BigDecimal candidate = rule.apply(parking, basePrice, duration, dateTimeFrom, dateTimeTo);
                    if (candidate != null) {
                        candidates.add(candidate);
                    }
                }
            }

            candidates.add(basePrice);
            finalPrice = candidates.stream()
                    .min(BigDecimal::compareTo)
                    .orElse(BigDecimal.ZERO);
        }

        Money money = new Money(finalPrice.intValue());
        return CalculationResponse.builder()
                .parkingId(request.parkingId())
                .from(dateTimeFrom.toString())
                .to(dateTimeTo.toString())
                .duration((int) duration.toMinutes())
                .price(money.generatePriceFormat(finalPrice))
                .build();
    }

    private Duration calculateDuration(LocalDateTime from, LocalDateTime to) {
        return Duration.between(from, to);
    }
}


