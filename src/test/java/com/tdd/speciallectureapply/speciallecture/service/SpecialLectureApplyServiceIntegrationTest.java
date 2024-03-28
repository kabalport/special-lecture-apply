package com.tdd.speciallectureapply.speciallecture.service;

import com.tdd.speciallectureapply.speciallecture.exception.SpecialLectureApplyException;
import com.tdd.speciallectureapply.speciallecture.model.SpecialLectureFixture;
import com.tdd.speciallectureapply.speciallecture.model.dto.ApplyStatus;
import com.tdd.speciallectureapply.speciallecture.model.dto.response.SpecialLectureApplyStatusResponse;
import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLecture;
import com.tdd.speciallectureapply.speciallecture.repository.SpecialLectureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SpecialLectureApplyServiceIntegrationTest {

    @Autowired
    private SpecialLectureApplyService specialLectureApplyService;

    @Autowired
    private SpecialLectureRepository specialLectureRepository;

    @Test
    public void 특강생성(){
        String userId = "user222";
        LocalDate applyDate = LocalDate.of(2024, 6, 22);

        SpecialLecture newLecture = SpecialLectureFixture.create(applyDate);
        specialLectureRepository.save(newLecture);
    }

    @Test
    public void 특강신청_성공_통합테스트() {
        // given
        String userId = "user222";
        LocalDate applyDate = LocalDate.of(2024, 6, 22);

        SpecialLecture newLecture = SpecialLectureFixture.create(applyDate);

        specialLectureRepository.save(newLecture);

        // when
        specialLectureApplyService.applyLecture(newLecture.getDate(), userId);

        // then
    }

    @Test
    public void 강의_신청_상태_성공적으로_조회() {
        // given: 특정 강의에 대해 신청을 완료한 상태
        LocalDate applyDate = LocalDate.of(2024, 4, 20);
        String userId = "user1";
        SpecialLecture newLecture = SpecialLecture.builder()
                .date(applyDate)
                .maxCapacity(30)
                .currentApplications(0)
                .build();
        specialLectureRepository.save(newLecture);
        specialLectureApplyService.applyLecture(applyDate, userId);

        // when: 신청한 강의의 상태를 조회
        SpecialLectureApplyStatusResponse response = specialLectureApplyService.getLectureApplicationStatus(userId, applyDate);

        // then: 신청 상태가 정상적으로 조회되어야 함
        assertEquals(userId, response.getUserId());
        assertEquals(ApplyStatus.ACCEPTED, response.getSpecialLectureApplyStatus());
        assertEquals("특강 신청 완료 성공.", response.getMessage());
    }

    @Test
    public void 강의_신청_상태_조회_실패_신청_기록_없음() {
        // given: 신청 기록이 없는 사용자 ID
        String userId = "nonexistentUser";
        LocalDate applyDate = LocalDate.of(2024, 4, 20);

        // when: 신청한 강의의 상태를 조회
        SpecialLectureApplyStatusResponse response = specialLectureApplyService.getLectureApplicationStatus(userId, applyDate);

        // then: 신청 기록이 없으므로 조회 실패 메시지가 반환되어야 함
        assertEquals(userId, response.getUserId());
        assertEquals(ApplyStatus.REJECTED, response.getSpecialLectureApplyStatus());
        assertEquals("특강 신청 완료 실패", response.getMessage());
    }


}
