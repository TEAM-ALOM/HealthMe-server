package HealthMe.HealthMe.domain.user.domain;

import HealthMe.HealthMe.domain.exercise.domain.ExerciseProgressList;
import HealthMe.HealthMe.domain.food.domain.IngestionList;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
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
    private boolean autoLogin = true;

    // 11/26 수정 : userBodyInformation table 삭제에 따른 column 합체
    private double height;
    private double weight;
    private String gender;
    private Date birthday;

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

    public void getUserBodyInformation(String name, Date birthday, double height, double weight, String gender){
        this.name = name;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
    }

    public void updatePassword(String password){
        this.password = password;
    }
}


