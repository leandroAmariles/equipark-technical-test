package io.paymeter.assessment.domain.pricing;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class FreeFirstHourRule extends DiscountRule {


    @Override
    public BigDecimal apply(Pricing pricing,BigDecimal basePrice, Duration duration, LocalDateTime from, LocalDateTime to) {
        if (duration.toMinutes() <= 60) {
            return BigDecimal.ZERO;
        }
        return basePrice.subtract(pricing.getHourlyPrice());
    }
}
