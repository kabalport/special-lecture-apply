package com.tdd.speciallectureapply.speciallecture.repository;


import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLectureApply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SpecialLectureApplyRepository extends JpaRepository<SpecialLectureApply, Long> {
    Optional<SpecialLectureApply> findByUserIdAndSpecialLectureDate(String userId, LocalDate specialLectureDate);
    Optional<SpecialLectureApply> findByUserId(String userId);
}
