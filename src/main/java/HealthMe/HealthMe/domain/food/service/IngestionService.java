package HealthMe.HealthMe.domain.food.service;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.exception.ErrorCode;
import HealthMe.HealthMe.domain.food.domain.FoodList;
import HealthMe.HealthMe.domain.food.domain.IngestionList;
import HealthMe.HealthMe.domain.food.dto.IngestionListDto;
import HealthMe.HealthMe.domain.food.repository.FoodListRepository;
import HealthMe.HealthMe.domain.food.repository.IngestionListRepository;
import HealthMe.HealthMe.domain.user.domain.User;
import HealthMe.HealthMe.domain.user.dto.UserDto;
import HealthMe.HealthMe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngestionService {

    private final FoodListRepository foodListRepository;
    private final IngestionListRepository ingestionListRepository;
    private final UserRepository userRepository;
    @Transactional
    public IngestionListDto saveIngestion(IngestionListDto ingestionListDto) throws CustomException {
        if(ingestionListDto == null){
            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND);
        }

        String email = ingestionListDto.getUserEmail();
        if(email == null){
            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));

        String foodName = ingestionListDto.getFoodName();
        if(foodName == null){
            throw new CustomException(ErrorCode.FOOD_NAME_NOT_FOUND);
        }
        FoodList foodList = foodListRepository.findByName(foodName)
                .orElseThrow(() -> new CustomException(ErrorCode.FOOD_NOT_FOUND));

        if(ingestionListDto.getDate() == null){
            throw new CustomException(ErrorCode.DATE_NOT_FOUND);
        }
        if(ingestionListDto.getMass() == null){
            throw new CustomException(ErrorCode.FOOD_MASS_NOT_FOUND);
        }

        ingestionListRepository.save(ingestionListDto.toEntity(user, foodList));

        return ingestionListDto;
    }

    public List<IngestionListDto> findByEmail(UserDto userDto) throws CustomException {
        if(userDto == null){
            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND);
        }
        String email = userDto.getEmail();
        if(email == null){
            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
        }

        List<IngestionList> byEmail = ingestionListRepository.findByEmail(email);
        List<IngestionListDto> result = new ArrayList<>();
        for (IngestionList ingestionList : byEmail) {
            IngestionListDto find = IngestionListDto.builder()
                    .foodName(ingestionList.getFoodList().getName())
                    .userEmail(userDto.getEmail())
                    .mass(ingestionList.getMass())
                    .date(ingestionList.getDate())
                    .build();

            result.add(find);
        }

        return result;
    }

    public List<IngestionListDto> findByDate(IngestionListDto ingestionListDto) throws CustomException {
        if(ingestionListDto == null){
            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND);
        }

        Date date = ingestionListDto.getDate();
        if(date == null){
            throw new CustomException(ErrorCode.DATE_NOT_FOUND);
        }

        List<IngestionList> byDate = ingestionListRepository.findByDate(date);
        List<IngestionListDto> result = new ArrayList<>();

        for (IngestionList ingestionList : byDate) {
            IngestionListDto find = IngestionListDto.builder()
                    .date(ingestionList.getDate())
                    .mass(ingestionList.getMass())
                    .userEmail(ingestionList.getUser().getEmail())
                    .foodName(ingestionList.getFoodList().getName())
                    .build();

            result.add(find);

        }
        return result;
    }
}
