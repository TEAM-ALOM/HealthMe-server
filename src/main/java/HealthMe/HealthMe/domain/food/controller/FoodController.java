package HealthMe.HealthMe.domain.food.controller;


import HealthMe.HealthMe.domain.food.service.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/food")
public class FoodController {
    public final FoodService foodService;
    @GetMapping
    public ResponseEntity findAll(){
        return new ResponseEntity(foodService.findAll(), HttpStatus.OK);
    }
}
