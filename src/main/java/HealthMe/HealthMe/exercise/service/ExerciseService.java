package HealthMe.HealthMe.exercise.service;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.exception.ErrorCode;
import HealthMe.HealthMe.exercise.domain.ExerciseList;
import HealthMe.HealthMe.exercise.dto.ExerciseDto;
import HealthMe.HealthMe.exercise.repository.ExerciseRepository;
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
        String name = exerciseDto.getName();
        if(name == null){
            throw new CustomException(ErrorCode.EXERCISE_NAME_NOT_FOUND);
        }

        ExerciseList exerciseList = exerciseRepository.findByName(name)
                .orElseThrow(() -> new CustomException(ErrorCode.EXERCISE_NOT_FOUND));

        return ExerciseDto.builder()
                .id(exerciseList.getId())
                .name(exerciseList.getName())
                .category(exerciseList.getCategory())
                .calorie(exerciseList.getCalorie())
                .build();
    }

    @Transactional
    public ExerciseDto save(ExerciseDto exerciseDto) throws CustomException {
        if(exerciseDto == null){
            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND);
        }

        String name = exerciseDto.getName();
        String category = exerciseDto.getCategory();
        if(name == null){
            throw new CustomException(ErrorCode.EXERCISE_NAME_NOT_FOUND);
        }
        if(category == null){
            throw new CustomException(ErrorCode.EXERCISE_CATEGORY_NOT_FOUND);
        }

        ExerciseList exerciseList = exerciseDto.toEntity();
        exerciseRepository.save(exerciseList);

        return exerciseDto;
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
