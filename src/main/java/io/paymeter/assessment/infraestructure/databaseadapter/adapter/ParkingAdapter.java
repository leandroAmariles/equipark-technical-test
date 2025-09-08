package io.paymeter.assessment.infraestructure.databaseadapter.adapter;

import io.paymeter.assessment.domain.exception.PricingNotFound;
import io.paymeter.assessment.domain.pricing.Pricing;
import io.paymeter.assessment.infraestructure.databaseadapter.IParkingAdapter;
import io.paymeter.assessment.infraestructure.databaseadapter.entity.PricingEntity;
import io.paymeter.assessment.infraestructure.databaseadapter.mapper.DiscountRuleMapper;
import io.paymeter.assessment.infraestructure.databaseadapter.mapper.PricingMapper;
import io.paymeter.assessment.infraestructure.databaseadapter.repository.PricingRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingAdapter implements IParkingAdapter {

    private final PricingRepository pricingRepository;

    private final PricingMapper pricingMapper;



    @Override
    public Pricing getPricingById(String id) {
        Long pricingId = Long.parseLong(id.substring(1));
        PricingEntity pricing = pricingRepository.findByPricingId(pricingId)
                .orElseThrow(() -> new PricingNotFound("Pricing with id " + id + " not found"));
        return pricingMapper.toDomain(pricing);
    }
}
