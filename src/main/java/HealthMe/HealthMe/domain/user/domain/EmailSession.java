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
    private Long id;

    @Column(nullable = false, name="SESSION")
    private UUID session;

    @Column(nullable = false)
    private UUID verityCode;

    @CreationTimestamp
    @Column(name="createdTime")
    private LocalDateTime createdTime;

    @OneToOne
    @JoinColumn(name="USER_ID")
    private User user;


}
