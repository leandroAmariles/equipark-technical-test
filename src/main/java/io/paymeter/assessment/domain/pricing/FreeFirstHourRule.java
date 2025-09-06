package io.paymeter.assessment.domain.pricing;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("FREE_FIRST_HOUR")
public class FreeFirstHourRule extends DiscountRule {


    @Override
    public BigDecimal apply(BigDecimal basePrice, Duration duration, LocalDateTime from, LocalDateTime to) {
        if (duration.toMinutes() <= 60) {
            return BigDecimal.ZERO;
        }
        return basePrice.subtract(pricing.getHourlyPrice());
    }
}
