package HealthMe.HealthMe.food.service;

import HealthMe.HealthMe.common.dto.PagingDto;
import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.exception.ErrorCode;
import HealthMe.HealthMe.food.domain.FoodList;
import HealthMe.HealthMe.food.dto.FoodListDto;
import HealthMe.HealthMe.food.dto.PagingFoodListDto;
import HealthMe.HealthMe.food.repository.FoodListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public List<PagingFoodListDto> findAllUsingPage(PagingDto pagingDto){
        Page<FoodList> allByPageable = foodListRepository.findAll(PageRequest.of(pagingDto.getIdx(), 50));
        List<PagingFoodListDto> result = new ArrayList<>();
        for (FoodList foodList : allByPageable) {
            result.add(PagingFoodListDto.builder()
                    .fat(foodList.getFat())
                    .name(foodList.getName())
                    .calorie(foodList.getCalorie())
                    .mass(foodList.getMass())
                    .protein(foodList.getProtein())
                    .carbohydrate(foodList.getCarbohydrate())
                    .idx(pagingDto.getIdx())
                    .build());
        }
        return result;
    }

}
