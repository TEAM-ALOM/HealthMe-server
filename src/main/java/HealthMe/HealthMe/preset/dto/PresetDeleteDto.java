package HealthMe.HealthMe.preset.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PresetDeleteDto {
    String email;
    Long presetNumber;

    @Builder
    public PresetDeleteDto(String email, Long presetNumber) {
        this.email = email;
        this.presetNumber = presetNumber;
    }
}
