package HealthMe.HealthMe.domain.exercise.dto;

import HealthMe.HealthMe.domain.exercise.domain.ExerciseProgressList;
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
    private String exercise;
    private Double weight;  // 무게
    private Integer setCount;   // 세트 수
    private Integer repetitionCount;    // 반복 횟수
    @Builder
    public ExerciseProgressDto(Long id, Date date, String exercise, Double weight, Integer setCount, Integer repetitionCount){
        this.id = id;
        this.date = date;
        this.exercise = exercise;
        this.weight = weight;
        this.setCount = setCount;
        this.repetitionCount = repetitionCount;
    }

    public ExerciseProgressList toEntity(){
        return ExerciseProgressList.builder()
                .id(id)
                .date(date)
                .exercise(exercise)
                .weight(weight)
                .setCount(setCount)
                .repetitionCount(repetitionCount)
                .build();

    }
}
