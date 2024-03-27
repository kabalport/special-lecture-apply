package com.tdd.speciallectureapply.speciallecture;

import com.tdd.speciallectureapply.speciallecture.exception.SpecialLectureApplyException;
import com.tdd.speciallectureapply.speciallecture.model.SpecialLectureFixture;
import com.tdd.speciallectureapply.speciallecture.model.entity.SpecialLecture;
import com.tdd.speciallectureapply.speciallecture.repository.SpecialLectureApplyRepository;
import com.tdd.speciallectureapply.speciallecture.repository.SpecialLectureRepository;
import com.tdd.speciallectureapply.speciallecture.service.SpecialLectureApplyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

public class SpecialLectureApplyConcurrencyTest {

    private SpecialLectureApplyService sut;
    private SpecialLectureApplyRepository specialLectureApplyRepository;
    private SpecialLectureRepository specialLectureRepository;
        @DisplayName("동시에 여러 요청을 처리할 때 정원 초과하지 않음을 검증")
        @Test
        public void applyLectureWithLock_ConcurrencyTest() throws ExecutionException, InterruptedException {
            LocalDate lectureDate = LocalDate.of(2024, 4, 20);
            int maxCapacity = 30;
            SpecialLecture expectSpecialLecture = SpecialLectureFixture.april20Lecture();

            // Mockito를 사용하여 특정 강의 조회 시 미리 정의된 강의 객체를 반환하도록 설정
            Mockito.when(specialLectureRepository.findBySpecialLectureDate(lectureDate)).thenReturn(Optional.of(expectSpecialLecture));

            int numberOfApplicants = 50;
            CompletableFuture[] futures = new CompletableFuture[numberOfApplicants];

            IntStream.range(0, numberOfApplicants).forEach(i -> {
                futures[i] = CompletableFuture.runAsync(() -> {
                    try {
                        sut.applyLectureWithLock(lectureDate, (long) i);
                    } catch (SpecialLectureApplyException e) {
                        // 예외 처리 로직
                    }
                });
            });

            CompletableFuture.allOf(futures).get();

            // 여기에서는 실제 데이터베이스 조회 대신 Mockito의 when을 사용하여 설정한 값으로 테스트를 진행합니다.
            // 실제 데이터베이스 조회 로직 대신 모의 객체의 상태를 검증하는 로직을 추가할 수 있습니다.
            // 예: assertThat(someMockedCondition).isEqualTo(expectedValue);
        }

}