package HealthMe.HealthMe.domain.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "USER_BODY_INFORMATION")
public class UserBodyInformation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_BODY_INFORMATION_ID")
    private Long id;

    private String height;
    private String weight;
    private String gender;
    private Integer age;

    @OneToOne
    @JoinColumn(name="USER_ID")
    private User user;
}
