package com.tdd.speciallectureapply.speciallecture.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class SpecialLectureResponse {
    private LocalDate date;
    private int currentApplications;
}


