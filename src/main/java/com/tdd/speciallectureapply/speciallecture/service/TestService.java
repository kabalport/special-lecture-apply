package com.tdd.speciallectureapply.speciallecture.service;


import com.tdd.speciallectureapply.speciallecture.exception.SpecialLectureException;
import com.tdd.speciallectureapply.speciallecture.model.common.ApplyStatus;
import com.tdd.speciallectureapply.speciallecture.model.dto.response.SpecialLectureResponse;
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
public class TestService {
    private SpecialLectureApplyRepository specialLectureApplyRepository;
    private SpecialLectureRepository specialLectureRepository;
    @Autowired
    public TestService(SpecialLectureApplyRepository specialLectureApplyRepository,
                                 SpecialLectureRepository specialLectureRepository) {
        this.specialLectureApplyRepository = specialLectureApplyRepository;
        this.specialLectureRepository = specialLectureRepository;
    }
    /**
     * 강의를 생성하고 저장합니다.
     *
     * @param lectureDate 강의 날짜
     * @param maxCapacity 강의 최대 정원
     * @return 생성된 강의 정보
     */
    @Transactional
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
}
