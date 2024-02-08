package HealthMe.HealthMe.food.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "FOOD_LIST")
public class FoodList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FOOD_LIST_ID")
    private Long id;

    private String name;
    private Double carbohydrate;
    private Double protein;
    private Double fat;
    private Double calorie;
    private Double mass;


}
