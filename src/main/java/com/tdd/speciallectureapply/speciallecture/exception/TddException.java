package com.tdd.speciallectureapply.speciallecture.exception;

import lombok.Getter;

@Getter
public class TddException extends RuntimeException {
    TddErrorType tddErrorType;

    public TddException(TddErrorType tddErrorType) {
        this.tddErrorType = tddErrorType;
    }
}

