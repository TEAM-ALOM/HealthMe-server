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
                .nickname(searchedUser.getNickname())
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


    public List<ExerciseProgressList> findProgressedExerciseByEmail(User user){
        if (user.getEmail() == null){
            return null;
        }
        List<ExerciseProgressList> list = exerciseProgressRepository.findByEmail(user.getEmail());
        return list;
    }
    // user가 해당 날짜에 해당하는 운동 진행 내역들 찾기 (user Dto와 연계하여 사용해야됨)
    public List<ExerciseProgressList> findProgressedExerciseByDate(ExerciseProgressDto exerciseProgressDto, User user){
        if (user.getEmail() == null || exerciseProgressDto.getDate() == null){
            return null;
        }
        List<ExerciseProgressList> lists = exerciseProgressRepository.findExerciseProgressListsByEmailAndDate(user.getEmail(), exerciseProgressDto.getDate());

        return lists;
    }

}
