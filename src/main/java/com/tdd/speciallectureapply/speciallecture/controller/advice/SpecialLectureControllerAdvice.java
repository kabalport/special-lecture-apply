package com.tdd.speciallectureapply.speciallecture.controller.advice;

import com.tdd.speciallectureapply.speciallecture.exception.SpecialLectureException;
import com.tdd.speciallectureapply.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SpecialLectureControllerAdvice {

    @ExceptionHandler(SpecialLectureException.class)
    public ResponseEntity<ApiResponse<Object>> handleSpecialLectureException(SpecialLectureException e) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.failure(e.getMessage()));
    }

}
