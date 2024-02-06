package HealthMe.HealthMe.domain.preset.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PresetFindResultDto {
    private String email;
    private Long presetNumber;
    private String presetName;
    private List<SplitExerciseDto> splitPresetDto;

    @Builder
    public PresetFindResultDto(String email, Long presetNumber, String presetName, List<SplitExerciseDto> splitPresetDto) {
        this.email = email;
        this.presetNumber = presetNumber;
        this.presetName = presetName;
        this.splitPresetDto = splitPresetDto;
    }
}
