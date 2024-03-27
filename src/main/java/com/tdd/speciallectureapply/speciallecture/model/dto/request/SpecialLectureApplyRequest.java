package com.tdd.speciallectureapply.speciallecture.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Getter
@AllArgsConstructor
public class SpecialLectureApplyRequest {

//    @NotNull(message = "신청 날짜는 필수입니다.")
//    @Future(message = "신청 날짜는 미래 날짜여야 합니다.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate applyDate;

//    @NotBlank(message = "사용자 ID는 비어 있을 수 없습니다.")
    private String userId;
}