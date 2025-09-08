CREATE TABLE pricing
(
    pricing_id    BIGINT NOT NULL,
    hourly_price  DECIMAL NULL,
    currency_code VARCHAR(255) NULL,
    CONSTRAINT pk_pricing PRIMARY KEY (pricing_id)
);