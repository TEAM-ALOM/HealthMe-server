package HealthMe.HealthMe.common.exception;

import HealthMe.HealthMe.common.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;


// 11/25 추가 : 글로벌 익셉션 핸들러
@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(CustonException.class)
    protected ResponseEntity<ErrorResponseDto> handleCommonException(
            final CustonException e,
            final HttpServletRequest request
            ){
        log.error("CustomException : {} {}", e.getErrorCode(), request.getRequestURI());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(new ErrorResponseDto(e.getErrorCode()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException(
            final MethodArgumentNotValidException e,
            final HttpServletRequest request
    ) {
        log.error("MethodArgumentNotValidException: {} {}", e.getMessage(), request.getRequestURL());
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .code(ErrorCode.METHOD_ARGUMENT_NOT_VALID.getCode())
                .status(ErrorCode.METHOD_ARGUMENT_NOT_VALID.getHttpStatus().value())
                .error(ErrorCode.METHOD_ARGUMENT_NOT_VALID.getHttpStatus().name())
                .message(e.getAllErrors().get(0).getDefaultMessage()).build();

        return ResponseEntity
                .status(ErrorCode.METHOD_ARGUMENT_NOT_VALID.getHttpStatus().value())
                .body(errorResponseDto);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponseDto> handleMethodNotSupportException(
            final HttpRequestMethodNotSupportedException e
    ) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .code(ErrorCode.METHOD_NOT_ALLOWED.getCode())
                .status(ErrorCode.METHOD_NOT_ALLOWED.getHttpStatus().value())
                .error(ErrorCode.METHOD_NOT_ALLOWED.getHttpStatus().name())
                .message(e.getMessage()).build();

        return ResponseEntity
                .status(ErrorCode.METHOD_NOT_ALLOWED.getHttpStatus().value())
                .body(errorResponseDto);
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class, SQLException.class, DataIntegrityViolationException.class})
    protected ResponseEntity<ErrorResponseDto> handleInternalException(
            final Exception e,
            final HttpServletRequest request
    ) {
        log.error("Exception: {} {}", e.getMessage(), request.getRequestURL());
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus().value())
                .body(new ErrorResponseDto(ErrorCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<ErrorResponseDto> handleNullPointerException(
            final NullPointerException e,
            final HttpServletRequest request
    ){
        log.info("NullPointerException : {} {}", e.getMessage(), request.getRequestURI());
        return ResponseEntity
                .status(ErrorCode.OBJECT_NOT_FOUND.getHttpStatus())
                .body(new ErrorResponseDto(ErrorCode.OBJECT_NOT_FOUND));
    }
}
