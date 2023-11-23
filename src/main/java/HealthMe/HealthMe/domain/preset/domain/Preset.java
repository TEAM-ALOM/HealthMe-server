package HealthMe.HealthMe.domain.preset.domain;


import HealthMe.HealthMe.domain.exercise.domain.ExerciseList;
import HealthMe.HealthMe.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "PRESET")
public class Preset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRESET_ID")
    private Long id;

    private Long presetNumber;
    private Double weight;
    private Integer setCount;
    private Integer repetitionCount;
    private String exerciseName;
    private String category;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

}
