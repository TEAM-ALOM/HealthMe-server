package HealthMe.HealthMe.common.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {
    /**
     * 1xxx -> Common 에러
     */
    BAD_REQUEST(1000, "Bad Request", HttpStatus.BAD_REQUEST),
    NOT_FOUND(1001, "Contents Not Found", HttpStatus.NOT_FOUND),
    METHOD_NOT_ALLOWED(1002, "Method Not Allowed", HttpStatus.METHOD_NOT_ALLOWED),
    INTERNAL_SERVER_ERROR(1003, "Internal Server Error Occurred", HttpStatus.INTERNAL_SERVER_ERROR),
    METHOD_ARGUMENT_NOT_VALID(1004, "Method Argument Is Not Valid", HttpStatus.BAD_REQUEST),
    OBJECT_NOT_FOUND(1005, "Object Is Null", HttpStatus.NOT_FOUND),

    /**
     * 2xxx -> 계정 에러
     */
    DUPLICATED_LOGIN_ID(2000, "Duplicated Login Id", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(2001, "Unauthorized", HttpStatus.UNAUTHORIZED),
    ACCOUNT_NOT_FOUND(2002, "Account Not Found", HttpStatus.UNAUTHORIZED),
    VERIFY_NOT_ALLOWED(2003, "Verify Code Is Not Allowed", HttpStatus.UNAUTHORIZED),
    EMAIL_NOT_FOUND(2003, "Email Not Found", HttpStatus.NOT_FOUND),
    EMAIL_EXSIST(2004, "Email Already Exist", HttpStatus.ALREADY_REPORTED),
    /**
     * 3xxx -> 운동 관련 에러
     */
    DATE_NOT_FOUND(3001, "Date Not Found", HttpStatus.NOT_FOUND);
    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;

    }
}
