package HealthMe.HealthMe.food.controller;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.food.dto.IngestionListDto;
import HealthMe.HealthMe.food.service.IngestionService;
import HealthMe.HealthMe.user.dto.UserDto;
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
@Tag(name = "섭취한 음식 관련 컨트롤러")
public class IngestionController {
    private final IngestionService ingestionService;

    @PostMapping("ingestion/save")
    @Operation(summary = "섭취한 음식 저장", description = "해당 url로 요청시 섭취한 음식을 저장")
    public ResponseEntity save(@RequestBody IngestionListDto ingestionListDto) throws CustomException {
        ingestionService.saveIngestion(ingestionListDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("ingestion/by-email")
    @Operation(summary = "섭취한 음식 email로 찾기", description = "해당 url로 요청시 섭취한 음식을 email로 로드")
    public ResponseEntity findByEmail(@RequestBody UserDto userDto) throws CustomException {
        return new ResponseEntity(ingestionService.findByEmail(userDto), HttpStatus.OK);
    }

    @PostMapping("ingestion/by-date")
    @Operation(summary = "섭취한 음식 날짜로 찾기", description = "해당 url로 요청시 섭취한 음식을 날짜로 로드")
    public ResponseEntity findByDate(@RequestBody IngestionListDto ingestionListDto) throws CustomException {
        return new ResponseEntity(ingestionService.findByDate(ingestionListDto), HttpStatus.OK);
    }
}
