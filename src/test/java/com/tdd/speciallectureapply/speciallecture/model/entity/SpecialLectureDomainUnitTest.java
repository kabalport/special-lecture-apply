package com.tdd.speciallectureapply.speciallecture.model.entity;

import com.tdd.speciallectureapply.speciallecture.exception.SpecialLectureException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SpecialLectureDomainUnitTest {

    @Test
    public void 정원테스트_30명미만() {
        // given
        SpecialLecture lecture = SpecialLecture.builder()
                .date(LocalDate.now())
                .maxCapacity(30)
                .currentApplications(29)
                .build();

        // when
        lecture.increaseCurrentApplications();

        // then
        assertEquals(30, lecture.getCurrentApplications(), "현재 신청 인원 수가 정상적으로 증가해야 합니다.");
    }

    @Test
    public void 정원테스트_30명초과_예외발생() {
        // given
        SpecialLecture lecture = SpecialLecture.builder()
                .date(LocalDate.now())
                .maxCapacity(30)
                .currentApplications(30)
                .build();

        // when & then
        Exception exception = assertThrows(SpecialLectureException.class, lecture::increaseCurrentApplications,
                "정원이 이미 초과되었을 때, SpecialLectureException이 발생해야 합니다.");
        System.out.println(exception);
    }
}
