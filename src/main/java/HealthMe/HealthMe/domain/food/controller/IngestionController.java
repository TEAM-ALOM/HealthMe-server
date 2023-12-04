package HealthMe.HealthMe.domain.food.controller;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.domain.food.dto.IngestionListDto;
import HealthMe.HealthMe.domain.food.service.IngestionService;
import HealthMe.HealthMe.domain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/ingestion")
public class IngestionController {
    private final IngestionService ingestionService;

    @GetMapping("/findByEmail")
    public ResponseEntity findByEmail(@RequestBody UserDto userDto) throws CustomException {
        return new ResponseEntity(ingestionService.findByEmail(userDto), HttpStatus.OK);
    }

    @GetMapping("/findByDate")
    public ResponseEntity findByDate(@RequestBody IngestionListDto ingestionListDto) throws CustomException {
        return new ResponseEntity(ingestionService.findByDate(ingestionListDto), HttpStatus.OK);
    }
}
