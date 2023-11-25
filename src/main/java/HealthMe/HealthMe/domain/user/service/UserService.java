package HealthMe.HealthMe.domain.user.service;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.exception.ErrorCode;
import HealthMe.HealthMe.domain.user.domain.User;
import HealthMe.HealthMe.domain.user.dto.UserDto;
import HealthMe.HealthMe.domain.user.dto.UserSignUpDto;
import HealthMe.HealthMe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final EmailCreateService emailCreateService;


    // 11/26 추가 : 회원가입 기능 : front랑 상의해서 가입 버튼 막을지 말지 결정
    public User signUp(UserSignUpDto userSignUpDto) throws CustomException{
        if(userRepository.findByEmail(userSignUpDto.getEmail())!=null){
            throw new CustomException(ErrorCode.EMAIL_EXSIST);
        }

        User newUser = userSignUpDto.toEntity();
        newUser.hashPassword(bCryptPasswordEncoder);
        return userRepository.save(newUser);
    }

    // 11/26 추가 : 로그인 기능 : session 방식 적용 해야됨
    public User signIn(UserSignUpDto userSignUpDto) throws CustomException{
        if(userSignUpDto.getEmail()==null){
            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
        }
        User findUser = userRepository.findByEmail(userSignUpDto.getEmail());
        boolean result = findUser.checkPassword(userSignUpDto.getPassword(), bCryptPasswordEncoder);
        if(result == true){
            return findUser;
        }
        else{
            throw new CustomException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
    }

}