package HealthMe.HealthMe.domain.food.dto;

import HealthMe.HealthMe.domain.food.domain.FoodList;
import HealthMe.HealthMe.domain.food.domain.IngestionList;
import HealthMe.HealthMe.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class IngestionListDto {
    private Long id;    // pk를 위한 id
    private Date date;  // 섭취 날짜
    private User user;
    private Double mass;
    private String userEmail;
    private String foodName;

    @Builder
    public IngestionListDto(Long id, Date date, User user, Double mass, String userEmail, String foodName) {
        this.id = id;
        this.date = date;
        this.user = user;
        this.mass = mass;
        this.userEmail = userEmail;
        this.foodName = foodName;
    }

    public IngestionList toEntity(User user, FoodList foodList){
        return IngestionList.builder()
                .id(id)
                .date(date)
                .mass(mass)
                .user(user)
                .foodList(foodList)
                .build();
    }
}
