package com.tdd.speciallectureapply.speciallecture.model.dto.response;

import com.tdd.speciallectureapply.speciallecture.model.common.ApplyStatus;

public class SpecialLectureApplyStatusResponse {

    private String userId;
    private ApplyStatus specialLectureApplyStatus;
    private String message;

    public SpecialLectureApplyStatusResponse() {
    }

    public SpecialLectureApplyStatusResponse(String userId, ApplyStatus specialLectureApplyStatus, String message) {
        this.userId = userId;
        this.message = message;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ApplyStatus getSpecialLectureApplyStatus() {
        return specialLectureApplyStatus;
    }

    public void setSpecialLectureApplyStatus(ApplyStatus specialLectureApplyStatus) {
        this.specialLectureApplyStatus = specialLectureApplyStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
