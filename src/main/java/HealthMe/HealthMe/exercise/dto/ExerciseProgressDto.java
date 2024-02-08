package HealthMe.HealthMe.exercise.dto;

import HealthMe.HealthMe.exercise.domain.ExerciseProgressList;

import HealthMe.HealthMe.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ExerciseProgressDto {
    @Hidden
    private Long id;
    private Date date;
    private Double weight;  // 무게
    private Integer setCount;   // 세트 수
    private Integer repetitionCount;    // 반복 횟수

    private String userEmail;
    private String exerciseName;

    @Builder
    public ExerciseProgressDto(Long id,
                               Date date,
                               Double weight,
                               Integer setCount,
                               Integer repetitionCount,
                               String userEmail,
                               String exerciseName){
        this.id = id;
        this.date = date;
        this.weight = weight;
        this.setCount = setCount;
        this.repetitionCount = repetitionCount;
        this.userEmail = userEmail;
        this.exerciseName = exerciseName;
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
