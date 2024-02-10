package HealthMe.HealthMe.preset.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SplitPresetDto {
    Long id;
    List<PresetDto> presetDtoList;

    @Builder
    public SplitPresetDto(Long id, List<PresetDto> presetDtoList) {
        this.id = id;
        this.presetDtoList = presetDtoList;
    }
}
