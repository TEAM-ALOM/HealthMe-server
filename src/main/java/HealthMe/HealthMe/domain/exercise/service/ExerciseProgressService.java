package HealthMe.HealthMe.domain.exercise.service;


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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
// @Transactional 필요한 메서드만 붙이기 class단에 붙이면 느려짐
public class ExerciseProgressService {
    private final ExerciseProgressRepository exerciseProgressRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;
    //삽입
    public ExerciseProgressList insert(ExerciseProgressDto exerciseProgressDto){
        // 받은 parameter에 해당 정보 없을시 리턴 값 수정
        String userEmail = exerciseProgressDto.getUserDto().getEmail();
        String exerciseName = exerciseProgressDto.getExerciseDto().getName();

        // 해당 parameter에 해당 row 없을시 리턴 값 수정
        User searchedUser = userRepository.findByEmail(userEmail);
        Optional<ExerciseList> exerciseList = exerciseRepository.findByName(exerciseName);
        ExerciseList searchedExercise = exerciseList.get();

        UserDto insertedUser = UserDto.builder()
                .id(searchedUser.getId())
                .email(searchedUser.getEmail())
                .name(searchedUser.getName())
                .build();

        ExerciseDto insertedExercise = ExerciseDto.builder()
                .id(searchedExercise.getId())
                .name(searchedExercise.getName())
                .calorie(searchedExercise.getCalorie())
                .category(searchedExercise.getCategory())
                .build();

        ExerciseProgressList exerciseProgressList = exerciseProgressDto.toEntity(insertedUser, insertedExercise);
        ExerciseProgressList inserted = exerciseProgressRepository.save(exerciseProgressList);

        return inserted;
    }


    public List<ExerciseProgressDto> findProgressedExerciseByEmail(UserDto userDto){
        if (userDto.getEmail() == null){
            return null;
        }
        List<ExerciseProgressDto> find = new ArrayList<>();
        User searchedUser = userRepository.findByEmail(userDto.getEmail());
        UserDto searchedUserDto = UserDto.builder()
                .email(searchedUser.getEmail())
                .name(searchedUser.getName())
                .build();

        List<ExerciseProgressList> list = exerciseProgressRepository.findByEmail(userDto.getEmail());
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
                    .userDto(searchedUserDto)
                    .exerciseDto(exerciseDto)
                    .build();


            find.add(exerciseProgressDto);
        }
        return find;
    }
    // user가 해당 날짜에 해당하는 운동 진행 내역들 찾기 (user Dto와 연계하여 사용해야됨)
    public List<ExerciseProgressDto> findProgressedExerciseByDate(ExerciseProgressDto exerciseProgressDto){
        if (exerciseProgressDto.getDate() == null){
            return null;
        }
        List<ExerciseProgressList> list = exerciseProgressRepository.findExerciseProgressListsByDate(exerciseProgressDto.getDate());
        List<ExerciseProgressDto> findList = new ArrayList<>();
        for (ExerciseProgressList exerciseProgressList : list) {
            // 이때 객체가 null일수도 있음(entity - not null로 수정)
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
                    .exerciseDto(findExerciseDto)
                    .date(exerciseProgressList.getDate())
                    .userDto(findUserDto)
                    .weight(exerciseProgressList.getWeight())
                    .repetitionCount(exerciseProgressList.getRepetitionCount())
                    .build();

            findList.add(find);
        }
        return findList;
    }

}
