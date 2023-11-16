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
    private String email;
    private String exercise;

    @Builder
    public ExerciseProgressDto(Long id, Date date, String email, String exercise){
        this.id = id;
        this.date = date;
        this.email = email;
        this.exercise = exercise;
    }

    public ExerciseProgressList toEntity(){
        return ExerciseProgressList.builder()
                .id(id)
                .date(date)
                .email(email)
                .exercise(exercise)
                .build();

    }
}
