package HealthMe.HealthMe.common.token;
import lombok.*;


@Builder
@Data
@AllArgsConstructor
public class AuthToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}