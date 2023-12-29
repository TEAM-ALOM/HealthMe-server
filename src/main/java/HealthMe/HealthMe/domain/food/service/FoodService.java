package HealthMe.HealthMe.domain.food.service;

import HealthMe.HealthMe.domain.food.domain.FoodList;
import HealthMe.HealthMe.domain.food.dto.FoodListDto;
import HealthMe.HealthMe.domain.food.repository.FoodListRepository;
import HealthMe.HealthMe.domain.food.repository.IngestionListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FoodService {

    private final FoodListRepository foodListRepository;

    public List<FoodListDto> findAll(){
        List<FoodListDto> find = new ArrayList<>();
        List<FoodList> all = foodListRepository.findAll();
        for (FoodList foodList : all) {
            find.add(FoodListDto.builder()
                    .id(foodList.getId())
                    .calorie(foodList.getCalorie())
                    .carbohydrate(foodList.getCarbohydrate())
                    .fat(foodList.getFat())
                    .protein(foodList.getProtein())
                    .name(foodList.getName())
                    .mass(foodList.getMass())
                    .build());
        }
        return find;
    }

}
