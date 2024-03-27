package com.tdd.speciallectureapply.speciallecture.exception;

import com.tdd.speciallectureapply.speciallecture.model.dto.SpecialLectureApplyErrorType;
import lombok.Getter;

@Getter
public class SpecialLectureApplyException extends RuntimeException {

    SpecialLectureApplyErrorType specialLectureApplyErrorType;

    public SpecialLectureApplyException(SpecialLectureApplyErrorType specialLectureApplyErrorType) {
        this.specialLectureApplyErrorType = specialLectureApplyErrorType;
    }
    public SpecialLectureApplyException(String message) {
        super(message);
    }
}
