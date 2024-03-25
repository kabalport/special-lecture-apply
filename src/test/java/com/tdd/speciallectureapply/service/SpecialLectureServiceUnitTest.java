package com.tdd.speciallectureapply.service;

import com.tdd.speciallectureapply.exception.SpecialLectureException;
import com.tdd.speciallectureapply.model.SpecialLectureApplyFixture;
import com.tdd.speciallectureapply.model.SpecialLectureFixture;
import com.tdd.speciallectureapply.model.dto.request.SpecialLectureApplyRequest;
import com.tdd.speciallectureapply.model.dto.response.SpecialLectureApplyResponse;
import com.tdd.speciallectureapply.model.entity.SpecialLecture;
import com.tdd.speciallectureapply.model.entity.SpecialLectureApply;
import com.tdd.speciallectureapply.repository.SpecialLectureApplyRepository;
import com.tdd.speciallectureapply.repository.SpecialLectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.tdd.speciallectureapply.model.SpecialLectureFixture.april20Lecture;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class SpecialLectureServiceUnitTest {

    private SpecialLectureService specialLectureService;
    private SpecialLectureApplyRepository specialLectureApplyRepository;
    private SpecialLectureRepository specialLectureRepository;

    @BeforeEach
    public void beforeEach() {
        specialLectureApplyRepository = Mockito.mock(SpecialLectureApplyRepository.class);
        specialLectureRepository = Mockito.mock(SpecialLectureRepository.class);
        specialLectureService =
                new SpecialLectureService(specialLectureApplyRepository, specialLectureRepository);
    }

    @Test
    public void whenApplyForLecture_givenAvailableLecture_thenSucceeds() {
        // Given
        LocalDateTime lectureDateTime = LocalDateTime.of(2023, 4, 20, 13, 0);
        SpecialLecture specialLecture = april20Lecture();
        SpecialLectureApplyRequest request = new SpecialLectureApplyRequest(LocalDate.now(),"123");
        // Set request fields appropriately
        when(specialLectureRepository.findBySpecialLectureDate(lectureDateTime.toLocalDate()))
                .thenReturn(Optional.of(specialLecture));
        when(specialLectureApplyRepository.save(any(SpecialLectureApply.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // When
        SpecialLectureApplyResponse response = specialLectureService.applyForLecture(request);

        // Then
        assertNotNull(response);
        assertEquals(request.getUserId(), response.getUserId());
        // Additional assertions can be made here
    }

//    @Test
//    public void whenApplyForLecture_givenFullLecture_thenThrowsException() {
//        // Given
//        LocalDateTime lectureDateTime = LocalDateTime.of(2023, 4, 20, 13, 0);
//        SpecialLecture specialLecture = SpecialLectureFixture.createSpecialLecture(1L, lectureDateTime.toLocalDate(), 30, 30);
//        SpecialLectureApplyRequest request = new SpecialLectureApplyRequest();
//        // Set request fields appropriately
//        when(specialLectureRepository.findBySpecialLectureDate(lectureDateTime.toLocalDate()))
//                .thenReturn(Optional.of(specialLecture));
//
//        // When & Then
//        assertThrows(SpecialLectureException.class, () -> {
//            specialLectureService.applyForLecture(request);
//        });
//    }

    // Additional test cases here
}
