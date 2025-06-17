package be.ddd.infra.exception;

import java.time.Instant;

public record ErrorResponse(String message, int status, Instant timestamp, String path) {

    public static ErrorResponse of(String message, int status, Instant timestamp, String path) {
        return new ErrorResponse(message, status, timestamp, path);
    }
}
