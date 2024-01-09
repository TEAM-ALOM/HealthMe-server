package HealthMe.HealthMe.domain.preset.dto;

import HealthMe.HealthMe.domain.preset.domain.Preset;
import HealthMe.HealthMe.domain.user.domain.User;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PresetDto {
    @Hidden
    private Long id;    // pk를 위한 id

    private Long presetNumber;          // 프리셋 번호
    private Double weight;              // 무게
    private Integer setCount;           // 세트 수
    private Integer repetitionCount;    // 반복 횟수
   // private Integer restTime;           // 쉬는 시간 (나중에 시간 자료형으로 변경 계획 있음)
    private String exerciseName;
    private String category;
    private String userEmail;

    @Builder
    public PresetDto(Long id, Long presetNumber, Double weight, Integer setCount, Integer repetitionCount,
                     String exerciseName, String category, String userEmail){
        this.id = id;
        this.presetNumber = presetNumber;
        this.weight = weight;
        this.setCount = setCount;
        this.repetitionCount = repetitionCount;
        this.exerciseName = exerciseName;
        this.category = category;
        this.userEmail =userEmail;
    }

    public Preset toEntity(User user){    // dto -> entity 변환
        return Preset.builder()
                .id(id)
                .presetNumber(presetNumber)
                .weight(weight)
                .setCount(setCount)
                .repetitionCount(repetitionCount)
                .exerciseName(exerciseName)
                .category(category)
                .user(user)
                .build();
    }
}
