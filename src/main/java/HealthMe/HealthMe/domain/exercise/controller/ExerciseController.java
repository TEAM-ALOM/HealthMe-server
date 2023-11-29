package HealthMe.HealthMe.domain.exercise.controller;


import HealthMe.HealthMe.domain.exercise.domain.ExerciseList;
import HealthMe.HealthMe.domain.exercise.domain.ExerciseProgressList;
import HealthMe.HealthMe.domain.exercise.dto.ExerciseDto;
import HealthMe.HealthMe.domain.exercise.service.ExerciseProgressService;
import HealthMe.HealthMe.domain.exercise.service.ExerciseService;
import HealthMe.HealthMe.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    /**
     * responseEntity 클래스 써서 status랑 message 관리
     * controller 단에서는 무조건 responseEntity로 return
     * 실행 로직에 따라 status 다르게 설정해야됨.
     */
    private final ExerciseService exerciseService;

    @GetMapping("/save")
    public ExerciseList save(@RequestBody ExerciseDto exerciseDto){
        ExerciseList exerciseList = exerciseService.save(exerciseDto);
        ResponseEntity.ok(exerciseList);
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
    //responseEntity status별로 message 전달 & 로직 조정 가능
}
