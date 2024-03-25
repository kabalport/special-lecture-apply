package com.tdd.speciallectureapply.model;

import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLecture;
import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLectureApply;

import java.time.LocalDate;

public class SpecialLectureApplyFixture {

    // 기본 특강 신청 객체 생성 메소드
    public static SpecialLectureApply createSpecialLectureApply(LocalDate lectureDate, String userId,  SpecialLecture specialLecture) {
        return new SpecialLectureApply(
                null, // ID는 생성 시 데이터베이스에서 자동으로 할당됨
                lectureDate,
                userId,

                specialLecture
        );
    }


    public static SpecialLectureApply create(LocalDate lectureDate, String userId) {
        return SpecialLectureApply.builder()
                .specialLectureDate(lectureDate)
                .userId(userId)
                .build();
    }

    // 신청이 수락된 특강 신청 객체 생성 메소드
    public static SpecialLectureApply acceptedApplication(LocalDate lectureDate, String userId, SpecialLecture specialLecture) {
        return createSpecialLectureApply(lectureDate, userId, specialLecture);
    }

    // 신청이 거부된 특강 신청 객체 생성 메소드
    public static SpecialLectureApply rejectedApplication(LocalDate lectureDate, String userId, SpecialLecture specialLecture) {
        return createSpecialLectureApply(lectureDate, userId, specialLecture);
    }
}
