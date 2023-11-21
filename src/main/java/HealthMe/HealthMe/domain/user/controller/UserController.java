package HealthMe.HealthMe.domain.user.controller;

import HealthMe.HealthMe.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping()
public class UserController {
    private final UserService userService;


}
