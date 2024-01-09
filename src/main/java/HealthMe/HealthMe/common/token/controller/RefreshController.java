package HealthMe.HealthMe.common.token.controller;


import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.token.dto.RefreshDto;
import HealthMe.HealthMe.common.token.service.RefreshService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/refresh")
@Tag(name = "리프레쉬 컨트롤러")
public class RefreshController {
    private final RefreshService refreshService;
    @Operation(summary = "access token 만료시 refresh token을 이용하여 재발급",
                description = "해당 url로 요청 시 access token 재발급")
    @PostMapping
    public ResponseEntity refresh(@RequestBody RefreshDto refreshDto) throws CustomException, ParseException {
        return new ResponseEntity(refreshService.refresh(refreshDto), HttpStatus.OK);
    }
}
