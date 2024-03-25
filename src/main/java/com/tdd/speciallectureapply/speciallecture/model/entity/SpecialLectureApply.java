package com.tdd.speciallectureapply.speciallecture.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "special_lecture_apply_info")
public class SpecialLectureApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applyId;

    @Column(nullable = false)
    private LocalDate specialLectureDate;

    @Column(nullable = false)
    private String userId;


    @ManyToOne
    @JoinColumn(name = "special_lecture_id", nullable = false)
    private SpecialLecture specialLecture;
}
