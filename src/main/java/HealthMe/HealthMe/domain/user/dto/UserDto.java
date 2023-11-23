package HealthMe.HealthMe.domain.user.dto;

import HealthMe.HealthMe.domain.exercise.domain.ExerciseList;
import HealthMe.HealthMe.domain.exercise.domain.ExerciseProgressList;
import HealthMe.HealthMe.domain.food.domain.IngestionList;
import HealthMe.HealthMe.domain.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String nickname;
    private String name;
    @Builder
    public UserDto(Long id, String email, String nickname, String name){
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.name = name;
    }
    public User toEntity(){
        return User.builder()
                .id(id)
                .email(email)
                .name(name)
                .build();
    }
}
