package HealthMe.HealthMe.domain.food.service;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.exception.ErrorCode;
import HealthMe.HealthMe.domain.food.domain.FoodList;
import HealthMe.HealthMe.domain.food.domain.IngestionList;
import HealthMe.HealthMe.domain.food.dto.FoodListDto;
import HealthMe.HealthMe.domain.food.dto.IngestionListDto;
import HealthMe.HealthMe.domain.food.repository.FoodListRepository;
import HealthMe.HealthMe.domain.food.repository.IngestionListRepository;
import HealthMe.HealthMe.domain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngestionService {

    private final FoodListRepository foodListRepository;
    private final IngestionListRepository ingestionListRepository;

    @Transactional
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

    public List<IngestionList> findByEmail(UserDto userDto) throws CustomException {
        if(userDto == null){
            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND);
        }
        String email = userDto.getEmail();
        if(email == null){
            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
        }
        return ingestionListRepository.findByEmail(email);
    }

    public List<IngestionList> findByDate(IngestionListDto ingestionListDto) throws CustomException {
        if(ingestionListDto == null){
            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND);
        }
        Date date = ingestionListDto.getDate();
        if(date == null){
            throw new CustomException(ErrorCode.DATE_NOT_FOUND);
        }
        return ingestionListRepository.findByDate(date);
    }
}
