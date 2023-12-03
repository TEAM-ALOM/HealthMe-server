package HealthMe.HealthMe.domain.food.service;

import HealthMe.HealthMe.domain.food.domain.FoodList;
import HealthMe.HealthMe.domain.food.dto.FoodListDto;
import HealthMe.HealthMe.domain.food.dto.IngestionListDto;
import HealthMe.HealthMe.domain.food.repository.FoodListRepository;
import HealthMe.HealthMe.domain.food.repository.IngestionListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class IngestionService {

    private final FoodListRepository foodListRepository;
    private final IngestionListRepository ingestionListRepository;

    public IngestionListDto saveIngestion(FoodListDto foodListDto, IngestionListDto ingestionListDto){
        FoodList foodList = foodListRepository.findByName(foodListDto.getName());
        Date date = ingestionListDto.getDate();

        return IngestionListDto.builder()
                .date(date)
                .user(ingestionListDto.getUser())
                .mass(ingestionListDto.getMass())
                .foodListDto(ingestionListDto.getFoodListDto())
                .userDto(ingestionListDto.getUserDto())
                .build();

    }
}
