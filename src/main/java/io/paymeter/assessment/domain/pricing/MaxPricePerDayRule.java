package io.paymeter.assessment.domain.pricing;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("MAX_PER_DAY")
public class MaxPricePerDayRule extends DiscountRule {

    private BigDecimal maxPrice;

    @Override
    public BigDecimal apply(BigDecimal basePrice, Duration duration, LocalDateTime from, LocalDateTime to) {
        BigDecimal days = BigDecimal.valueOf(Math.ceil(duration.toHours() / 24.0));
        return basePrice.min(maxPrice.multiply(days));
    }
}
