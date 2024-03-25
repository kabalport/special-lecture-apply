package com.tdd.speciallectureapply.speciallecture.model.entity;

import com.tdd.speciallectureapply.speciallecture.model.ApplyStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplyStatus specialLectureApplyStatus;

    // 특강 ID를 참조하는 외래키로 ManyToOne 관계 설정
    // SpecialLecture 클래스는 해당 특강의 상세 정보를 담는 엔티티로 가정합니다.
    @ManyToOne
    @JoinColumn(name = "special_lecture_id", nullable = false)
    private SpecialLecture specialLecture;

}
