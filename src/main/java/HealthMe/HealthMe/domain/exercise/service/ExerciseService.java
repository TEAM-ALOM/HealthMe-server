package HealthMe.HealthMe.domain.exercise.service;

import HealthMe.HealthMe.domain.exercise.domain.ExerciseList;
import HealthMe.HealthMe.domain.exercise.dto.ExerciseDto;
import HealthMe.HealthMe.domain.exercise.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    public ExerciseList findById(ExerciseDto exerciseDto){
        if(exerciseDto.getId() == null){
            return new ExerciseList();
        }
        Optional<ExerciseList> exerciseList = exerciseRepository.findById(exerciseDto.getId());

        return exerciseList.orElseGet(ExerciseList::new);
    }

    public ExerciseList findByName(ExerciseDto exerciseDto){
        if(exerciseDto.getName() == null){
            return new ExerciseList();
        }
        Optional<ExerciseList> exerciseList = exerciseRepository.findByName(exerciseDto.getName());

        return exerciseList.orElseGet(ExerciseList::new);
    }

    public ExerciseList save(ExerciseDto exerciseDto){
        ExerciseList exerciseList = exerciseDto.toEntity();
        if(exerciseDto.getName() == null || exerciseDto.getCategory() == null){
            log.error("error occur");
            return new ExerciseList();
        }
        exerciseRepository.save(exerciseList);
        return exerciseList;
    }
}
