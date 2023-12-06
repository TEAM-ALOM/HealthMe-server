package HealthMe.HealthMe.domain.exercise.service;


import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.exception.ErrorCode;
import HealthMe.HealthMe.domain.exercise.domain.ExerciseList;
import HealthMe.HealthMe.domain.exercise.domain.ExerciseProgressList;
import HealthMe.HealthMe.domain.exercise.dto.ExerciseDto;
import HealthMe.HealthMe.domain.exercise.dto.ExerciseProgressDto;
import HealthMe.HealthMe.domain.exercise.repository.ExerciseProgressRepository;
import HealthMe.HealthMe.domain.exercise.repository.ExerciseRepository;
import HealthMe.HealthMe.domain.user.domain.User;

import HealthMe.HealthMe.domain.user.dto.UserDto;
import HealthMe.HealthMe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExerciseProgressService {
    private final ExerciseProgressRepository exerciseProgressRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;
    @Transactional
    public void insert(ExerciseProgressDto exerciseProgressDto) throws CustomException {

        String userEmail = exerciseProgressDto.getUserEmail();
        if(userEmail == null){
            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
        }

        User searchedUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));


        UserDto insertedUser = UserDto.builder()
                    .id(searchedUser.getId())
                    .email(searchedUser.getEmail())
                    .name(searchedUser.getName())
                    .build();

        String exerciseName = exerciseProgressDto.getExerciseName();
        if(exerciseName == null){
            throw new CustomException(ErrorCode.EXERCISE_NAME_NOT_FOUND);
        }

        ExerciseList searchedExercise = exerciseRepository.findByName(exerciseName)
                .orElseThrow(() -> new CustomException(ErrorCode.EXERCISE_NOT_FOUND));

        ExerciseDto insertedExercise = ExerciseDto.builder()
                        .id(searchedExercise.getId())
                        .name(searchedExercise.getName())
                        .calorie(searchedExercise.getCalorie())
                        .category(searchedExercise.getCategory())
                        .build();


        ExerciseProgressList exerciseProgressList = exerciseProgressDto.toEntity(insertedUser, insertedExercise);
        exerciseProgressRepository.save(exerciseProgressList);
    }


    public List<ExerciseProgressDto> findProgressedExerciseByEmail(UserDto userDto) throws CustomException {
        if (userDto.getEmail() == null){
            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
        }
        List<ExerciseProgressList> list = exerciseProgressRepository.findByEmail(userDto.getEmail());
        List<ExerciseProgressDto> find = new ArrayList<>();

        for (ExerciseProgressList exerciseProgressList : list) {
            ExerciseList exerciseList = exerciseProgressList.getExerciseList();
            ExerciseDto exerciseDto = ExerciseDto.builder()
                    .category(exerciseList.getCategory())
                    .calorie(exerciseList.getCalorie())
                    .name(exerciseList.getName())
                    .build();

            ExerciseProgressDto exerciseProgressDto = ExerciseProgressDto.builder()
                    .date(exerciseProgressList.getDate())
                    .repetitionCount(exerciseProgressList.getRepetitionCount())
                    .setCount(exerciseProgressList.getSetCount())
                    .weight(exerciseProgressList.getWeight())
                    .userEmail(userDto.getEmail())
                    .exerciseName(exerciseDto.getName())
                    .build();


            find.add(exerciseProgressDto);
        }
        return find;
    }

    public List<ExerciseProgressDto> findProgressedExerciseByDate(ExerciseProgressDto exerciseProgressDto) throws CustomException {
        if (exerciseProgressDto.getDate() == null){
            throw new CustomException(ErrorCode.DATE_NOT_FOUND);
        }
        List<ExerciseProgressList> list = exerciseProgressRepository.findExerciseProgressListsByDate(exerciseProgressDto.getDate());
        List<ExerciseProgressDto> findList = new ArrayList<>();

        for (ExerciseProgressList exerciseProgressList : list) {
            User findUser = exerciseProgressList.getUser();
            ExerciseList findExercise = exerciseProgressList.getExerciseList();

            UserDto findUserDto = UserDto.builder()
                    .id(findUser.getId())
                    .email(findUser.getEmail())
                    .name(findUser.getName())
                    .build();

            ExerciseDto findExerciseDto = ExerciseDto.builder()
                    .id(findExercise.getId())
                    .name(findExercise.getName())
                    .calorie(findExercise.getCalorie())
                    .category(findExercise.getCategory())
                    .build();

            ExerciseProgressDto find = ExerciseProgressDto.builder()
                    .id(exerciseProgressList.getId())
                    .exerciseName(findExerciseDto.getName())
                    .date(exerciseProgressList.getDate())
                    .userEmail(findUserDto.getEmail())
                    .weight(exerciseProgressList.getWeight())
                    .repetitionCount(exerciseProgressList.getRepetitionCount())
                    .build();

            findList.add(find);
        }
        return findList;
    }

}
