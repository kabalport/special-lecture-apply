package com.tdd.speciallectureapply.speciallecture.repository;

import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface SpecialLectureRepository extends JpaRepository<SpecialLecture, Long> {
    @Query("SELECT s FROM SpecialLecture s WHERE s.date = :date")
    Optional<SpecialLecture> findBySpecialLectureDate(@Param("date") LocalDate date);
//    Optional<SpecialLecture> findBySpecialLectureDate(LocalDate specialLectureDate);


}
