package HealthMe.HealthMe.food.dto;

import HealthMe.HealthMe.food.domain.FoodList;
import HealthMe.HealthMe.food.domain.IngestionList;
import HealthMe.HealthMe.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class IngestionListDto {

    private Date date;  // 섭취 날짜
    private Double mass;
    private String userEmail;
    private String foodName;

    @Builder
    public IngestionListDto(Date date, Double mass, String userEmail, String foodName) {
        this.date = date;
        this.mass = mass;
        this.userEmail = userEmail;
        this.foodName = foodName;
    }

    public IngestionList toEntity(User user, FoodList foodList){
        return IngestionList.builder()
                .date(date)
                .mass(mass)
                .user(user)
                .foodList(foodList)
                .build();
    }
}
