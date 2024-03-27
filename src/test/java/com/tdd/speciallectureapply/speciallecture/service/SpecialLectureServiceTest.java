package com.tdd.speciallectureapply.speciallecture.service;

import com.tdd.speciallectureapply.speciallecture.model.SpecialLectureFixture;
import com.tdd.speciallectureapply.speciallecture.model.dto.response.SpecialLectureResponse;
import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLecture;
import com.tdd.speciallectureapply.speciallecture.repository.SpecialLectureApplyRepository;
import com.tdd.speciallectureapply.speciallecture.repository.SpecialLectureRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 사용자는 특강목록을 조회할 수 있습니다.
 */

public class SpecialLectureServiceTest {
    private SpecialLectureRepository specialLectureRepository;
    private SpecialLectureService sut;
    @BeforeEach
    public void beforeEach() {
        specialLectureRepository = Mockito.mock(SpecialLectureRepository.class);
        sut =
                new SpecialLectureService(specialLectureRepository);
    }

    @DisplayName("특강 목록 조회")
    @Test
    public void 특강목록_조회_성공() {
        // given
        LocalDate givenDate = LocalDate.of(2024, 3, 27);
        SpecialLecture expect1 = SpecialLectureFixture.april20Lecture();
        SpecialLecture expect2 = SpecialLectureFixture.create(givenDate);


        Mockito.when(specialLectureRepository.findAll())
                .thenReturn(List.of(expect1, expect2));

        // when
        var expectResponse =
                List.of(expect1, expect2).stream()
                        .map((list) -> new SpecialLectureResponse(list.getDate(), list.getCurrentApplications()))
                        .toList();
        List<SpecialLectureResponse> responses = sut.getSpecialLectureList();

        //then
        Assertions.assertIterableEquals(expectResponse, responses);
        expectResponse.stream().forEach(expectRespons -> System.out.println(expectRespons.getDate()));
        responses.stream().forEach(response -> System.out.println(response.getDate()));
        assertEquals(2, responses.size(), "특강 목록의 크기가 예상과 다릅니다.");
    }
}
