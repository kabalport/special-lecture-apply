package com.tdd.speciallectureapply.speciallecture.service;

import com.tdd.speciallectureapply.speciallecture.model.dto.response.SpecialLectureResponse;
import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLecture;
import com.tdd.speciallectureapply.speciallecture.repository.SpecialLectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
public class SpecialLectureServiceIntegrationTest {

    @Autowired
    private SpecialLectureService specialLectureService;

    @Autowired
    private SpecialLectureRepository specialLectureRepository;

    @BeforeEach
    public void setUp() {
        specialLectureRepository.deleteAll();
    }

    @DisplayName("특강 목록 조회")
    @Test
    public void 특강목록_조회_성공() {
        // given
        LocalDate date1 = LocalDate.of(2024, 4, 20);
        LocalDate date2 = LocalDate.of(2024, 5, 15);
        specialLectureRepository.save(new SpecialLecture(null, date1, 30, 0));
        specialLectureRepository.save(new SpecialLecture(null, date2, 30, 0));

        // when
        List<SpecialLectureResponse> responses = specialLectureService.getSpecialLectureList();

        // then
        assertEquals(2, responses.size(), "특강 목록의 크기가 예상과 다릅니다.");
    }
}
