package io.paymeter.assessment.infraestructure.databaseadapter.entity;

import io.paymeter.assessment.domain.pricing.DiscountRule;
import io.paymeter.assessment.domain.pricing.Pricing;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "rule_type")
@Table(name = "discount_rule")
public abstract class DiscountRuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String currency;

    @Column(name = "max_price")
    private BigDecimal maxPrice;

    @Column(name = "period_hours")
    private Integer periodHours;

    @ManyToOne
    @JoinColumn(name = "pricing_id", nullable = false)
    public PricingEntity pricing;

    public abstract DiscountRule toDomain();


}
