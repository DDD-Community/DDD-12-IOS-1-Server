package be.ddd.common.dto;

public record ApiResponse<T>(String message, int status, T data) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("OK", 200, data);
    }

    public static <T> ApiResponse<T> of(String message, int status, T data) {
        return new ApiResponse<>(message, status, data);
    }
}
