package com.tdd.speciallectureapply.speciallecture.service;

import com.tdd.speciallectureapply.speciallecture.exception.SpecialLectureException;
import com.tdd.speciallectureapply.speciallecture.model.common.ApplyStatus;
import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLecture;
import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLectureApply;
import com.tdd.speciallectureapply.speciallecture.model.dto.response.SpecialLectureApplyResponse;
import com.tdd.speciallectureapply.speciallecture.model.dto.response.SpecialLectureApplyStatusResponse;
import com.tdd.speciallectureapply.speciallecture.repository.SpecialLectureApplyRepository;
import com.tdd.speciallectureapply.speciallecture.repository.SpecialLectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SpecialLectureService {
    private final SpecialLectureApplyRepository specialLectureApplyRepository;
    private final SpecialLectureRepository specialLectureRepository;

    @Autowired
    public SpecialLectureService(SpecialLectureApplyRepository specialLectureApplyRepository,
                                 SpecialLectureRepository specialLectureRepository) {
        this.specialLectureApplyRepository = specialLectureApplyRepository;
        this.specialLectureRepository = specialLectureRepository;
    }

    // 추후리팩토링
    // - 강의생성서비스-강의를 생성합니다,
    // - 강의신청서비스-강의를 신청합니다, 강의신청상태를 확인합니다, 강의신청자목록을 불러옵니다

    /**
     * 강의를 생성하고 저장합니다.
     *
     * @param lectureDate 강의 날짜
     * @param maxCapacity 강의 최대 정원
     * @return 생성된 강의 정보
     */
    public SpecialLecture createLecture(LocalDate lectureDate, int maxCapacity) {
        // 강의 생성
        SpecialLecture newLecture = SpecialLecture.builder()
                .date(lectureDate)
                .maxCapacity(maxCapacity)
                .currentApplications(0)
                .build();

        // 강의 저장
        return specialLectureRepository.save(newLecture);
    }


    /**
     * TODO - 강의신청을 합니다
     * @param applyDate
     * @param userId
     */
    public void applyLecture(LocalDate applyDate, String userId) {
        // 1. 신청날짜 유효성 검사
        if (!applyDate.isAfter(LocalDate.now())) {
            throw new SpecialLectureException("신청 날짜가 유효하지않습니다.");
        }
        // 2. 특강 존재 여부 검사
        SpecialLecture lecture = specialLectureRepository
                .findBySpecialLectureDate(applyDate)
                .orElseThrow(() -> new SpecialLectureException("해당 날짜에는 특강이 없습니다."));

        // 3. 정원 초과 검사
        if (lecture.getCurrentApplications() >= lecture.getMaxCapacity()) {
            throw new SpecialLectureException("정원이 초과되었습니다.");
        }

        // 4. 중복 신청 검사
        Optional<SpecialLectureApply> isDuplicate = specialLectureApplyRepository
                .findByUserIdAndSpecialLectureDate(userId, applyDate);
        if (isDuplicate.isPresent()) {
            throw new SpecialLectureException("이미 신청된 특강입니다.");
        }
        // 특강 신청 처리
        SpecialLectureApply specialLectureApply = SpecialLectureApply.builder()
                .specialLecture(lecture)
                .specialLectureDate(applyDate)
                .userId(userId)
                .build();
        specialLectureApplyRepository.save(specialLectureApply);
        // 정원 업데이트
        lecture.increaseCurrentApplications();
        specialLectureRepository.save(lecture);
    }

    /**
     * TODO - 강의신청자 상태를 나타냅니다.
     * @param userId
     * @return
     */
    public SpecialLectureApplyStatusResponse getLectureApplicationStatus(String userId) {
        Optional<SpecialLectureApply> applyOptional = specialLectureApplyRepository.findByUserId(userId);

        if (applyOptional.isPresent()) {
            SpecialLectureApply apply = applyOptional.get();
            return new SpecialLectureApplyStatusResponse(
                    apply.getUserId(),
                    ApplyStatus.ACCEPTED,
                    "신청 상태 조회 성공."
            );
        } else {
            return new SpecialLectureApplyStatusResponse(
                    userId,
                    null,
                    "신청한 특강이 없거나 신청이 실패하였습니다."
            );
        }
    }

    /**
     * TODO - 강의신청자 목록을 가져옵니다.
     * @param specialLectureDate
     * @return
     */
    public List<SpecialLectureApplyResponse> getPassLectureApplyList(LocalDate specialLectureDate) {
        List<SpecialLectureApply> lectureApplies = specialLectureApplyRepository.findAll();

        return lectureApplies.stream()
                .filter((pass) -> pass.getSpecialLectureDate().equals(specialLectureDate))
                .map((pass) -> new SpecialLectureApplyResponse(pass.getSpecialLectureDate(), pass.getUserId()))
                .toList();
    }


}