package HealthMe.HealthMe.domain.exercise.dto;


import HealthMe.HealthMe.domain.exercise.domain.ExerciseList;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class ExerciseDto {
    @Hidden
    private Long id;
    private String name;
    private Double calorie;
    private String category;

    @Builder
    public ExerciseDto(Long id, String name, Double calorie, String category){
        this.id = id;
        this.name = name;
        this.calorie = calorie;
        this.category = category;
    }


    public ExerciseList toEntity(){
        return ExerciseList.builder()
                .id(id)
                .name(name)
                .calorie(calorie)
                .category(category)
                .build();
    }
}
