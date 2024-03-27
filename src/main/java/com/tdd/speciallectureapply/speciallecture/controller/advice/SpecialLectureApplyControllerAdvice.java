package com.tdd.speciallectureapply.speciallecture.controller.advice;

import com.tdd.speciallectureapply.speciallecture.exception.SpecialLectureApplyException;
import com.tdd.speciallectureapply.global.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SpecialLectureApplyControllerAdvice {

    @ExceptionHandler(SpecialLectureApplyException.class)
    public ResponseEntity<ApiResponse<Object>> handleSpecialLectureException(SpecialLectureApplyException e) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.failure(e.getMessage()));
    }

}
