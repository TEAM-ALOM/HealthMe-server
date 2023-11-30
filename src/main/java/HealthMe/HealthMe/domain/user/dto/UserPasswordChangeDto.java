package HealthMe.HealthMe.domain.user.dto;

import HealthMe.HealthMe.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserPasswordChangeDto {
    private String email;
    private String password;
    private String changedPassword;

    @Builder
    public UserPasswordChangeDto(String email, String password, String changedPassword) {
        this.email = email;
        this.password = password;
        this.changedPassword = changedPassword;
    }

    public User toEntity(){
        return User.builder()
                .password(changedPassword)
                .build();
    }
}
