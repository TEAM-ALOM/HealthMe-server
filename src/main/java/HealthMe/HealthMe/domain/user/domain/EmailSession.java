package HealthMe.HealthMe.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "EMAIL_SESSION")
public class EmailSession {
    @Id
    @Column(name="SESSION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String verifyCode;
    private String email;

    @CreationTimestamp
    @Column(name="createdTime")
    private LocalDateTime createdTime;

    public void reSetVerifyCode(String verifyCode, LocalDateTime createdTime) {
        this.verifyCode = verifyCode;
        this.createdTime = createdTime;
    }
}
