package HealthMe.HealthMe.common.token.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RefreshDto {
    private String email;
    private String accessToken;
    private String refreshToken;
}
