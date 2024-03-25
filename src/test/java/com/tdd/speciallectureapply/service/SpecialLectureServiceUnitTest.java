// 나중에 리팩토링
//**특강 선택 API**
//- 단 한번의 특강을 위한 것이 아닌 날짜별로 특강이 존재할 수 있는 범용적인 서비스로 변화시켜 봅니다.
//- 이를 수용하기 위해, 특강 엔티티의 경우 기존의 설계에서 변경되어야 합니다.
//- 특강의 정원은 30명으로 고정이며, 사용자는 각 특강에 신청하기전 목록을 조회해볼 수 있어야 합니다.
package com.tdd.speciallectureapply.service;

import com.tdd.speciallectureapply.model.SpecialLectureFixture;
import com.tdd.speciallectureapply.speciallecture.model.dto.request.SpecialLectureApplyRequest;
import com.tdd.speciallectureapply.speciallecture.model.dto.response.SpecialLectureApplyResponse;
import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLecture;
import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLectureApply;
import com.tdd.speciallectureapply.speciallecture.repository.SpecialLectureApplyRepository;
import com.tdd.speciallectureapply.speciallecture.repository.SpecialLectureRepository;
import com.tdd.speciallectureapply.speciallecture.service.SpecialLectureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.tdd.speciallectureapply.model.SpecialLectureFixture.april20Lecture;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class SpecialLectureServiceUnitTest {

    private SpecialLectureService specialLectureService;
    private SpecialLectureApplyRepository specialLectureApplyRepository;
    private SpecialLectureRepository specialLectureRepository;

    @BeforeEach
    public void beforeEach() {
        specialLectureApplyRepository = Mockito.mock(SpecialLectureApplyRepository.class);
        specialLectureRepository = Mockito.mock(SpecialLectureRepository.class);
        specialLectureService =
                new SpecialLectureService(specialLectureApplyRepository, specialLectureRepository);
    }


//  특강 신청
// - 특정 userId 로 선착순으로 제공되는 특강을 신청하는 API 를 작성합니다.
// - 동일한 신청자는 한 번의 수강 신청만 성공할 수 있습니다.
//- 특강은 `4월 20일 토요일 1시` 에 열리며, 선착순 30명만 신청 가능합니다.
//- 이미 신청자가 30명이 초과되면 이후 신청자는 요청을 실패합니다

    @DisplayName("신청-24년4월20일토요일1시에 열린 특강 / 한명도 신청하지 않았을 경우")
    @Test
    public void OneApplyAprilTest() {
        // given
        SpecialLecture test = SpecialLectureFixture.april20Lecture();

        // when
//        studentScoreService.saveScore(
//                givenStudentName, givenExam, givenKorScore, givenEnglishScore, givenMathScore);
    }


// (기본)** 특강 신청 완료 여부 조회 API
//

// 특정 userId 로 특강 신청 완료 여부를 조회하는 API 를 작성합니다.
// 특강 신청에 성공한 사용자는 성공했음을, 특강 등록자 명단에 없는 사용자는 실패했음을 반환하는지 테스트

// 정확하게 30 명의 사용자에게만 특강을 제공하고 있는지 테스트
// 같은 사용자에게 여러 번의 특강 슬롯이 제공되지 않도록 제한하고 있는지 테스트





    @Test
    public void whenApplyForLecture_givenAvailableLecture_thenSucceeds() {
        // Given
        LocalDateTime lectureDateTime = LocalDateTime.of(2023, 4, 20, 13, 0);
        SpecialLecture specialLecture = april20Lecture();
        SpecialLectureApplyRequest request = new SpecialLectureApplyRequest(LocalDate.now(),"123");
        // Set request fields appropriately
        when(specialLectureRepository.findBySpecialLectureDate(lectureDateTime.toLocalDate()))
                .thenReturn(Optional.of(specialLecture));
        when(specialLectureApplyRepository.save(any(SpecialLectureApply.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // When
        SpecialLectureApplyResponse response = specialLectureService.applyForLecture(request);

        // Then
        assertNotNull(response);
        assertEquals(request.getUserId(), response.getUserId());
        // Additional assertions can be made here
    }

//    @Test
//    public void whenApplyForLecture_givenFullLecture_thenThrowsException() {
//        // Given
//        LocalDateTime lectureDateTime = LocalDateTime.of(2023, 4, 20, 13, 0);
//        SpecialLecture specialLecture = SpecialLectureFixture.createSpecialLecture(1L, lectureDateTime.toLocalDate(), 30, 30);
//        SpecialLectureApplyRequest request = new SpecialLectureApplyRequest();
//        // Set request fields appropriately
//        when(specialLectureRepository.findBySpecialLectureDate(lectureDateTime.toLocalDate()))
//                .thenReturn(Optional.of(specialLecture));
//
//        // When & Then
//        assertThrows(SpecialLectureException.class, () -> {
//            specialLectureService.applyForLecture(request);
//        });
//    }

    // Additional test cases here
}
