package HealthMe.HealthMe.domain.food.dto;

import HealthMe.HealthMe.domain.food.domain.FoodList;
import HealthMe.HealthMe.domain.food.domain.IngestionList;
import HealthMe.HealthMe.domain.user.domain.User;
import HealthMe.HealthMe.domain.user.dto.UserDto;
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
    private FoodListDto foodListDto;
    private UserDto userDto;

    @Builder
    public IngestionListDto(Long id,
                            Date date,
                            User user,
                            Double mass,
                            FoodListDto foodListDto,
                            UserDto userDto) {
        this.id = id;
        this.date = date;
        this.user = user;
        this.mass = mass;
        this.foodListDto = foodListDto;
        this.userDto = userDto;
    }

    public IngestionList toEntity(){
        return IngestionList.builder()
                .id(id)
                .date(date)
                .user(user)
                .mass(mass)
                .foodList(foodListDto.toEntity())
                .build();
    }
}
