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
    private String name;
    private boolean autoLogin; // 11/25 추가 : 자동 로그인 관련
    @Builder
    public UserDto(Long id, String email, String name, boolean autoLogin){
        this.id = id;
        this.email = email;
        this.name = name;
        this.autoLogin = autoLogin;
    }
    public User toEntity(){
        return User.builder()
                .id(id)
                .email(email)
                .name(name)
                .autoLogin(autoLogin)
                .build();
    }

}
