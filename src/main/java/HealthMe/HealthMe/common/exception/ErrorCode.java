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
    DUPLICATED_EMAIL(2000, "Duplicated Email", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(2001, "Unauthorized", HttpStatus.UNAUTHORIZED),
    ACCOUNT_NOT_FOUND(2002, "Account Not Found", HttpStatus.UNAUTHORIZED),
    VERIFY_NOT_ALLOWED(2003, "Verify Code Is Not Allowed", HttpStatus.UNAUTHORIZED),
    EMAIL_NOT_FOUND(2003, "Email Not Found", HttpStatus.NOT_FOUND),
    EMAIL_EXSIST(2004, "Email Already Exist", HttpStatus.ALREADY_REPORTED),
    PASSWORD_NOT_MATCH(2005, "Password Not Match", HttpStatus.UNAUTHORIZED),
    PASSWORD_NOT_FOUND(2006, "Password Input Not Found", HttpStatus.NOT_FOUND),
    TIME_OVER(2007, "Auth Time Over", HttpStatus.REQUEST_TIMEOUT),
    /**
     * 3xxx -> 운동 관련 에러
     */
    DATE_NOT_FOUND(3001, "Date Not Found", HttpStatus.NOT_FOUND),
    PRESET_NOT_FOUND(3002, "Preset Not Fount", HttpStatus.NOT_FOUND),
    WEIGHT_NOT_FOUND(3003, "Weight Not Found", HttpStatus.NOT_FOUND),
    SET_COUNT_NOT_FOUND(3004, "Set Count Not Found", HttpStatus.NOT_FOUND),
    REPETITION_COUNT_NOT_FOUND(3005, "Repetition Count Not Found", HttpStatus.NOT_FOUND),
    PRESET_NUMBER_NOT_FOUND(3006, "Preset Number Not Found", HttpStatus.NOT_FOUND),
    EXERCISE_NAME_NOT_FOUND(3007, "Exercise Name Not Found", HttpStatus.NOT_FOUND),
    EXERCISE_NOT_FOUND(3008, "Exercise Not Found", HttpStatus.NOT_FOUND),
    EXERCISE_CATEGORY_NOT_FOUND(3009, "Exercise Category Not Found", HttpStatus.NOT_FOUND),
    /**
     * 4xxx -> 음식 관련 에러
     */
    FOOD_NAME_NOT_FOUND(4001, "Food Name Not Found", HttpStatus.NOT_FOUND),
    FOOD_NOT_FOUND(4002, "Food Not Found In Data Base", HttpStatus.NOT_FOUND),
    FOOD_MASS_NOT_FOUND(4003, "Food Mass Not Selected", HttpStatus.NOT_FOUND),

    /**
     * 5xxx -> jwt 관련 에러
     */
    JWT_EMPTY(5001, "Access Token Is Empty", HttpStatus.UNAUTHORIZED),
    JWT_EXPIRED(5002, "Access Token has expired", HttpStatus.UNAUTHORIZED),
    INVALID_JWT_TOKEN(5003, "Access Token Is Invalid", HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN(5004, "Refresh Token Is Invalid", HttpStatus.UNAUTHORIZED),
    INCORRECT_REFRESH_TOKEN(5005, "Refresh Token Mismatched With User's Refresh Token", HttpStatus.UNAUTHORIZED);
    private final int code;
    private final String message;
    private final HttpStatus httpStatus;
    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;

    }
}
