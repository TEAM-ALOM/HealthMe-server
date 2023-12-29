package HealthMe.HealthMe.common.token.controller;


import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.token.AuthToken;
import HealthMe.HealthMe.common.token.service.RefreshService;
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
public class RefreshController {
    private final RefreshService refreshService;

    @GetMapping
    public ResponseEntity refresh(@RequestBody AuthToken authToken) throws CustomException, ParseException {
        AuthToken refresh = refreshService.refresh(authToken);
        return new ResponseEntity(refresh, HttpStatus.OK);
    }
}
