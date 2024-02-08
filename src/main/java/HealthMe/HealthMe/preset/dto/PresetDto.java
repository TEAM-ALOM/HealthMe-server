package HealthMe.HealthMe.preset.dto;

import HealthMe.HealthMe.preset.domain.Preset;
import HealthMe.HealthMe.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * TODO : 세트 수랑 무게 리스트로 리턴
 */
@Setter
@Getter
@NoArgsConstructor
public class PresetDto {
    private Long presetNumber;          // 프리셋 번호
    private Long exerciseNumber;        // 운동 번호
    private String presetTitle;         // 프리셋 제목
    private Double weight;              // 무게
    private Integer setCount;           // 세트 수
    private Integer repetitionCount;    // 반복 횟수
    private String exerciseName;
    private String category;

    @Builder
    public PresetDto(Long presetNumber, Double weight, Integer setCount, Integer repetitionCount,
                     String exerciseName, String category, String presetTitle, Long exerciseNumber){
        this.presetNumber = presetNumber;
        this.weight = weight;
        this.setCount = setCount;
        this.repetitionCount = repetitionCount;
        this.exerciseName = exerciseName;
        this.category = category;
        this.presetTitle = presetTitle;
        this.exerciseNumber = exerciseNumber;
    }

    public Preset toEntity(User user){    // dto -> entity 변환
        return Preset.builder()
                .presetTitle(presetTitle)
                .exerciseNumber(exerciseNumber)
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
