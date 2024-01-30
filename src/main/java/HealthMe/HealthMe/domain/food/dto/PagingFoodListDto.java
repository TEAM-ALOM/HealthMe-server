package HealthMe.HealthMe.domain.food.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PagingFoodListDto {
    private String name;    // 음식 명
    private Double carbohydrate;    // 탄수화물
    private Double protein; // 단백질
    private Double fat; // 지방
    private Double calorie; // 칼로리
    private Double mass;
    private Integer idx; // 페이징 인덱스
    @Builder
    public PagingFoodListDto(String name,
                             Double carbohydrate,
                             Double protein,
                             Double fat,
                             Double calorie,
                             Double mass, Integer idx) {


        this.name = name;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
        this.calorie = calorie;
        this.mass = mass;
        this.idx = idx;
    }

}
