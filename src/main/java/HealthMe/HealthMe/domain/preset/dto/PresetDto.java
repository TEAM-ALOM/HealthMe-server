package HealthMe.HealthMe.domain.preset.dto;

import HealthMe.HealthMe.domain.preset.domain.Preset;
import HealthMe.HealthMe.domain.user.domain.User;
import HealthMe.HealthMe.domain.user.dto.UserDto;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PresetDto {
    private Long id;    // pk를 위한 id

    private Long presetNumber;          // 프리셋 번호
    private Double weight;              // 무게
    private Integer setCount;           // 세트 수
    private Integer repetitionCount;    // 반복 횟수
    private Integer restTime;           // 쉬는 시간 (나중에 시간 자료형으로 변경 계획 있음)
    private String exerciseName;
    private String category;

    //private User user;                  // User 클래스 -> 어떻게 할지 생각, 엔티티 직접 사용 x -> 차라리 userDto를 사용
    private UserDto userDto;              //  user 엔티티 대신 userDto로 사용
    @Builder
    public PresetDto(Long id, Long presetNumber, Double weight, Integer setCount, Integer repetitionCount,
                     Integer restTime, String exerciseName, String category, UserDto userDto){
        this.id = id;
        this.presetNumber = presetNumber;
        this.weight = weight;
        this.setCount = setCount;
        this.repetitionCount = repetitionCount;
        this.restTime = restTime;
        this.exerciseName = exerciseName;
        this.category = category;
        this.userDto = userDto;
    }

    public Preset toEntity(User user){    // dto -> entity 변환
        return Preset.builder()
                .id(id)
                .presetNumber(presetNumber)
                .weight(weight)
                .setCount(setCount)
                .repetitionCount(repetitionCount)
                .restTime(restTime)
                .exerciseName(exerciseName)
                .category(category)
                .user(userDto.toEntity())
                .build();
    }

}
