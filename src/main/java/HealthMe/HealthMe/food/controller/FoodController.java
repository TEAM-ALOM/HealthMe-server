package HealthMe.HealthMe.food.controller;


import HealthMe.HealthMe.common.dto.PagingDto;
import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.food.dto.FoodListDto;
import HealthMe.HealthMe.food.service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/food")
@Tag(name="음식 관련 컨트롤러")
public class FoodController {
    public final FoodService foodService;
    @GetMapping
    @Operation(summary = "모든 음식 찾기", description = "해당 url로 요청시 저장 된 모든 음식 리스트를 로드")
    public ResponseEntity findAll(){
        return new ResponseEntity(foodService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/find-by-name")
    @Operation(summary = "음식 이름으로 찾기", description = "해당 url로 요청시 저장 된 모든 음식 중 음식 이름으로 로드")
    public ResponseEntity findByName(@RequestBody FoodListDto foodListDto) throws CustomException {
        return new ResponseEntity(foodService.findByName(foodListDto), HttpStatus.OK);
    }

    @PostMapping("/paging")
    @Operation(summary = "음식 50개씩 로드", description = "해당 url로 요청시 저장 된 모든 음식 중 idx로 50개씩 로드")
    public ResponseEntity findAllByPaging(@RequestBody PagingDto pagingDto){
        return new ResponseEntity(foodService.findAllUsingPage(pagingDto), HttpStatus.OK);
    }
}
