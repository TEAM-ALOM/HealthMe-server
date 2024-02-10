package HealthMe.HealthMe.preset.domain;

import HealthMe.HealthMe.user.domain.User;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "PRESET")
public class Preset{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRESET_ID")
    private Long id;

    private Long presetNumber;
    private String presetTitle;
    private Long exerciseNumber;
    private Integer exerciseOrder;
    private Double weight;
    private Integer setCount;
    private Integer repetitionCount;
    private String exerciseName;
    private String category;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public void setPresetNumber(Long presetNumber){
        this.presetNumber = presetNumber;
    }
}
