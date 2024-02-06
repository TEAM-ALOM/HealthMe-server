package HealthMe.HealthMe.domain.preset.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UnSplitExerciseDto {
    private Integer exerciseNumber;
    private String exerciseName;
    private String category;
    private Double weight;
    private Integer setCount;
    private Integer repetitionCount;
    private Integer order;

    @Builder
    public UnSplitExerciseDto(Integer exerciseNumber, String exerciseName, String category, Double weight, Integer setCount, Integer repetitionCount, Integer order) {
        this.exerciseNumber = exerciseNumber;
        this.exerciseName = exerciseName;
        this.category = category;
        this.weight = weight;
        this.setCount = setCount;
        this.repetitionCount = repetitionCount;
        this.order = order;
    }
}
