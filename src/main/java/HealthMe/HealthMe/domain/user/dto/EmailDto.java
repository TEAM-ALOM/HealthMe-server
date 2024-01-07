package HealthMe.HealthMe.domain.user.dto;

import HealthMe.HealthMe.domain.user.domain.EmailSession;
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
    private Long id;
    private String verifyCode;
    private LocalDateTime createdTime;      // 11/25 추가 : 생성시간
    private String email;
    private UserDto userDto;
    private boolean authResult;
    @Builder
    public EmailDto(Long id, String verifyCode, UserDto userDto, LocalDateTime createdTime, String email, boolean authResult) {
        this.id = id;
        this.verifyCode = verifyCode;
        this.email = email;
        this.createdTime = createdTime;
        this.userDto=userDto;
        this.authResult =authResult;
    }

    public EmailSession toEntity(){
        return EmailSession.builder()
                .verifyCode(verifyCode)
                .email(email)
                .build();
    }
}
