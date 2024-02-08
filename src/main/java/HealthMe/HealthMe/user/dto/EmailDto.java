package HealthMe.HealthMe.user.dto;

import HealthMe.HealthMe.user.domain.EmailSession;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// 11/25추가 : emailDto
@Setter
@Getter
@NoArgsConstructor
public class EmailDto {
    private String verifyCode;
    @Hidden
    private LocalDateTime createdTime;      // 11/25 추가 : 생성시간
    private String email;
    private boolean authResult;
    @Builder
    public EmailDto(String verifyCode, LocalDateTime createdTime, String email, boolean authResult) {
        this.verifyCode = verifyCode;
        this.email = email;
        this.createdTime = createdTime;
        this.authResult =authResult;
    }

    public EmailSession toEntity(){
        return EmailSession.builder()
                .verifyCode(verifyCode)
                .email(email)
                .build();
    }
}
