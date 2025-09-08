package io.paymeter.assessment.infraestructure.databaseadapter.entity;

import io.paymeter.assessment.domain.pricing.FreeFirstHourRule;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("FREE_FIRST_HOUR")
public class FreeFirstHourRuleEntity extends DiscountRuleEntity {


    public FreeFirstHourRule toDomain() {
        return new FreeFirstHourRule();
    }

    public static FreeFirstHourRuleEntity fromDomain(FreeFirstHourRule rule) {
        return new FreeFirstHourRuleEntity();
    }
}
