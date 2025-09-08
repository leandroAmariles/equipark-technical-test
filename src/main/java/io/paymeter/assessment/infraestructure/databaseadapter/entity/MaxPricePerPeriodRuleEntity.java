package io.paymeter.assessment.infraestructure.databaseadapter.entity;

import io.paymeter.assessment.domain.pricing.DiscountRule;
import io.paymeter.assessment.domain.pricing.MaxPricePerPeriodRule;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@DiscriminatorValue("MAX_PER_PERIOD")
public class MaxPricePerPeriodRuleEntity extends DiscountRuleEntity {


    public MaxPricePerPeriodRule toDomain() {
        return new MaxPricePerPeriodRule(this.getMaxPrice(), this.getPeriodHours());
    }

    public static MaxPricePerPeriodRuleEntity fromDomain(MaxPricePerPeriodRule rule) {
        MaxPricePerPeriodRuleEntity entity = new MaxPricePerPeriodRuleEntity();
        entity.setMaxPrice(rule.getMaxPrice());
        entity.setPeriodHours(rule.getPeriodHours());
        return entity;
    }

}
