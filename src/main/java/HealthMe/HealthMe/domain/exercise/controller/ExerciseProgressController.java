package HealthMe.HealthMe.domain.exercise.controller;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.domain.exercise.domain.ExerciseProgressList;
import HealthMe.HealthMe.domain.exercise.dto.ExerciseProgressDto;
import HealthMe.HealthMe.domain.exercise.service.ExerciseProgressService;
import HealthMe.HealthMe.domain.user.domain.User;

import HealthMe.HealthMe.domain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// 11/25 미사용 : 리턴 타입 responseEntity로 변경
//@RestController
@RequiredArgsConstructor
@Slf4j
//@RequestMapping("api/v1/progress")
public class ExerciseProgressController {
    private final ExerciseProgressService exerciseProgressService;

    @GetMapping("/findByEmail")
    public List<ExerciseProgressDto> findByEmail(@RequestBody UserDto userDto) throws CustomException {
        return exerciseProgressService.findProgressedExerciseByEmail(userDto);
    }

    @GetMapping("/findByDate")
    public List<ExerciseProgressDto> findByDate(@RequestBody ExerciseProgressDto exerciseProgressDto) throws CustomException {
        return exerciseProgressService.findProgressedExerciseByDate(exerciseProgressDto);
    }
    @PostMapping("/insert")
    public ExerciseProgressList insert(@RequestBody ExerciseProgressDto exerciseProgressDto){
        // DTO가 아니라 entity로 다시 바꿔라
        exerciseProgressService.insert(exerciseProgressDto);
        return null;
    }
}
