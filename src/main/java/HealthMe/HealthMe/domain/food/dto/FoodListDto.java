package HealthMe.HealthMe.domain.food.dto;

import HealthMe.HealthMe.domain.food.domain.FoodList;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class FoodListDto {
    private Long id;    // pk를 위한 id
    private String name;    // 음식 명
    private Double carbohydrate;    // 탄수화물
    private Double protein; // 단백질
    private Double fat; // 지방
    private Double calorie; // 칼로리

    @Builder
    public FoodListDto(Long id,
                       String name,
                       Double carbohydrate,
                       Double protein,
                       Double fat,
                       Double calorie) {
        this.id = id;
        this.name = name;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
        this.calorie = calorie;
    }

    public FoodList toEntity(){
        return FoodList.builder()
                .id(id)
                .name(name)
                .carbohydrate(carbohydrate)
                .protein(protein)
                .fat(fat)
                .calorie(calorie)
                .build();
    }
}
