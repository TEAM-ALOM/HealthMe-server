package HealthMe.HealthMe.domain.food.controller;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.domain.food.dto.IngestionListDto;
import HealthMe.HealthMe.domain.food.service.IngestionService;
import HealthMe.HealthMe.domain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/food")
public class IngestionController {
    private final IngestionService ingestionService;

    @PostMapping("ingestion/save")
    public ResponseEntity save(@RequestBody IngestionListDto ingestionListDto) throws CustomException {
        ingestionService.saveIngestion(ingestionListDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("ingestion/by-email")
    public ResponseEntity findByEmail(@RequestBody UserDto userDto) throws CustomException {
        return new ResponseEntity(ingestionService.findByEmail(userDto), HttpStatus.OK);
    }

    @GetMapping("ingestion/by-date")
    public ResponseEntity findByDate(@RequestBody IngestionListDto ingestionListDto) throws CustomException {
        return new ResponseEntity(ingestionService.findByDate(ingestionListDto), HttpStatus.OK);
    }
}
