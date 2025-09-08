package io.paymeter.assessment.infraestructure.databaseadapter.entity;

import io.paymeter.assessment.domain.pricing.DiscountRule;
import io.paymeter.assessment.domain.pricing.FreeFirstHourRule;
import io.paymeter.assessment.domain.pricing.MaxPricePerDayRule;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@DiscriminatorValue("MAX_PER_DAY")
public class MaxPricePerDayRuleEntity extends DiscountRuleEntity {


    public MaxPricePerDayRule toDomain() {
        return new MaxPricePerDayRule(this.getMaxPrice());
    }

    public static FreeFirstHourRuleEntity fromDomain(FreeFirstHourRule rule) {
        return FreeFirstHourRuleEntity.fromDomain(rule);
    }

}
