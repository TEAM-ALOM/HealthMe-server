package HealthMe.HealthMe.common.token.dto;


import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RefreshDto {
    @Schema(description = "유저 이메일", example = "example@email.com")
    private String email;
    @Schema(description = "access token")
    @Hidden
    private String accessToken;
    @Schema(description = "refresh token")
    private String refreshToken;
}
