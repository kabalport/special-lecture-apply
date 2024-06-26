package com.tdd.speciallectureapply.speciallecture.service;

import com.tdd.speciallectureapply.speciallecture.LockHandler;
import com.tdd.speciallectureapply.speciallecture.model.SpecialLectureApplyFixture;
import com.tdd.speciallectureapply.speciallecture.model.SpecialLectureFixture;
import com.tdd.speciallectureapply.speciallecture.exception.SpecialLectureApplyException;
import com.tdd.speciallectureapply.speciallecture.model.dto.response.SpecialLectureApplyResponse;
import com.tdd.speciallectureapply.speciallecture.model.dto.response.SpecialLectureApplyStatusResponse;
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
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
/**
 * 특강신청
 *  특정 userId 로 선착순으로 제공되는 특강을 신청하는 API 를 작성합니다.
 *  동일한 신청자는 한 번의 수강 신청만 성공할 수 있습니다.
 *  특강은 `4월 20일 토요일 1시` 에 열리며, 선착순 30명만 신청 가능합니다.
 *  이미 신청자가 30명이 초과되면 이후 신청자는 요청을 실패합니다
 *  1.
 */
public class SpecialLectureApplyServiceTest {
    private SpecialLectureApplyService sut;
    private SpecialLectureApplyRepository specialLectureApplyRepository;
    private SpecialLectureRepository specialLectureRepository;
    private String givenUser = "test";

    @BeforeEach
    public void beforeEach() {
        specialLectureApplyRepository = Mockito.mock(SpecialLectureApplyRepository.class);
        specialLectureRepository = Mockito.mock(SpecialLectureRepository.class);
        sut =
                new SpecialLectureApplyService(specialLectureApplyRepository, specialLectureRepository);
    }

    @DisplayName("실패-신청날짜유효성검증/특강신청한 날짜가 과거인 경우 '신청 날짜가 유효하지않습니다.' 라는 예외를 던집니다.")
    @Test
    public void 실패_신청날짜검증_신청날이오늘보다과거() {
        // given: 현재 날짜보다 이전인 날짜로 특강 신청 시도
        LocalDate pastDate = LocalDate.now().minusDays(1); // 오늘 날짜에서 하루를 뺀 과거 날짜

        // when: applyLecture 메서드를 호출할 때 이 과거 날짜를 사용하고, 결과로 발생하는 예외를 캡처
        Exception exception = assertThrows(SpecialLectureApplyException.class, () -> {
            sut.applyLecture(pastDate, givenUser);
        });

        // then: 예외 메시지가 "신청 날짜가 지났습니다."인지 확인
        assertEquals("신청 날짜가 유효하지않습니다.", exception.getMessage());
    }


    @DisplayName("실패-특강날짜검증/특강신청한 날짜에 특강이 없는경우 특강이없습니다. 라는 예외를 던집니다.")
    @Test
    public void 실패_특강날짜검증_신청날짜에특강이존재하지않음() {
        // given
        LocalDate givenAprilDate = LocalDate.of(2024, 4, 20);
        SpecialLecture expectSpecialLecture = SpecialLectureFixture.create(givenAprilDate);
        when(specialLectureRepository.findBySpecialLectureDate(expectSpecialLecture.getDate())).thenReturn(Optional.empty());

        // when
        Exception exception = assertThrows(SpecialLectureApplyException.class, () -> {
            sut.applyLecture(expectSpecialLecture.getDate(), givenUser);
        });

        // then
        assertEquals("해당 날짜에는 특강이 없습니다.", exception.getMessage());
    }

//    @DisplayName("실패-정원검증/정원이 30명 초과인경우 정원이 초과되었습니다. 라는 예외를 던집니다")
//    @Test
//    public void 실패_정원검증_30명초과() {
//        // given : test 라는 유저가 강의를 신청할때 30명이 꽉찬 강의를 리턴시킵니다.
//        SpecialLecture expectSpecialLecture = SpecialLectureFixture.failedMaxCapacity();
//        when(specialLectureRepository.findBySpecialLectureDate(expectSpecialLecture.getDate())).thenReturn(Optional.of(expectSpecialLecture));
//
//        // when : test 라는 유저가 정원 30명이 꽉찬 강의를 신청하면 예외를 던져줍니다. - 정원이 초과되었습니다.
//        Exception exception = assertThrows(SpecialLectureApplyException.class, () -> {
//            sut.applyLecture(expectSpecialLecture.getDate(), givenUser);
//        });
//
//        // then : 정원이 초과되었습니다. 나오는지 확인합니다.
//        assertEquals("정원이 초과되었습니다.", exception.getMessage());
//    }

    @DisplayName("성공-4월 20일 토요일 1시에 정원이 30명 미만이고 신청유저아이디가 중복이 아니고, 신청날짜에 특강이 존재하고 신청날짜가 유효한경우")
    @Test
    public void 성공_특강가능한강의를신청시_정상적인데이터로_신청성공한결과() {
        // given : 특강이 `4월 20일 토요일 1시` 에 열리며 신청인원이 30명 미만인 경우
        LocalDate givenAprilDate = LocalDate.of(2024, 4, 20);

        SpecialLecture expectSpecialLecture = SpecialLectureFixture.april20Lecture();
        SpecialLectureApply expectSpecialLectureApply = SpecialLectureApplyFixture.create(givenAprilDate, givenUser);
        when(specialLectureRepository.findBySpecialLectureDate(givenAprilDate)).thenReturn(Optional.of(expectSpecialLecture));
        ArgumentCaptor<SpecialLecture> specialLectureArgumentCaptor =
                ArgumentCaptor.forClass(SpecialLecture.class);
        ArgumentCaptor<SpecialLectureApply> specialLectureApplyArgumentCaptor =
                ArgumentCaptor.forClass(SpecialLectureApply.class);
        // when : 특강을 신청합니다.
        sut.applyLecture(givenAprilDate, givenUser);
        // then 검증
        // 강의레포지토리가 한번 저장 실행되었습니다.
        // 강의날짜,강의신청자 비교
        Mockito.verify(specialLectureRepository, Mockito.times(1)).save(specialLectureArgumentCaptor.capture());
        SpecialLecture capturedSpecialLecture = specialLectureArgumentCaptor.getValue();
        Assertions.assertEquals(expectSpecialLecture.getDate(), capturedSpecialLecture.getDate());
        Assertions.assertEquals(expectSpecialLecture.getCurrentApplications(), capturedSpecialLecture.getCurrentApplications());
        // 강의신청 레포지토리가 한번 저장 실행되었습니다.
        // 강의신청자 아이디 비교
        Mockito.verify(specialLectureApplyRepository, Mockito.times(1)).save(specialLectureApplyArgumentCaptor.capture());
        SpecialLectureApply capturedSpecialLectureApply = specialLectureApplyArgumentCaptor.getValue();
        Assertions.assertEquals(expectSpecialLectureApply.getUserId(), capturedSpecialLectureApply.getUserId());
    }

    // 특강 신청 완료 여부 조회 API
// 특정 userId 로 특강 신청 완료 여부를 조회하는 API 를 작성합니다.
// 특강 신청에 성공한 사용자는 성공했음을, 특강 등록자 명단에 없는 사용자는 실패했음을 반환하는지 테스트
    @DisplayName("특강 신청에 실패한 사용자의 특강 신청 완료 여부 확인")
    @Test
    public void 특강신청에실패한사용자의_특강신청완료여부확인() {
        // given: 특강 신청에 실패한(즉, 특강 신청 기록이 없는) 사용자 ID
        String failedUserId = "userFailed";
        LocalDate givenAprilDate = LocalDate.of(2024, 4, 30);
        // 신청 기록이 없음을 나타내기 위해 빈 Optional 반환 설정
        when(specialLectureApplyRepository.findByUserId(failedUserId)).thenReturn(Optional.empty());

        // when: 사용자의 특강 신청 완료 여부를 조회
        SpecialLectureApplyStatusResponse response = sut.getLectureApplicationStatus(failedUserId, givenAprilDate);


    }

    @DisplayName("특강 신청에 성공한 사용자의 특강 신청 완료 여부 확인")
    @Test
    public void 특강신청에성공한사용자의_특강신청완료여부확인() {
        // given: 특강 신청에 성공한 사용자 ID와 해당 정보
        String successUserId = "userSuccess";
        LocalDate givenAprilDate = LocalDate.of(2024, 4, 20);
        LocalDate lectureDate = LocalDate.now().plusDays(10); // 미래 날짜를 예시로 사용
        SpecialLectureApply successfulApplication = SpecialLectureApply.builder()
                .userId(successUserId)
                .specialLectureDate(lectureDate)
                .build();

        // 신청 성공 기록이 있음을 나타내기 위해 설정
        when(specialLectureApplyRepository.findByUserId(successUserId)).thenReturn(Optional.of(successfulApplication));

        // when: 사용자의 특강 신청 완료 여부를 조회
        SpecialLectureApplyStatusResponse response = sut.getLectureApplicationStatus(successUserId, givenAprilDate);

    }

}
