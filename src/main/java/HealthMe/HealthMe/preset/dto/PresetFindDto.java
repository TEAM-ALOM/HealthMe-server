package HealthMe.HealthMe.preset.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.method.P;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PresetFindDto {
    private String email;
    private Long presetId;

    @Builder
    public PresetFindDto(String email, Long presetId) {
        this.email = email;
        this.presetId = presetId;
    }
}
