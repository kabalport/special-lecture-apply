package com.tdd.speciallectureapply.speciallecture.model.dto.response;

import com.tdd.speciallectureapply.speciallecture.model.ApplyStatus;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class SpecialLectureApplyResponse {

    private Long applyId;
    private LocalDate specialLectureDate;
    private String userId;
//    private ApplyStatus specialLectureApplyStatus;

//    public SpecialLectureApplyResponse(Long applyId, LocalDate specialLectureDate, String userId, ApplyStatus specialLectureApplyStatus) {
////    }

    // Constructors, Getters and Setters
}
