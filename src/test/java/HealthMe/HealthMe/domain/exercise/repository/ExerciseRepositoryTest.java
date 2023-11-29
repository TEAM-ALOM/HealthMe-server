package HealthMe.HealthMe.domain.exercise.repository;

import HealthMe.HealthMe.domain.exercise.domain.ExerciseList;

import HealthMe.HealthMe.domain.exercise.dto.ExerciseDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@Slf4j
@SpringBootTest
class ExerciseControllerTest {

    private ExerciseDto exerciseDto = new ExerciseDto();
    @Autowired
    private ExerciseRepository exerciseRepository;

    @Test
    void findById(){
        Optional<ExerciseList> byId = exerciseRepository.findById(1L);
        log.info("exercise name = {}", byId.get().getName());
    }

    @Test
    void findByName(){
//        ExerciseList exercise = exerciseRepository.findByName("푸쉬업");
//
//        log.info("find by name = {}, category = {}", exercise.getName(), exercise.getCategory());

    }

    @Test
    void insert(){
        String name = "어나더테스트";
        Double calorie = null;
        String category = "무제";
        exerciseDto.setName(name);
        exerciseDto.setCalorie(calorie);
        exerciseDto.setCategory(category);
        log.info("id = {} name = {} calorie = {} category = {}", exerciseDto.getId(), exerciseDto.getName(), exerciseDto.getCalorie(), exerciseDto.getCategory());
        exerciseRepository.save(exerciseDto.toEntity());

    }
}
