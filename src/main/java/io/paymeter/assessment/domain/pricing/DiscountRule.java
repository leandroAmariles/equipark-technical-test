package io.paymeter.assessment.domain.pricing;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "rule_type")
public abstract class DiscountRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String currency;

    @ManyToOne
    @JoinColumn(name = "pricingId", nullable = false)
    public Pricing pricing;

    public abstract BigDecimal apply(BigDecimal basePrice, Duration duration, LocalDateTime from, LocalDateTime to);
}
