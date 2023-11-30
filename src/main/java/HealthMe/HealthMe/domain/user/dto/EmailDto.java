package HealthMe.HealthMe.domain.user.dto;

import HealthMe.HealthMe.domain.user.domain.EmailSession;
import HealthMe.HealthMe.domain.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

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
    @Builder
    public EmailDto(Long id, String verifyCode, UserDto userDto, LocalDateTime createdTime, String email) {
        this.id = id;
        this.verifyCode = verifyCode;
        this.email = email;
        this.createdTime = createdTime;
        this.userDto=userDto;
    }

    public EmailSession toEntity(){
        return EmailSession.builder()
                .verifyCode(verifyCode)
                .email(email)
                .build();
    }
}
