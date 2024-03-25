package com.tdd.speciallectureapply.global.response;

public class ApiResponse<T> {
    private boolean success;
    private T response;
    private String error;

    public ApiResponse(boolean success, T response, String error) {
        this.success = success;
        this.response = response;
        this.error = error;
    }

    public static <T> ApiResponse<T> success(T response) {
        return new ApiResponse<>(true, response, null);
    }

    public static <T> ApiResponse<T> failure(String errorMessage) {
        return new ApiResponse<>(false, null, errorMessage);
    }

}
