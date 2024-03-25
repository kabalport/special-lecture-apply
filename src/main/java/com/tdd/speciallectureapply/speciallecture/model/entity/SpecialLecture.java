package com.tdd.speciallectureapply.speciallecture.model.entity;

import com.tdd.speciallectureapply.speciallecture.exception.SpecialLectureException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "special_lecture_info")
public class SpecialLecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long specialLectureId;

    @Column(nullable = false)
    private LocalDate specialLectureDate;

    @Column(nullable = false)
    private Integer maxCapacity;

    @Column(nullable = false)
    private Integer currentApplications;


    public void increaseCurrentApplications() {
        if (this.currentApplications < this.maxCapacity) {
            this.currentApplications++;
        } else {
            throw new SpecialLectureException("정원이 이미 초과되었습니다.");
        }
    }
}
