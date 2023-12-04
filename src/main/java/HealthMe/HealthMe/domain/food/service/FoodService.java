package HealthMe.HealthMe.domain.food.service;

import HealthMe.HealthMe.domain.food.domain.FoodList;
import HealthMe.HealthMe.domain.food.dto.FoodListDto;
import HealthMe.HealthMe.domain.food.repository.FoodListRepository;
import HealthMe.HealthMe.domain.food.repository.IngestionListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FoodService {

    private final FoodListRepository foodListRepository;
    private final IngestionListRepository ingestionListRepository;


}
