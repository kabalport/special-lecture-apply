package com.tdd.speciallectureapply.speciallecture.repository;

import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLecture;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;

public interface SpecialLectureRepository extends JpaRepository<SpecialLecture, Long> {
    Optional<SpecialLecture> findBySpecialLectureDate(LocalDate specialLectureDate);
}
