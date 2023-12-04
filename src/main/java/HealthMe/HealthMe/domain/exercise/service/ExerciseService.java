package HealthMe.HealthMe.domain.exercise.service;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.exception.ErrorCode;
import HealthMe.HealthMe.domain.exercise.domain.ExerciseList;
import HealthMe.HealthMe.domain.exercise.dto.ExerciseDto;
import HealthMe.HealthMe.domain.exercise.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    public ExerciseList findByName(ExerciseDto exerciseDto) throws CustomException {
        if(exerciseDto == null){
            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND);
        }

        if(exerciseDto.getName() == null){
            throw new CustomException(ErrorCode.EXERCISE_NAME_NOT_FOUND);
        }

        ExerciseList exerciseList = exerciseRepository.findByName(exerciseDto.getName());
        if(exerciseList == null){
            throw new CustomException(ErrorCode.EXERCISE_NOT_FOUND);
        }

        return exerciseList;
    }

    @Transactional
    public ExerciseList save(ExerciseDto exerciseDto) throws CustomException {
        if(exerciseDto == null){
            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND);
        }
        if(exerciseDto.getName() == null){
            throw new CustomException(ErrorCode.EXERCISE_NAME_NOT_FOUND);
        }
        if(exerciseDto.getCategory() == null){
            throw new CustomException(ErrorCode.EXERCISE_CATEGORY_NOT_FOUND);
        }

        ExerciseList exerciseList = exerciseDto.toEntity();
        ExerciseList save = exerciseRepository.save(exerciseList);
        return save;
    }

    public List<ExerciseList> findAll(){
        return exerciseRepository.findAll();
    }


}
