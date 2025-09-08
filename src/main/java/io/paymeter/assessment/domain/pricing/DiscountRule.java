package io.paymeter.assessment.domain.pricing;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
public abstract class DiscountRule {

    private Long id;

    private String currency;

    public Pricing pricing;

    public abstract BigDecimal apply(Pricing pricing, BigDecimal basePrice, Duration duration, LocalDateTime from, LocalDateTime to);
}
