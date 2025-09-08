package io.paymeter.assessment.infraestructure.databaseadapter.mapper;

import io.paymeter.assessment.domain.pricing.Pricing;
import io.paymeter.assessment.infraestructure.databaseadapter.entity.PricingEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DiscountRuleMapper.class})
public interface PricingMapper {

    Pricing toDomain(PricingEntity entity);
}
