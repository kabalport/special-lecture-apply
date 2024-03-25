package com.tdd.speciallectureapply.model;

import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLecture;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SpecialLectureFixture {


    public static SpecialLecture create(LocalDate lectureDate) {
        return SpecialLecture.builder()
                .specialLectureDate(lectureDate)
                .maxCapacity(30) // 최대 정원 30명
                .currentApplications(0) // 초기 상태는 신청자 없음
                .build();
    }

    // 4월 20일 특강에 대한 Fixture 생성 메소드
    public static SpecialLecture april20Lecture() {
        return SpecialLecture.builder()
                .specialLectureDate(LocalDate.from(LocalDateTime.of(2024, 4, 20, 13, 0)))
                .maxCapacity(30) // 최대 정원 30명
                .currentApplications(0) // 현재 신청 인원은 0명으로 시작
                .build();
    }

    public static SpecialLecture failedMaxCapacity() {
        LocalDate lectureDate = LocalDate.from(LocalDateTime.of(2024, 4, 20, 13, 0));
        return SpecialLecture.builder()
                .specialLectureDate(lectureDate)
                .maxCapacity(30)
                .currentApplications(30) // 정원이 꽉 찬 상태
                .build();
    }

    public static SpecialLecture withAvailableSpots(LocalDate lectureDate, int maxCapacity, int currentApplications) {
        return SpecialLecture.builder()
                .specialLectureDate(lectureDate)
                .maxCapacity(maxCapacity)
                .currentApplications(currentApplications) // 정원에 여유가 있는 상태
                .build();
    }
}
