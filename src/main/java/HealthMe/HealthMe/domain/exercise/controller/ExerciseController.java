package HealthMe.HealthMe.domain.exercise.controller;


import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.domain.exercise.domain.ExerciseList;
import HealthMe.HealthMe.domain.exercise.domain.ExerciseProgressList;
import HealthMe.HealthMe.domain.exercise.dto.ExerciseDto;
import HealthMe.HealthMe.domain.exercise.service.ExerciseProgressService;
import HealthMe.HealthMe.domain.exercise.service.ExerciseService;
import HealthMe.HealthMe.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity save(@RequestBody ExerciseDto exerciseDto) throws CustomException {
        return new ResponseEntity(exerciseService.save(exerciseDto), HttpStatus.CREATED);
    }

    @GetMapping("/findByName")
    public ResponseEntity findByName(@RequestBody ExerciseDto exerciseDto) throws CustomException {
        return new ResponseEntity(exerciseService.findByName(exerciseDto), HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity findAll(){
        return new ResponseEntity(exerciseService.findAll(), HttpStatus.OK);
    }
}
