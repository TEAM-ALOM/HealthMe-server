package HealthMe.HealthMe.domain.user.domain;

import HealthMe.HealthMe.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "EMAIL_SESSION")
public class EmailSession {
    @Id
    @Column(name="SESSION_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @Column(nullable = false, name="SESSION")     // 11/25 삭제 : 로직 내부에서 인증 코드 구분
//    private UUID session;

    @Column(nullable = false)
    private String verifyCode;  // 11/25 자료형 수정 : 랜덤 생성 서비스 내에서 구현
//    private UUID verityCode;

    private String email; // 11/25 추가 : 회원가입인데 email이 없어..

    @CreationTimestamp
    @Column(name="createdTime")
    private LocalDateTime createdTime;

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
