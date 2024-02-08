package HealthMe.HealthMe.user.dto;

import HealthMe.HealthMe.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class UserSignUpDto {
    private String email;
    private String name;
    private String password;
    private double height;
    private double weight;
    private String gender;
    private Date birthday;

    @Builder
    public UserSignUpDto(String email, String name, String password, double height, double weight, String gender, Date birthday){
        this.email = email;
        this.name = name;
        this.password = password;
        this.height = height;
        this.weight =weight;
        this.gender = gender;
        this.birthday = birthday;
    }
    public User toEntity(){
        return User.builder()
                .email(email)
                .name(name)
                .password(password)
                .weight(weight)
                .birthday(birthday)
                .height(height)
                .gender(gender)
                .build();
    }
}
