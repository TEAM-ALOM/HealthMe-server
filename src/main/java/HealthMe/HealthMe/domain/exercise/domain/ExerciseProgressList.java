package HealthMe.HealthMe.domain.exercise.domain;

import HealthMe.HealthMe.domain.user.domain.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name ="EXERCISE_PROGRESS_LIST")
public class ExerciseProgressList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EXERCISE_PROGRESS_LIST_ID")
    private Long id;

    @Column(nullable = false)
    private Date date;

    private Double weight;
    private Integer setCount;
    private Integer repetitionCount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="EXERCISE_LIST_ID")
    private ExerciseList exerciseList;

}
