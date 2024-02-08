package HealthMe.HealthMe.preset.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SplitExerciseDto {
    private Integer exerciseNumber;
    private String exerciseName;
    private String category;
    private List<SplitExerciseDetailDto> splitExerciseDetailDto;

    @Builder

    public SplitExerciseDto(Integer exerciseNumber, String exerciseName, String category, List<SplitExerciseDetailDto> splitExerciseDetailDto) {
        this.exerciseNumber = exerciseNumber;
        this.exerciseName = exerciseName;
        this.category = category;
        this.splitExerciseDetailDto = splitExerciseDetailDto;
    }
}
