package com.tdd.speciallectureapply.speciallecture.service;

import com.tdd.speciallectureapply.speciallecture.LockHandler;
import com.tdd.speciallectureapply.speciallecture.exception.SpecialLectureApplyException;
import com.tdd.speciallectureapply.speciallecture.model.dto.ApplyStatus;
import com.tdd.speciallectureapply.speciallecture.model.dto.request.SpecialLectureApplyRequest;
import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLecture;
import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLectureApply;
import com.tdd.speciallectureapply.speciallecture.model.dto.response.SpecialLectureApplyResponse;
import com.tdd.speciallectureapply.speciallecture.model.dto.response.SpecialLectureApplyStatusResponse;
import com.tdd.speciallectureapply.speciallecture.repository.SpecialLectureApplyRepository;
import com.tdd.speciallectureapply.speciallecture.repository.SpecialLectureRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SpecialLectureApplyService {
    private final SpecialLectureApplyRepository specialLectureApplyRepository;
    private final SpecialLectureRepository specialLectureRepository;
    //    private final LockHandler lockHandler;
    @Autowired
    public SpecialLectureApplyService(SpecialLectureApplyRepository specialLectureApplyRepository,
                                      SpecialLectureRepository specialLectureRepository
//                                      LockHandler lockHandler
    ) {
        this.specialLectureApplyRepository = specialLectureApplyRepository;
        this.specialLectureRepository = specialLectureRepository;
//        this.lockHandler = lockHandler;
    }

//    public void applyLectureWithLock(LocalDate applyDate,Long userId) {
//        lockHandler.executeOnLock(userId, () -> {
//            applyLecture(applyDate, String.valueOf(userId));
//            return null;
//        });
//    }



    @Transactional
    public synchronized void applyLecture(LocalDate applyDate,String userId) {

//        LocalDate applyDate =request.getApplyDate();
//        String userId = request.getUserId();

        // 입력 값 검증
        if (applyDate == null) {
            throw new SpecialLectureApplyException("applyDate는 null이 될 수 없습니다.");
        }
        if (userId == null || userId.trim().isEmpty()) {
            throw new SpecialLectureApplyException("userId는 비어 있을 수 없습니다.");
        }

        // 1. 신청날짜 유효성 검사
        if (!applyDate.isAfter(LocalDate.now())) {
            throw new SpecialLectureApplyException("신청 날짜가 유효하지않습니다.");
        }
        // 2. 특강 존재 여부 검사
        SpecialLecture lecture = specialLectureRepository
                .findBySpecialLectureDate(applyDate)
                .orElseThrow(() -> new SpecialLectureApplyException("해당 날짜에는 특강이 없습니다."));

        // 3. 정원 초과 검사 - 도메인에서 정원검사 할거임
//        if (lecture.getCurrentApplications() >= lecture.getMaxCapacity()) {
//            throw new SpecialLectureApplyException("정원이 초과되었습니다.");
//        }

        // 4. 중복 신청 검사
        Optional<SpecialLectureApply> isDuplicate = specialLectureApplyRepository
                .findByUserIdAndSpecialLectureDate(userId, applyDate);
        if (isDuplicate.isPresent()) {
            throw new SpecialLectureApplyException("이미 신청된 특강입니다.");
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
    public SpecialLectureApplyStatusResponse getLectureApplicationStatus(String userId, LocalDate date) {
        // 입력 값 검증
        if (date == null) {
            throw new SpecialLectureApplyException("date는 null이 될 수 없습니다.");
        }
        if (userId == null || userId.trim().isEmpty()) {
            throw new SpecialLectureApplyException("userId는 비어 있을 수 없습니다.");
        }

        Optional<SpecialLectureApply> applyOptional = specialLectureApplyRepository.findByUserIdAndSpecialLectureDate(userId,date);

        if (applyOptional.isPresent()) {
            SpecialLectureApply apply = applyOptional.get();
            return new SpecialLectureApplyStatusResponse(
                    apply.getUserId(),
                    ApplyStatus.ACCEPTED,
                    "특강 신청 완료 성공."
            );
        } else {
            return new SpecialLectureApplyStatusResponse(
                    userId,
                    ApplyStatus.REJECTED,
                    "특강 신청 완료 실패"
            );
        }
    }
}