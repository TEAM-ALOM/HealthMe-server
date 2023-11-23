package HealthMe.HealthMe.domain.user.domain;

import HealthMe.HealthMe.domain.exercise.domain.ExerciseProgressList;
import HealthMe.HealthMe.domain.food.domain.IngestionList;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

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
//    private String nickname;   2023/11/23 삭제 : 불필요한 데이터
    private String name;

    @OneToMany(mappedBy = "user")
    private List<IngestionList> ingestionLists = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<ExerciseProgressList> exerciseProgressLists = new ArrayList<>();
}


