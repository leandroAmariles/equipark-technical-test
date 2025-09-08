INSERT INTO pricing (pricing_id, hourly_price, currency_code)
VALUES (123, 2.00, 'EUR');

INSERT INTO pricing (pricing_id, hourly_price, currency_code)
VALUES (456, 3.00, 'EUR');


INSERT INTO discount_rule (id, rule_type, currency, pricing_id, max_price, period_hours)
VALUES (1, 'MAX_PER_DAY', 'EUR', 123, 15.00, null);

INSERT INTO discount_rule (id, rule_type, currency, pricing_id, max_price, period_hours)
VALUES (2, 'FREE_FIRST_HOUR', 'EUR', 456, 20.00, null);

INSERT INTO discount_rule (id, rule_type, currency, pricing_id, max_price, period_hours)
VALUES (3, 'MAX_PER_PERIOD', 'EUR', 456, 20.00, 12);


