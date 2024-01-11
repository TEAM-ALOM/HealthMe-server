package HealthMe.HealthMe.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class CustomException extends Exception{
    private ErrorCode errorCode;
    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage()); // Set the error message here
        this.errorCode = errorCode;
    }
}
