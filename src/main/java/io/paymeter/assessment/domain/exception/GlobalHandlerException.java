package io.paymeter.assessment.domain.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;

import java.util.Date;

@ControllerAdvice
public class GlobalHandlerException {


    @ExceptionHandler(PricingNotFound.class)
    public ResponseEntity<ErrorMessage> handlePricingException(PricingNotFound ex, ServerWebExchange exchange) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .status(400)
                .message(ex.getMessage())
                .timestamp(String.valueOf(new Date()))
                .error("Error from client service")
                .path(exchange.getRequest().getPath().value())
                .build();
        return ResponseEntity.status(404).body(errorMessage);
    }
}
