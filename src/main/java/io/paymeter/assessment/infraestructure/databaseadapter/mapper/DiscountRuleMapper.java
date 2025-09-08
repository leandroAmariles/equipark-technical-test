package io.paymeter.assessment.infraestructure.databaseadapter.mapper;

import io.paymeter.assessment.domain.pricing.DiscountRule;
import io.paymeter.assessment.domain.pricing.FreeFirstHourRule;
import io.paymeter.assessment.domain.pricing.MaxPricePerDayRule;
import io.paymeter.assessment.domain.pricing.MaxPricePerPeriodRule;
import io.paymeter.assessment.infraestructure.databaseadapter.entity.DiscountRuleEntity;
import io.paymeter.assessment.infraestructure.databaseadapter.entity.FreeFirstHourRuleEntity;
import io.paymeter.assessment.infraestructure.databaseadapter.entity.MaxPricePerDayRuleEntity;
import io.paymeter.assessment.infraestructure.databaseadapter.entity.MaxPricePerPeriodRuleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface DiscountRuleMapper {


    @Mapping(target = "pricing", ignore = true)
    FreeFirstHourRule toDomain(FreeFirstHourRuleEntity entity);

    @Mapping(target = "pricing", ignore = true)
    MaxPricePerDayRule toDomain(MaxPricePerDayRuleEntity entity);

    @Mapping(target = "pricing", ignore = true)
    MaxPricePerPeriodRule toDomain(MaxPricePerPeriodRuleEntity entity);

    default DiscountRule toDomain(DiscountRuleEntity entity) {
        if (entity instanceof FreeFirstHourRuleEntity e) {
            return toDomain(e);
        }
        if (entity instanceof MaxPricePerDayRuleEntity e) {
            return toDomain(e);
        }
        if (entity instanceof MaxPricePerPeriodRuleEntity e) {
            return toDomain(e);
        }
        throw new IllegalArgumentException("Unknown entity type: " + entity.getClass());
    }



    default List<DiscountRule> toDomainList(List<DiscountRuleEntity> entities) {
        if (entities == null) return null;
        return entities.stream().map(this::toDomain).toList();
    }
}
