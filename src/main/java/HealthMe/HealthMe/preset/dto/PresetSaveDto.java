package HealthMe.HealthMe.preset.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PresetSaveDto {
    private String email;
    private List<PresetDto> presetDto;

    @Builder
    public PresetSaveDto(String email, List<PresetDto> presetDto) {
        this.email = email;
        this.presetDto = presetDto;
    }
}
