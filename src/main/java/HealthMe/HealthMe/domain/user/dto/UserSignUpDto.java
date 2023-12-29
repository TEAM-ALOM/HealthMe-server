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
    private Long id;
    private String email;
    private String name;
    private String password;
    private String authCode;
    @Builder
    public UserSignUpDto(Long id, String email, String name, String password, String authCode){
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.authCode = authCode;
    }
    public User toEntity(){
        return User.builder()
                .id(id)
                .email(email)
                .name(name)
                .password(password)
                .build();
    }
}
