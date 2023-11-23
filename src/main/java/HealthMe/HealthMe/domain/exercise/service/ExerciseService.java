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


/**
 * exception 처리
 */
@Service
@Slf4j
@RequiredArgsConstructor

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
            /**
             * exception 처리 : Error code(enum),custom exception (Error code 상속) 만들고,
             * global exception handler 따로 만들고
             * throw new custom excetion 던지기
             * @RestControllerAdvice : controller단에서 일어나는 모든 exception 받음
             * @ExceptionHandler(ex.class) : 해당 exception (ex)의 로직 처리
             */
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
