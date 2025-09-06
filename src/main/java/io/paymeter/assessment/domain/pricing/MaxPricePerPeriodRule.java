package io.paymeter.assessment.domain.pricing;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("MAX_PER_PERIOD")
public class MaxPricePerPeriodRule extends DiscountRule {

    private BigDecimal maxPrice;
    private int periodHours;

    @Override
    public BigDecimal apply(BigDecimal basePrice, Duration duration, LocalDateTime from, LocalDateTime to) {
        long periods = (long) Math.ceil((double) duration.toHours() / periodHours);
        return basePrice.min(maxPrice.multiply(BigDecimal.valueOf(periods)));
    }
}
