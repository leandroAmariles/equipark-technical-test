CREATE TABLE discount_rule
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    rule_type    VARCHAR(31) NOT NULL,
    currency     VARCHAR(255) NULL,
    pricing_id   BIGINT      NOT NULL,
    max_price    DECIMAL NULL,
    period_hours INT         NULL,
    CONSTRAINT pk_discountrule PRIMARY KEY (id)
);

ALTER TABLE discount_rule
    ADD CONSTRAINT FK_DISCOUNTRULE_ON_PRICINGID FOREIGN KEY (pricing_id) REFERENCES pricing (pricing_id);