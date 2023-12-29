package HealthMe.HealthMe.common.token.dto;
import lombok.*;


@Builder
@Data
@AllArgsConstructor
public class AuthToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}