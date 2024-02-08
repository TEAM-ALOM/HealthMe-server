package HealthMe.HealthMe.exercise.controller;


import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.exercise.dto.ExerciseDto;
import HealthMe.HealthMe.exercise.service.ExerciseService;
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
@Tag(name="운동 관련 컨트롤러")
public class ExerciseController {
    private final ExerciseService exerciseService;

    @PostMapping("/save")
    @Operation(summary = "운동 저장", description = "해당 url로 요청시 사용자 지정 운동 저장")
    public ResponseEntity save(@RequestBody ExerciseDto exerciseDto) throws CustomException {
        exerciseService.save(exerciseDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/by-name")
    @Operation(summary = "특정 운동 찾기", description = "해당 url로 요청시 운동 명으로 운동 로드")
    public ResponseEntity findByName(@RequestBody ExerciseDto exerciseDto) throws CustomException {
        return new ResponseEntity(exerciseService.findByName(exerciseDto), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "전체 운동 찾기", description = "해당 url로 요청시 모든 운동 로드")
    public ResponseEntity findAll(){
        return new ResponseEntity(exerciseService.findAll(), HttpStatus.OK);
    }
}
