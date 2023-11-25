package HealthMe.HealthMe.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustonException extends Exception{
    private ErrorCode errorCode;
}
