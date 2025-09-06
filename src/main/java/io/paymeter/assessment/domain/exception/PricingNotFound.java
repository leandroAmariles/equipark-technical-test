package io.paymeter.assessment.domain.exception;

public class PricingNotFound extends RuntimeException {
    public PricingNotFound(String message) {
        super(message);
    }
}
