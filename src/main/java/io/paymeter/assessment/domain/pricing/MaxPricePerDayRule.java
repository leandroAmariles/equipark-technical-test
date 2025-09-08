package io.paymeter.assessment.domain.pricing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaxPricePerDayRule extends DiscountRule {

    private BigDecimal maxPrice;

    @Override
    public BigDecimal apply(Pricing pricing,BigDecimal basePrice, Duration duration, LocalDateTime from, LocalDateTime to) {
        BigDecimal days = BigDecimal.valueOf(Math.ceil(duration.toHours() / 24.0));
        return basePrice.min(maxPrice.multiply(days));
    }
}
