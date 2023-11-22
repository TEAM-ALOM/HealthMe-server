package HealthMe.HealthMe.domain.exercise.dto;

import HealthMe.HealthMe.domain.exercise.domain.ExerciseList;
import HealthMe.HealthMe.domain.exercise.domain.ExerciseProgressList;
import HealthMe.HealthMe.domain.user.domain.User;

import HealthMe.HealthMe.domain.user.dto.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ExerciseProgressDto {
    private Long id;
    private Date date;
    private Double weight;  // 무게
    private
    Integer setCount;   // 세트 수
    private Integer repetitionCount;    // 반복 횟수

    private UserDto userDto;
    private ExerciseDto exerciseDto;

    @Builder
    public ExerciseProgressDto(Long id,
                               Date date,
                               Double weight,
                               Integer setCount,
                               Integer repetitionCount,
                               UserDto userDto,
                               ExerciseDto exerciseDto){
        this.id = id;
        this.date = date;
        this.weight = weight;
        this.setCount = setCount;
        this.repetitionCount = repetitionCount;
        this.userDto = userDto;
        this.exerciseDto = exerciseDto;
    }

    public ExerciseProgressList toEntity(UserDto userDto, ExerciseDto exerciseDto){
        return ExerciseProgressList.builder()
                .user(userDto.toEntity())
                .exerciseList(exerciseDto.toEntity())
                .id(id)
                .date(date)
                .weight(weight)
                .setCount(setCount)
                .repetitionCount(repetitionCount)
                .build();
    }
}
