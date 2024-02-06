package HealthMe.HealthMe.domain.preset.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SplitExerciseDetailDto {
    private Double weight;
    private Integer setCount;
    private Integer repetitionCount;
    private Integer order;

    @Builder
    public SplitExerciseDetailDto(Double weight, Integer setCount, Integer repetitionCount, Integer order) {
        this.weight = weight;
        this.setCount = setCount;
        this.repetitionCount = repetitionCount;
        this.order = order;
    }
}
