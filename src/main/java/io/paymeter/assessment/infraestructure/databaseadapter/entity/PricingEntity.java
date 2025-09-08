package io.paymeter.assessment.infraestructure.databaseadapter.entity;


import io.paymeter.assessment.domain.pricing.DiscountRule;
import io.paymeter.assessment.domain.pricing.Pricing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pricing")
public class PricingEntity {

    @Id
    @Column(name = "pricing_id")
    private Long pricingId;

    @Column(name = "hourly_price")
    private BigDecimal hourlyPrice;

    @Column(name = "currency_code")
    private String currencyCode;

    @OneToMany(mappedBy = "pricing", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DiscountRuleEntity> discountRule;


    public Pricing toDomain(){
        Pricing pricing = new Pricing();
        pricing.setPricingId(this.pricingId);
        pricing.setHourlyPrice(this.hourlyPrice);
        pricing.setCurrencyCode(this.currencyCode);
        if(this.discountRule != null){
            List<DiscountRule> rules = this.discountRule.stream().map(DiscountRuleEntity::toDomain).toList();
            pricing.setDiscountRule(rules);
        }
        return pricing;
    }

}
