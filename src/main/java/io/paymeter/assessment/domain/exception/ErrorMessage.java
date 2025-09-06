package io.paymeter.assessment.domain.exception;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorMessage {

    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
