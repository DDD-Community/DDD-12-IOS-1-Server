package be.ddd.infra.exception;

import be.ddd.domain.exception.CafeBeverageNotFoundException;
import java.util.Set;
import lombok.Getter;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Getter
public enum ErrorCode {

    // Common
    INTERNAL_SERVER_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 생겼습니다. 관리자에게 문의하세요.", Set.of()),
    INVALID_INPUT_VALUE(
            HttpStatus.BAD_REQUEST,
            "입력 값이 올바르지 않습니다.",
            Set.of(MethodArgumentNotValidException.class, ConstraintViolationException.class)),
    METHOD_NOT_ALLOWED(
            HttpStatus.METHOD_NOT_ALLOWED,
            "지원하지 않는 HTTP 메서드입니다.",
            Set.of(HttpRequestMethodNotSupportedException.class)),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다.", Set.of()),

    CAFE_BEVERAGE_NOT_FOUND(
            HttpStatus.NOT_FOUND, "카페 음료를 찾을 수 없습니다.", Set.of(CafeBeverageNotFoundException.class));

    private final HttpStatusCode status;
    private final String code;
    private final String message;
    private final Set<Class<? extends Exception>> exceptions;

    ErrorCode(
            HttpStatusCode status,
            String code,
            String message,
            Set<Class<? extends Exception>> exceptions) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.exceptions = exceptions;
    }

    ErrorCode(HttpStatusCode status, String message, Set<Class<? extends Exception>> exceptions) {
        this.status = status;
        this.code = String.valueOf(status.value());
        this.message = message;
        this.exceptions = exceptions;
    }
}
