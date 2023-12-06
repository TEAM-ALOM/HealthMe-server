package HealthMe.HealthMe.domain.user.controller;


import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.domain.user.dto.LoginDto;
import HealthMe.HealthMe.domain.user.dto.UserDto;
import HealthMe.HealthMe.domain.user.dto.UserSignUpDto;
import HealthMe.HealthMe.domain.user.service.LoginService;
import HealthMe.HealthMe.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/user")
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity logIn(@RequestBody LoginDto user, HttpServletRequest httpServletRequest) throws CustomException {
        LoginDto loginDto = loginService.signIn(user);
        // 로그인 성공 -> 신규 세션 생성

        // 세션을 생성하기 전에 기존의 세션 파기
        httpServletRequest.getSession().invalidate();
        HttpSession session = httpServletRequest.getSession(true);// Session이 없으면 생성

        // 세션에 userId를 넣어줌
        session.setAttribute("userEmail", loginDto.getEmail());
        session.setMaxInactiveInterval(30 * 60); // Session이 30 * 60초동안 유지
        // 다른데에다 세션 붙여서 검증
        return new ResponseEntity(loginDto, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity logout(HttpServletRequest httpServletRequest){
        httpServletRequest.getSession().invalidate();
        return new ResponseEntity("redirect:/", HttpStatus.OK);
    }
}
