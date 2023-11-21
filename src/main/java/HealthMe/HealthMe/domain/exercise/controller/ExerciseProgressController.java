package HealthMe.HealthMe.domain.exercise.controller;

import HealthMe.HealthMe.domain.exercise.domain.ExerciseList;
import HealthMe.HealthMe.domain.exercise.domain.ExerciseProgressList;
import HealthMe.HealthMe.domain.exercise.dto.ExerciseProgressDto;
import HealthMe.HealthMe.domain.exercise.service.ExerciseProgressService;
import HealthMe.HealthMe.domain.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/progress")
public class ExerciseProgressController {
    private final ExerciseProgressService exerciseProgressService;

    @GetMapping("/findP")
    public List<ExerciseProgressList> findByEmail(@RequestBody User user){
        return exerciseProgressService.findProgressedExerciseByEmail(user);
    }

    @PostMapping("/insert")
    public ExerciseProgressList insert(@RequestBody ExerciseProgressDto exerciseProgressDto){
        // DTO가 아니라 entity로 다시 바꿔라
        ExerciseProgressList inserted = exerciseProgressService.insert(exerciseProgressDto);
        log.info("inserted = {}", inserted);
        return inserted;
    }
}
