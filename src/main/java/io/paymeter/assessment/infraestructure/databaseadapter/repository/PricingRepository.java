package io.paymeter.assessment.infraestructure.databaseadapter.repository;

import io.paymeter.assessment.domain.pricing.Pricing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PricingRepository extends JpaRepository<Pricing,Long> {

    Pricing get(String parkingId);
}
