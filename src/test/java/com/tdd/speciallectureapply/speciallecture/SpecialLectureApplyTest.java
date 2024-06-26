package com.tdd.speciallectureapply.speciallecture;

import com.tdd.speciallectureapply.speciallecture.exception.SpecialLectureApplyException;
import com.tdd.speciallectureapply.speciallecture.model.SpecialLectureFixture;
import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLecture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SpecialLectureApplyTest {


    @DisplayName("특강신청시 현재신청자가 30명으로 정원업데이트 /2024년 4월20일 13일특강의 정원은 현재 29명이다")
    @Test
    public void 정원테스트_30명미만() {
        // given
        SpecialLecture lecture = SpecialLectureFixture.april20LectureFullBefore();
        // when
        lecture.increaseCurrentApplications();
        // then
        assertEquals(30, lecture.getCurrentApplications(), "현재 신청 인원 수가 정상적으로 증가해야 합니다.");
    }

    @Test
    @DisplayName("특강신청시 신청정원 30명초과 예외발생/2024년 4월20일 13일특강의 정원은 현재 30명이다")
    public void 정원테스트_30명초과_예외발생() {
        // given
        SpecialLecture lecture = SpecialLectureFixture.april20LectureFull();


        // when & then
        Exception exception = assertThrows(SpecialLectureApplyException.class, lecture::increaseCurrentApplications,
                "정원이 이미 초과되었을 때, SpecialLectureException이 발생해야 합니다.");
        System.out.println(exception);
    }

    @DisplayName("특강 신청을 30번 반복하여 정원 업데이트 검증")
    @Test
    void 특강신청_30번반복하여_정원업데이트_검증() {
        // given
        SpecialLecture lecture = SpecialLectureFixture.april20Lecture();

        // when
        for (int i = 0; i < 30; i++) {
            lecture.increaseCurrentApplications();
        }

        // then
        assertEquals(30, lecture.getCurrentApplications(), "2024년 4월 20일 특강의 정원은 현재 30명이어야 합니다.");
    }
}
