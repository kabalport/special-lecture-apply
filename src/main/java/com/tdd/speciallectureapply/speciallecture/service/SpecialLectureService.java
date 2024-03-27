package com.tdd.speciallectureapply.speciallecture.service;

import com.tdd.speciallectureapply.speciallecture.model.dto.response.SpecialLectureResponse;
import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLecture;
import com.tdd.speciallectureapply.speciallecture.repository.SpecialLectureApplyRepository;
import com.tdd.speciallectureapply.speciallecture.repository.SpecialLectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SpecialLectureService {

    private final SpecialLectureRepository specialLectureRepository;
    @Autowired
    public SpecialLectureService(SpecialLectureRepository specialLectureRepository) {
        this.specialLectureRepository = specialLectureRepository;}
    /**
     * TODO - 특강 목록 조회
     */
    public List<SpecialLectureResponse> getSpecialLectureList() {
        List<SpecialLecture> lectureApplies = specialLectureRepository.findAll();

        return lectureApplies.stream()
                .map((list) -> new SpecialLectureResponse(list.getDate(), list.getCurrentApplications()))
                .toList();
    }
}
