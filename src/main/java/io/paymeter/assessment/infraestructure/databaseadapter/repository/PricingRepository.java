package io.paymeter.assessment.infraestructure.databaseadapter.repository;

import io.paymeter.assessment.domain.pricing.Pricing;
import io.paymeter.assessment.infraestructure.databaseadapter.entity.PricingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PricingRepository extends JpaRepository<PricingEntity,Long> {

    Optional<PricingEntity> findByPricingId(Long pricingId);
}
