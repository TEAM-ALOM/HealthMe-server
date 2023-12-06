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


import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;

    public ExerciseDto findByName(ExerciseDto exerciseDto) throws CustomException {
        if(exerciseDto == null){
            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND);
        }

        if(exerciseDto.getName() == null){
            throw new CustomException(ErrorCode.EXERCISE_NAME_NOT_FOUND);
        }

        ExerciseList exerciseList = exerciseRepository.findByName(exerciseDto.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.EXERCISE_NOT_FOUND));

        return ExerciseDto.builder().id(exerciseList.getId())
                .name(exerciseList.getName())
                .category(exerciseList.getCategory())
                .calorie(exerciseList.getCalorie())
                .build();
    }

    @Transactional
    public void save(ExerciseDto exerciseDto) throws CustomException {
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
        exerciseRepository.save(exerciseList);
    }

    public List<ExerciseDto> findAll(){
        List<ExerciseList> find = exerciseRepository.findAll();
        List<ExerciseDto> result = new ArrayList<>();
        for (ExerciseList exerciseList : find) {
            result.add(ExerciseDto.builder()
                    .id(exerciseList.getId())
                    .name(exerciseList.getName())
                    .category(exerciseList.getCategory())
                    .calorie(exerciseList.getCalorie())
                    .build());
        }
        return result;
    }


}
