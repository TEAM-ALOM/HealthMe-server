package HealthMe.HealthMe.domain.exercise.controller;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.domain.exercise.dto.ExerciseProgressDto;
import HealthMe.HealthMe.domain.exercise.service.ExerciseProgressService;
import HealthMe.HealthMe.domain.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/exercise")
@Tag(name="진행한 운동 관련 컨트롤러")
public class ExerciseProgressController {
    private final ExerciseProgressService exerciseProgressService;

    @PostMapping("progress/by-email")
    @Operation(summary = "진행한 운동 email로 찾기", description = "해당 url로 요청시 진행한 운동을 user의 email로 로드")
    public ResponseEntity findByEmail(@RequestBody UserDto userDto) throws CustomException {
        return new ResponseEntity(exerciseProgressService.findProgressedExerciseByEmail(userDto), HttpStatus.OK);
    }

    @PostMapping("progress/by-date")
    @Operation(summary = "진행한 운동 날짜로 찾기", description = "해당 url로 요청시 진행한 운동을 특정 날짜로 로드")
    public ResponseEntity findByDate(@RequestBody ExerciseProgressDto exerciseProgressDto) throws CustomException {
        return new ResponseEntity(exerciseProgressService.findProgressedExerciseByDate(exerciseProgressDto), HttpStatus.OK);
    }
    @PostMapping("progress/save")
    @Operation(summary = "진행한 운동 저장", description = "해당 url로 요청시 진행한 운동 저장 기능 수행")
    public ResponseEntity save(@RequestBody ExerciseProgressDto exerciseProgressDto) throws CustomException {
        exerciseProgressService.insert(exerciseProgressDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
