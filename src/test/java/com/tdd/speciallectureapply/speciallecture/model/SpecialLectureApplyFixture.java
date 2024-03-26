package com.tdd.speciallectureapply.speciallecture.model;

import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLecture;
import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLectureApply;

import java.time.LocalDate;

public class SpecialLectureApplyFixture {

    // 기본 특강 신청 객체 생성 메소드
    public static SpecialLectureApply create(LocalDate lectureDate, String userId) {
        return SpecialLectureApply.builder()
                .specialLectureDate(lectureDate)
                .userId(userId)
                .build();
    }

    // 신청이 수락된 특강 신청 객체 생성 메소드
    //passed

    // 신청이 거부된 특강 신청 객체 생성 메소드
    //failed
}
