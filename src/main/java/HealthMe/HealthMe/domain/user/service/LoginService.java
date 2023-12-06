package HealthMe.HealthMe.domain.user.service;


import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.exception.ErrorCode;
import HealthMe.HealthMe.domain.user.domain.User;
import HealthMe.HealthMe.domain.user.dto.LoginDto;
import HealthMe.HealthMe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public LoginDto signIn(LoginDto loginDto) throws CustomException {
        if(loginDto.getEmail()==null){
            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
        }
        User findUser = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));

        boolean result = findUser.checkPassword(loginDto.getPassword(), bCryptPasswordEncoder);

        if(result == true){
            return LoginDto.builder()
                    .name(findUser.getName())
                    .autoLogin(findUser.isAutoLogin())
                    .email(findUser.getEmail())
                    .build();
        }
        else{
            throw new CustomException(ErrorCode.ACCOUNT_NOT_FOUND);
        }

    }
}
