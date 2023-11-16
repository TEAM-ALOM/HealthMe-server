package HealthMe.HealthMe.domain.exercise.service;


import HealthMe.HealthMe.domain.exercise.domain.ExerciseProgressList;
import HealthMe.HealthMe.domain.exercise.dto.ExerciseProgressDto;
import HealthMe.HealthMe.domain.exercise.repository.ExerciseProgressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ExerciseProgressService {
    private final ExerciseProgressRepository exerciseProgressRepository;

    public ExerciseProgressList insert(ExerciseProgressDto exerciseProgressDto){
        if(exerciseProgressDto.getDate() ==null || exerciseProgressDto.getExercise() == null || exerciseProgressDto.getEmail() == null){
            return new ExerciseProgressList();
        }
        ExerciseProgressList exerciseProgressList = exerciseProgressRepository.save(exerciseProgressDto.toEntity());
        return exerciseProgressList;
    }
}
