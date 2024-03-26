// 나중에 리팩토링
//**특강 선택 API**
//- 단 한번의 특강을 위한 것이 아닌 날짜별로 특강이 존재할 수 있는 범용적인 서비스로 변화시켜 봅니다.
//- 이를 수용하기 위해, 특강 엔티티의 경우 기존의 설계에서 변경되어야 합니다.
//- 특강의 정원은 30명으로 고정이며, 사용자는 각 특강에 신청하기전 목록을 조회해볼 수 있어야 합니다.

// 특강생성
// 특강 신청자목록
package com.tdd.speciallectureapply.speciallecture.service;

import com.tdd.speciallectureapply.speciallecture.model.SpecialLectureApplyFixture;
import com.tdd.speciallectureapply.speciallecture.model.SpecialLectureFixture;
import com.tdd.speciallectureapply.speciallecture.exception.SpecialLectureException;
import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLecture;
import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLectureApply;
import com.tdd.speciallectureapply.speciallecture.repository.SpecialLectureApplyRepository;
import com.tdd.speciallectureapply.speciallecture.repository.SpecialLectureRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
/**
 * 특강신청
 *  특정 userId 로 선착순으로 제공되는 특강을 신청하는 API 를 작성합니다.
 *  동일한 신청자는 한 번의 수강 신청만 성공할 수 있습니다.
 *  특강은 `4월 20일 토요일 1시` 에 열리며, 선착순 30명만 신청 가능합니다.
 *  이미 신청자가 30명이 초과되면 이후 신청자는 요청을 실패합니다
 */
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


    @DisplayName("실패-신청날짜유효성검증/특강신청한 날짜가 과거인 경우 '신청 날짜가 유효하지않습니다.' 라는 예외를 던집니다.")
    @Test
    public void 실패_신청날짜검증_신청날이오늘보다과거() {
        // given: 현재 날짜보다 이전인 날짜로 특강 신청 시도
        String givenUser = "test";
        LocalDate pastDate = LocalDate.now().minusDays(1); // 오늘 날짜에서 하루를 뺀 과거 날짜

        // when: applyLecture 메서드를 호출할 때 이 과거 날짜를 사용하고, 결과로 발생하는 예외를 캡처
        Exception exception = assertThrows(SpecialLectureException.class, () -> {
            specialLectureService.applyLecture(pastDate, givenUser);
        });

        // then: 예외 메시지가 "신청 날짜가 지났습니다."인지 확인
        assertEquals("신청 날짜가 유효하지않습니다.", exception.getMessage());
    }


    @DisplayName("실패-특강날짜검증/특강신청한 날짜에 특강이 없는경우 특강이없습니다. 라는 예외를 던집니다.")
    @Test
    public void 실패_특강날짜검증_신청날짜에특강이존재하지않음(){
        // given
        String givenUser= "test";
        SpecialLecture expectSpecialLecture = SpecialLectureFixture.create(LocalDate.from(LocalDateTime.of(2024, 4, 21, 13, 0)));
        when(specialLectureRepository.findBySpecialLectureDate(expectSpecialLecture.getSpecialLectureDate())).thenReturn(Optional.empty());

        // when
        Exception exception = assertThrows(SpecialLectureException.class, () -> {
            specialLectureService.applyLecture(expectSpecialLecture.getSpecialLectureDate(), givenUser);
        });

        // then
        assertEquals("해당 날짜에는 특강이 없습니다.", exception.getMessage());
    }

    @DisplayName("실패-정원검증/정원이 30명 초과인경우 정원이 초과되었습니다. 라는 예외를 던집니다")
    @Test
    public void 실패_정원검증_30명초과() {
        // given : test 라는 유저가 강의를 신청할때 30명이 꽉찬 강의를 리턴시킵니다.
        String givenUser = "test";
        SpecialLecture expectSpecialLecture = SpecialLectureFixture.failedMaxCapacity();
        when(specialLectureRepository.findBySpecialLectureDate(expectSpecialLecture.getSpecialLectureDate())).thenReturn(Optional.of(expectSpecialLecture));

        // when : test 라는 유저가 정원 30명이 꽉찬 강의를 신청하면 예외를 던져줍니다. - 정원이 초과되었습니다.
        Exception exception = assertThrows(SpecialLectureException.class, () -> {
                    specialLectureService.applyLecture(expectSpecialLecture.getSpecialLectureDate(), givenUser);
        });

        // then : 정원이 초과되었습니다. 나오는지 확인합니다.
        assertEquals("정원이 초과되었습니다.", exception.getMessage());
    }

// (기본)** 특강 신청 완료 여부 조회 API

// 특정 userId 로 특강 신청 완료 여부를 조회하는 API 를 작성합니다.
// 특강 신청에 성공한 사용자는 성공했음을, 특강 등록자 명단에 없는 사용자는 실패했음을 반환하는지 테스트

// 정확하게 30 명의 사용자에게만 특강을 제공하고 있는지 테스트
// 같은 사용자에게 여러 번의 특강 슬롯이 제공되지 않도록 제한하고 있는지 테스트






    @DisplayName("성공-4월 20일 토요일 1시에 정원이 30명 미만이고 신청유저아이디가 중복이 아니고, 신청날짜에 특강이 존재하고 신청날짜가 유효한경우")
    @Test
    public void 특강가능한강의를신청시_정상적인데이터로_신청성공한결과() {
        // given : 특강이 `4월 20일 토요일 1시` 에 열리며 신청인원이 30명 미만인 경우
        LocalDate givenAprilDate = LocalDate.from(LocalDateTime.of(2024, 4, 20, 13, 0));
        String givenUser = "firstApply";
        SpecialLecture expectSpecialLecture = SpecialLectureFixture.create(givenAprilDate);
        SpecialLectureApply expectSpecialLectureApply= SpecialLectureApplyFixture.create(givenAprilDate,givenUser);
        when(specialLectureRepository.findBySpecialLectureDate(givenAprilDate)).thenReturn(Optional.of(expectSpecialLecture));
        ArgumentCaptor<SpecialLecture> specialLectureArgumentCaptor =
                ArgumentCaptor.forClass(SpecialLecture.class);
        ArgumentCaptor<SpecialLectureApply> specialLectureApplyArgumentCaptor =
                ArgumentCaptor.forClass(SpecialLectureApply.class);
        // when : 특강을 신청합니다.
        specialLectureService.applyLecture(givenAprilDate, givenUser);
        // then 검증
        // 강의레포지토리가 한번 저장 실행되었습니다.
        // 강의날짜,강의신청자 비교
        Mockito.verify(specialLectureRepository, Mockito.times(1)).save(specialLectureArgumentCaptor.capture());
        SpecialLecture capturedSpecialLecture = specialLectureArgumentCaptor.getValue();
        Assertions.assertEquals(expectSpecialLecture.getSpecialLectureDate(), capturedSpecialLecture.getSpecialLectureDate());
        Assertions.assertEquals(expectSpecialLecture.getCurrentApplications(), capturedSpecialLecture.getCurrentApplications());
        // 강의신청 레포지토리가 한번 저장 실행되었습니다.
        // 강의신청자 아이디 비교
        Mockito.verify(specialLectureApplyRepository, Mockito.times(1)).save(specialLectureApplyArgumentCaptor.capture());
        SpecialLectureApply capturedSpecialLectureApply = specialLectureApplyArgumentCaptor.getValue();
        Assertions.assertEquals(expectSpecialLectureApply.getUserId(), capturedSpecialLectureApply.getUserId());
    }

}
