package com.tdd.speciallectureapply.speciallecture.model;

import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLecture;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SpecialLectureFixture {


    public static SpecialLecture create(LocalDate lectureDate) {
        return SpecialLecture.builder()
                .date(lectureDate)
                .maxCapacity(30)
                .currentApplications(0)
                .build();
    }

    public static SpecialLecture april20Lecture() {
        return SpecialLecture.builder()
                .date(LocalDate.from(LocalDateTime.of(2024, 4, 20, 13, 0)))
                .maxCapacity(30)
                .currentApplications(0)
                .build();
    }
    public static SpecialLecture april20LectureFull() {
        return SpecialLecture.builder()
                .date(LocalDate.from(LocalDateTime.of(2024, 4, 20, 13, 0)))
                .maxCapacity(30)
                .currentApplications(30)
                .build();
    }
    public static SpecialLecture april20LectureFullBefore() {
        return SpecialLecture.builder()
                .date(LocalDate.from(LocalDateTime.of(2024, 4, 20, 13, 0)))
                .maxCapacity(30)
                .currentApplications(29)
                .build();
    }

    public static SpecialLecture failedMaxCapacity() {
        LocalDate lectureDate = LocalDate.from(LocalDateTime.of(2024, 4, 20, 13, 0));
        return SpecialLecture.builder()
                .date(lectureDate)
                .maxCapacity(30)
                .currentApplications(30) // 정원이 꽉 찬 상태
                .build();
    }

    public static SpecialLecture withAvailableSpots(LocalDate lectureDate, int maxCapacity, int currentApplications) {
        return SpecialLecture.builder()
                .date(lectureDate)
                .maxCapacity(maxCapacity)
                .currentApplications(currentApplications) // 정원에 여유가 있는 상태
                .build();
    }
}
