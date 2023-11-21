package HealthMe.HealthMe.domain.exercise.controller;


import HealthMe.HealthMe.domain.exercise.domain.ExerciseList;
import HealthMe.HealthMe.domain.exercise.domain.ExerciseProgressList;
import HealthMe.HealthMe.domain.exercise.dto.ExerciseDto;
import HealthMe.HealthMe.domain.exercise.service.ExerciseProgressService;
import HealthMe.HealthMe.domain.exercise.service.ExerciseService;
import HealthMe.HealthMe.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1")
public class ExerciseController {
    private final ExerciseService exerciseService;

    @GetMapping("/save")
    public ExerciseList save(@RequestBody ExerciseDto exerciseDto){
        ExerciseList exerciseList = exerciseService.save(exerciseDto);
        return exerciseList;
    }

    @GetMapping("/findById")
    public ExerciseList findById(@RequestBody ExerciseDto exerciseDto){
        ExerciseList exerciseList = exerciseService.findById(exerciseDto);
        return exerciseList;
    }

    @GetMapping("/findByName")
    public ExerciseList findByName(@RequestBody ExerciseDto exerciseDto){
        ExerciseList exerciseList = exerciseService.findByName(exerciseDto);
        return exerciseList;
    }

    @GetMapping("/findAll")
    public List<ExerciseList> findAll(){
        return exerciseService.findAll();
    }


}
