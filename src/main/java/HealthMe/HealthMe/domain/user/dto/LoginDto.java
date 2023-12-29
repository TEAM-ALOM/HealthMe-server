package HealthMe.HealthMe.domain.user.dto;

import HealthMe.HealthMe.domain.user.domain.User;
import jakarta.servlet.http.HttpSession;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LoginDto {
    private String email;
    private String password;
    private boolean autoLogin;

    @Builder
    public LoginDto(String email, HttpSession session, boolean autoLogin, String password) {
        this.email = email;
        this.autoLogin = autoLogin;
        this.password = password;
    }

    public User toEntity(){
        return User.builder()
                .email(email)
                .autoLogin(autoLogin)
                .password(password)
                .build();
    }

}
