package HealthMe.HealthMe.domain.exercise.controller;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.domain.exercise.dto.ExerciseProgressDto;
import HealthMe.HealthMe.domain.exercise.service.ExerciseProgressService;
import HealthMe.HealthMe.domain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// 11/25 v2 추가 : 리턴 타입 ResponseEntity로 수정
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v2/progress")
public class ExerciseProgressControllerV2 {
    private final ExerciseProgressService exerciseProgressService;

    @GetMapping("/findByEmail")
    public ResponseEntity<List<ExerciseProgressDto>> findByEmail(@RequestBody UserDto userDto) throws CustomException {
        return new ResponseEntity<>(exerciseProgressService.findProgressedExerciseByEmail(userDto), HttpStatus.OK);
    }

    @GetMapping("/findByDate")
    public ResponseEntity<List<ExerciseProgressDto>> findByDate(@RequestBody ExerciseProgressDto exerciseProgressDto) throws CustomException {
        return new ResponseEntity<>(exerciseProgressService.findProgressedExerciseByDate(exerciseProgressDto), HttpStatus.OK);
    }
    @PostMapping("/insert")
    public ResponseEntity insert(@RequestBody ExerciseProgressDto exerciseProgressDto){
        exerciseProgressService.insert(exerciseProgressDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
