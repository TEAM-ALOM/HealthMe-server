package HealthMe.HealthMe.domain.exercise.controller;


import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.domain.exercise.dto.ExerciseDto;
import HealthMe.HealthMe.domain.exercise.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/exercise")
public class ExerciseController {
    private final ExerciseService exerciseService;

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody ExerciseDto exerciseDto) throws CustomException {
        exerciseService.save(exerciseDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/by-name")
    public ResponseEntity findByName(@RequestBody ExerciseDto exerciseDto) throws CustomException {
        return new ResponseEntity(exerciseService.findByName(exerciseDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity findAll(){
        return new ResponseEntity(exerciseService.findAll(), HttpStatus.OK);
    }
}
