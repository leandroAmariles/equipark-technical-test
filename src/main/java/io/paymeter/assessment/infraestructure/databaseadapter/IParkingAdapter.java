package io.paymeter.assessment.infraestructure.databaseadapter;

import io.paymeter.assessment.domain.pricing.Pricing;

public interface IParkingAdapter {

        Pricing getPricingById(String id);
}
