package com.tdd.speciallectureapply;

import com.tdd.speciallectureapply.model.entity.SpecialLecture;
import com.tdd.speciallectureapply.repository.SpecialLectureRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(SpecialLectureRepository specialLectureRepository) {
        return args -> {
            specialLectureRepository.save(new SpecialLecture(1L, LocalDate.of(2024, 4, 20), 30, 0));
        };
    }
}
