package HealthMe.HealthMe.domain.exercise.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="EXERCISE_LIST")
public class ExerciseList {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXERCISE_LIST_ID")
    private Long id;

    private String name;
    private Double calorie;

    @Column(nullable = false)
    private String category;

    @OneToMany(mappedBy = "exerciseList")
    private List<ExerciseProgressList> exerciseProgressLists = new ArrayList<>();

}
