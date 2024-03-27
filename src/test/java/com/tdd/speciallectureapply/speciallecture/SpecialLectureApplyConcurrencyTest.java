package com.tdd.speciallectureapply.speciallecture;

import com.tdd.speciallectureapply.speciallecture.exception.SpecialLectureApplyException;
import com.tdd.speciallectureapply.speciallecture.model.SpecialLectureApplyFixture;
import com.tdd.speciallectureapply.speciallecture.model.SpecialLectureFixture;
import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLecture;
import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLectureApply;
import com.tdd.speciallectureapply.speciallecture.repository.SpecialLectureApplyRepository;
import com.tdd.speciallectureapply.speciallecture.repository.SpecialLectureRepository;
import com.tdd.speciallectureapply.speciallecture.service.SpecialLectureApplyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;

public class SpecialLectureApplyConcurrencyTest {

    private SpecialLectureApplyService sut;
    private SpecialLectureApplyRepository specialLectureApplyRepository;
    private SpecialLectureRepository specialLectureRepository;
    private LockHandler lockHandler;
    @BeforeEach
    public void beforeEach() {
        specialLectureApplyRepository = Mockito.mock(SpecialLectureApplyRepository.class);
        specialLectureRepository = Mockito.mock(SpecialLectureRepository.class);
        lockHandler = Mockito.mock(LockHandler.class);
        sut =
                new SpecialLectureApplyService(specialLectureApplyRepository, specialLectureRepository, lockHandler);
    }

    @DisplayName("동시에 여러 요청을 처리할 때 정원 초과하지 않음을 검증")
    @Test
    public void applyLecture_ConcurrencyTest() throws InterruptedException, ExecutionException {
        // Given: 특정 날짜에 열리는 특강이 설정되어 있고, 정원은 30명으로 설정
        LocalDate lectureDate = LocalDate.of(2024, 4, 20);
        int maxCapacity = 30;
        SpecialLecture lecture = SpecialLecture.builder()
                .date(lectureDate)
                .maxCapacity(maxCapacity)
                .currentApplications(0)
                .build();

        when(specialLectureRepository.findBySpecialLectureDate(lectureDate))
                .thenReturn(Optional.of(lecture));

        // When: 동시에 50명의 사용자가 특강 신청을 시도
        int numberOfApplicants = 50;
        CompletableFuture<Void>[] futures = new CompletableFuture[numberOfApplicants];

        for (int i = 0; i < numberOfApplicants; i++) {
            String userId = "user" + i;
            futures[i] = CompletableFuture.runAsync(() -> {
                try {
                    sut.applyLecture(lectureDate, userId);
                } catch (SpecialLectureApplyException e) {
                    // 에러 로깅 또는 추가적인 처리를 할 수 있음
                }
            });
        }

        CompletableFuture.allOf(futures).join();

        // Then: 실제로 신청된 특강의 수가 정원을 초과하지 않아야 함
        ArgumentCaptor<SpecialLecture> lectureCaptor = ArgumentCaptor.forClass(SpecialLecture.class);
        Mockito.verify(specialLectureRepository, Mockito.atLeastOnce()).save(lectureCaptor.capture());

        SpecialLecture updatedLecture = lectureCaptor.getValue();
        Assertions.assertTrue(updatedLecture.getCurrentApplications() <= maxCapacity, "정원을 초과하지 않아야 합니다.");

        // 추가적으로, 실제로 신청 처리된 SpecialLectureApply 객체들의 수를 검증할 수도 있습니다.
        ArgumentCaptor<SpecialLectureApply> applyCaptor = ArgumentCaptor.forClass(SpecialLectureApply.class);
        Mockito.verify(specialLectureApplyRepository, Mockito.atLeastOnce()).save(applyCaptor.capture());

        List<SpecialLectureApply> appliedLectures = applyCaptor.getAllValues();
        Assertions.assertTrue(appliedLectures.size() <= maxCapacity, "정원을 초과하여 신청 처리되어서는 안됩니다.");
    }

    @DisplayName("동시에 여러 요청을 처리할 때 정원 초과하지 않음을 검증")
    @Test
    public void applyLectureWithLock_ConcurrencyTest() throws InterruptedException, ExecutionException {
        // Given: 특정 날짜에 열리는 특강이 설정되어 있고, 정원은 30명으로 설정
        LocalDate lectureDate = LocalDate.of(2024, 4, 20);
        int maxCapacity = 30;
        SpecialLecture lecture = SpecialLecture.builder()
                .date(lectureDate)
                .maxCapacity(maxCapacity)
                .currentApplications(0)
                .build();

        when(specialLectureRepository.findBySpecialLectureDate(lectureDate))
                .thenReturn(Optional.of(lecture));

        // When: 동시에 50명의 사용자가 특강 신청을 시도
        int numberOfApplicants = 50;
        CompletableFuture<Void>[] futures = new CompletableFuture[numberOfApplicants];

        for (int i = 0; i < numberOfApplicants; i++) {
            long userId =  i;
            futures[i] = CompletableFuture.runAsync(() -> {
                try {
                    sut.applyLectureWithLock(lectureDate, Long.valueOf(userId));
                } catch (SpecialLectureApplyException e) {
                    // 에러 로깅 또는 추가적인 처리를 할 수 있음
                }
            });
        }

        CompletableFuture.allOf(futures).join();

        // Then: 실제로 신청된 특강의 수가 정원을 초과하지 않아야 함
        ArgumentCaptor<SpecialLecture> lectureCaptor = ArgumentCaptor.forClass(SpecialLecture.class);
        Mockito.verify(specialLectureRepository, Mockito.atLeastOnce()).save(lectureCaptor.capture());

        SpecialLecture updatedLecture = lectureCaptor.getValue();
        Assertions.assertTrue(updatedLecture.getCurrentApplications() <= maxCapacity, "정원을 초과하지 않아야 합니다.");

        // 추가적으로, 실제로 신청 처리된 SpecialLectureApply 객체들의 수를 검증할 수도 있습니다.
        ArgumentCaptor<SpecialLectureApply> applyCaptor = ArgumentCaptor.forClass(SpecialLectureApply.class);
        Mockito.verify(specialLectureApplyRepository, Mockito.atLeastOnce()).save(applyCaptor.capture());

        List<SpecialLectureApply> appliedLectures = applyCaptor.getAllValues();
        Assertions.assertTrue(appliedLectures.size() <= maxCapacity, "정원을 초과하여 신청 처리되어서는 안됩니다.");
    }

}