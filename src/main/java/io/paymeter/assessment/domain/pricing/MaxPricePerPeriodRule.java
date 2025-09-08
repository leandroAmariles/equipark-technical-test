package io.paymeter.assessment.domain.pricing;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaxPricePerPeriodRule extends DiscountRule {

    private BigDecimal maxPrice;
    private int periodHours;


    @Override
    public BigDecimal apply(Pricing pricing,BigDecimal basePrice, Duration duration, LocalDateTime from, LocalDateTime to) {
        long totalMinutes = Duration.between(from, to).toMinutes();
        if (totalMinutes <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal hourlyPrice = pricing.getHourlyPrice();
        int periodMinutes = periodHours * 60;

        long fullPeriods = totalMinutes / periodMinutes;
        long remainderMinutes = totalMinutes % periodMinutes;

        BigDecimal total = BigDecimal.ZERO;

        for (int i = 0; i < fullPeriods; i++) {
            BigDecimal periodCost = hourlyPrice.multiply(BigDecimal.valueOf(periodHours));
            BigDecimal capped = periodCost.min(maxPrice);
            total = total.add(capped);
        }

        if (remainderMinutes > 0) {
            BigDecimal remainderHours = BigDecimal.valueOf(remainderMinutes)
                    .divide(BigDecimal.valueOf(60), 8, RoundingMode.HALF_UP);
            BigDecimal remainderCost = hourlyPrice.multiply(remainderHours);
            BigDecimal capped = remainderCost.min(maxPrice);
            total = total.add(capped);
        }
        return total;
    }
}
