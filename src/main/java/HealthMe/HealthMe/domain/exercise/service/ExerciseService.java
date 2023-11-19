package HealthMe.HealthMe.domain.exercise.service;

import HealthMe.HealthMe.domain.exercise.domain.ExerciseList;
import HealthMe.HealthMe.domain.exercise.dto.ExerciseDto;
import HealthMe.HealthMe.domain.exercise.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// exerciseList와 관련된 service 완
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

        // optional 처리 공부
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

    public List<ExerciseList> findAll(){
        return exerciseRepository.findAll();
    }
}
