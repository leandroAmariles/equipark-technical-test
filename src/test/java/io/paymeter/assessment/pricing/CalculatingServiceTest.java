package io.paymeter.assessment.pricing;

import io.paymeter.assessment.applications.services.CalculatingService;
import io.paymeter.assessment.domain.pricing.*;
import io.paymeter.assessment.infraestructure.controller.dto.in.CalculationRequest;
import io.paymeter.assessment.infraestructure.controller.dto.out.CalculationResponse;
import io.paymeter.assessment.infraestructure.databaseadapter.IParkingAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CalculatingServiceTest {

    @Mock
    private IParkingAdapter parkingAdapter;

    @InjectMocks
    private CalculatingService calculatingService;

    private Pricing pricing;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        pricing = new Pricing();
        pricing.setPricingId(456L);
        pricing.setHourlyPrice(BigDecimal.valueOf(3));
        pricing.setCurrencyCode("EUR");
    }

    @Test
    void shouldCalculateTicket_withFreeFirstHourRule() {

        LocalDateTime from = LocalDateTime.of(2025, 9, 7, 10, 0);
        LocalDateTime to = from.plusHours(1);

        DiscountRule freeFirstHour = new FreeFirstHourRule();
        freeFirstHour.setPricing(pricing);
        freeFirstHour.setCurrency("EUR");

        List<DiscountRule> discountRules = new ArrayList<>();
        FreeFirstHourRule freeFirstHourRule = new FreeFirstHourRule();
        discountRules.add(freeFirstHourRule);


        pricing.setDiscountRule(discountRules);

        when(parkingAdapter.getPricingById("456")).thenReturn(pricing);

        pricing.setDiscountRule(List.of(freeFirstHour));

        when(parkingAdapter.getPricingById("456")).thenReturn(pricing);

        CalculationRequest request = new CalculationRequest("456", from.toString(), to.toString());

        CalculationResponse response = calculatingService.calculateTicket(request);

        assertThat(response.price()).isEqualTo("0EUR");
        assertThat(response.duration()).isEqualTo(60);
    }

    @Test
    void shouldCalculateTicket_withFreeFirstHourRuleTwoHours() {
        LocalDateTime from = LocalDateTime.of(2025, 9, 7, 10, 0);
        LocalDateTime to = from.plusMinutes(120);

        DiscountRule freeFirstHour = new FreeFirstHourRule();
        freeFirstHour.setPricing(pricing);
        freeFirstHour.setCurrency("EUR");


        pricing.setDiscountRule(List.of(freeFirstHour));

        when(parkingAdapter.getPricingById("456")).thenReturn(pricing);

        pricing.setDiscountRule(List.of(freeFirstHour));

        when(parkingAdapter.getPricingById("456")).thenReturn(pricing);

        CalculationRequest request = new CalculationRequest("456", from.toString(), to.toString());

        CalculationResponse response = calculatingService.calculateTicket(request);

        assertThat(response.price()).isEqualTo("300EUR");

    }

    @Test
    void shouldCalculateTicket_withMaxPriceRule() {
        LocalDateTime from = LocalDateTime.of(2025, 9, 7, 8, 0);
        LocalDateTime to = from.plusHours(15);

        MaxPricePerDayRule maxRule = new MaxPricePerDayRule();
        maxRule.setPricing(pricing);
        maxRule.setCurrency("EUR");
        maxRule.setMaxPrice(BigDecimal.valueOf(15));

        pricing.setDiscountRule(List.of(maxRule));

        when(parkingAdapter.getPricingById("456")).thenReturn(pricing);

        CalculationRequest request = new CalculationRequest("456", from.toString(), to.toString());

        CalculationResponse response = calculatingService.calculateTicket(request);


        assertThat(response.price()).isEqualTo("1500EUR"); // capped by rule
        assertThat(response.duration()).isEqualTo((int) Duration.between(from, to).toMinutes());
    }

    @Test
    void shouldCalculateTicket_withMaxPriceRuleTwoDays() {

        LocalDateTime from = LocalDateTime.of(2025, 9, 7, 8, 0);
        LocalDateTime to = from.plusHours(48);

        MaxPricePerDayRule maxRule = new MaxPricePerDayRule();
        maxRule.setPricing(pricing);
        maxRule.setCurrency("EUR");
        maxRule.setMaxPrice(BigDecimal.valueOf(15));

        pricing.setDiscountRule(List.of(maxRule));

        when(parkingAdapter.getPricingById("456")).thenReturn(pricing);

        CalculationRequest request = new CalculationRequest("456", from.toString(), to.toString());


        CalculationResponse response = calculatingService.calculateTicket(request);

        assertThat(response.price()).isEqualTo("3000EUR"); // capped by rule
        assertThat(response.duration()).isEqualTo((int) Duration.between(from, to).toMinutes());
    }

    @Test
    void shouldCalculateTicket_withMultipleRules() {

        LocalDateTime from = LocalDateTime.of(2025, 9, 7, 8, 0);
        LocalDateTime to = from.plusHours(13);

        FreeFirstHourRule freeRule = new FreeFirstHourRule();
        freeRule.setPricing(pricing);
        freeRule.setCurrency("EUR");

        MaxPricePerPeriodRule maxRule = new MaxPricePerPeriodRule();
        maxRule.setPricing(pricing);
        maxRule.setCurrency("EUR");
        maxRule.setMaxPrice(BigDecimal.valueOf(20));
        maxRule.setPeriodHours(12);

        pricing.setDiscountRule(List.of(freeRule, maxRule));

        when(parkingAdapter.getPricingById("456")).thenReturn(pricing);

        CalculationRequest request = new CalculationRequest("456", from.toString(), to.toString());


        CalculationResponse response = calculatingService.calculateTicket(request);

        assertThat(response.price()).isEqualTo("2300EUR");
        assertThat(response.duration()).isEqualTo((int) Duration.between(from, to).toMinutes());
    }
}
