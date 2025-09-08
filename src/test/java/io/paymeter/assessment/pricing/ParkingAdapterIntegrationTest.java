package io.paymeter.assessment.pricing;

import io.paymeter.assessment.domain.exception.PricingNotFound;
import io.paymeter.assessment.domain.pricing.Pricing;
import io.paymeter.assessment.infraestructure.databaseadapter.adapter.ParkingAdapter;
import io.paymeter.assessment.infraestructure.databaseadapter.entity.PricingEntity;
import io.paymeter.assessment.infraestructure.databaseadapter.repository.PricingRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ParkingAdapterIntegrationTest {



    @Autowired
    private ParkingAdapter parkingAdapter;

    @Autowired
    private PricingRepository pricingRepository;

    @Test
    void testGetPricingById_success() {

        PricingEntity entity = new PricingEntity();
        entity.setPricingId(123L);
        entity.setHourlyPrice(BigDecimal.TEN);
        pricingRepository.save(entity);


        Pricing pricing = parkingAdapter.getPricingById("P000123");


        assertNotNull(pricing);
        assertEquals(123L, pricing.getPricingId());
        assertEquals(BigDecimal.TEN, pricing.getHourlyPrice());
    }

    @Test
    void testGetPricingById_notFound() {

        assertThrows(PricingNotFound.class, () -> parkingAdapter.getPricingById("P999"));
    }
}
