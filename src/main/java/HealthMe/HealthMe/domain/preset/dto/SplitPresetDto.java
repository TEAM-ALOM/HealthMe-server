package HealthMe.HealthMe.domain.preset.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SplitPresetDto {
    private String email;
    private Long presetNumber;
    private String presetName;
    private List<UnSplitExerciseDto> unSplitExerciseDto;

    @Builder
    public SplitPresetDto(String email, Long presetNumber, String presetName, List<UnSplitExerciseDto> unSplitExerciseDto) {
        this.email = email;
        this.presetNumber = presetNumber;
        this.presetName = presetName;
        this.unSplitExerciseDto = unSplitExerciseDto;
    }
}
