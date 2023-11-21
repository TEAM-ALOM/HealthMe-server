package HealthMe.HealthMe.domain.exercise.service;


import HealthMe.HealthMe.domain.exercise.domain.ExerciseList;
import HealthMe.HealthMe.domain.exercise.domain.ExerciseProgressList;
import HealthMe.HealthMe.domain.exercise.dto.ExerciseDto;
import HealthMe.HealthMe.domain.exercise.dto.ExerciseProgressDto;
import HealthMe.HealthMe.domain.exercise.repository.ExerciseProgressRepository;
import HealthMe.HealthMe.domain.exercise.repository.ExerciseRepository;
import HealthMe.HealthMe.domain.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ExerciseProgressService {
    private final ExerciseProgressRepository exerciseProgressRepository;
    private final ExerciseRepository exerciseRepository;

    //삽입
    public ExerciseProgressList insert(ExerciseProgressDto exerciseProgressDto){
        return null;
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
