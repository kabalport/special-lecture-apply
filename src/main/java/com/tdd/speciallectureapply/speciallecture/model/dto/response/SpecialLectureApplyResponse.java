package com.tdd.speciallectureapply.speciallecture.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class SpecialLectureApplyResponse {
    private LocalDate specialLectureDate;
    private String userId;
}
