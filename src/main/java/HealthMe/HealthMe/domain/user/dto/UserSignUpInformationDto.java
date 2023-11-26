package HealthMe.HealthMe.domain.user.dto;


import HealthMe.HealthMe.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class UserSignUpInformationDto {
    private double height;
    private double weight;
    private String gender;
    private Date birthday;
    private String name;
    private String email;

    @Builder
    public UserSignUpInformationDto(String email, double height, double weight, String gender, Date birthday, String name) {
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.birthday = birthday;
        this.name = name;
        this.email = email;
    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .gender(gender)
                .height(height)
                .weight(weight)
                .birthday(birthday)
                .build();
    }
}
