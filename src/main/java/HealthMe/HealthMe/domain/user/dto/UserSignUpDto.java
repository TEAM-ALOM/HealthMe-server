package HealthMe.HealthMe.domain.user.dto;

import HealthMe.HealthMe.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserSignUpDto {
    private String email;
    private String name;
    private String password;

    @Builder
    public UserSignUpDto(String email, String name, String password){
        this.email = email;
        this.name = name;
        this.password = password;
    }
    public User toEntity(){
        return User.builder()
                .email(email)
                .name(name)
                .password(password)
                .build();
    }
}
