package com.tdd.speciallectureapply.speciallecture.model.entity;

import jakarta.persistence.*;

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
@Table(name = "special_lecture_apply_info", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"special_lecture_date", "user_id"}, name = "unique_user_per_lecture")
})
public class SpecialLectureApply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "special_lecture_id", nullable = false)
    private SpecialLecture specialLecture;

    @Column(name = "special_lecture_date", nullable = false)
    private LocalDate specialLectureDate;

    @Column(name = "user_id", nullable = false)
    private String userId;

}
