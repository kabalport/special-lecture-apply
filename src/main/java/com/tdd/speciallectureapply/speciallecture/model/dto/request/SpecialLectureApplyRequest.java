package com.tdd.speciallectureapply.speciallecture.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
@Getter
@AllArgsConstructor
public class SpecialLectureApplyRequest {
    private LocalDate specialLectureDate;
    private String userId;


}