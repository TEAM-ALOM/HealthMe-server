package HealthMe.HealthMe.domain.user.domain;

import HealthMe.HealthMe.domain.exercise.domain.ExerciseProgressList;
import HealthMe.HealthMe.domain.food.domain.IngestionList;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity(name = "USER")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    private boolean autoLogin = false;  // 11/25 추가 : 자동 로그인 관련

    @OneToMany(mappedBy = "user")
    private List<IngestionList> ingestionLists = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ExerciseProgressList> exerciseProgressLists = new ArrayList<>();

    public User hashPassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
        return this;
    }

    public boolean checkPassword(String rawPassword, PasswordEncoder passwordEncoder){
        return passwordEncoder.matches(rawPassword, this.password);
    }
}


