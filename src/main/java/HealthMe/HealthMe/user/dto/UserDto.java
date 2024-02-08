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
public class UserDto {
    private double height;
    private double weight;
    private String gender;
    private Date birthday;
    private String name;
    private String email;
    private double bmi;
    @Builder
    public UserDto(String email, double height, double weight, String gender, Date birthday, String name, double bmi) {
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.birthday = birthday;
        this.name = name;
        this.email = email;
        this.bmi = bmi;
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
