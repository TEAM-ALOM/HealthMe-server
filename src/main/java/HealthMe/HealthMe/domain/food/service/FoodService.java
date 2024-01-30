package HealthMe.HealthMe.domain.food.service;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.exception.ErrorCode;
import HealthMe.HealthMe.domain.food.domain.FoodList;
import HealthMe.HealthMe.domain.food.dto.FoodListDto;
import HealthMe.HealthMe.domain.food.repository.FoodListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public FoodListDto findByName(FoodListDto foodListDto) throws CustomException {
        if(foodListDto == null){
            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND);
        }
        String foodName = foodListDto.getName();
        if(foodName == null) {
            throw new CustomException(ErrorCode.FOOD_NAME_NOT_FOUND);
        }

        FoodList foodList = foodListRepository.findByName(foodName)
                .orElseThrow(() -> new CustomException(ErrorCode.FOOD_NOT_FOUND));


        return FoodListDto.builder()
                .name(foodList.getName())
                .calorie(foodList.getCalorie())
                .mass(foodList.getMass())
                .protein(foodList.getProtein())
                .fat(foodList.getFat())
                .carbohydrate(foodList.getCarbohydrate())
                .build();
    }

}
