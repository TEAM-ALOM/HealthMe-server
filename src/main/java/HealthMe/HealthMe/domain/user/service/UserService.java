package HealthMe.HealthMe.domain.user.service;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.exception.ErrorCode;
import HealthMe.HealthMe.domain.user.domain.User;
import HealthMe.HealthMe.domain.user.dto.UserBodyInformationDto;
import HealthMe.HealthMe.domain.user.dto.UserDto;
import HealthMe.HealthMe.domain.user.dto.UserPasswordChangeDto;
import HealthMe.HealthMe.domain.user.dto.UserSignUpDto;
import HealthMe.HealthMe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



// 11/27 수정 -> entity 리턴에서 dto리턴으로
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    // 11/26 추가 : 회원가입 기능 : front랑 상의해서 가입 버튼 막을지 말지 결정
    public UserDto signUp(UserSignUpDto userSignUpDto) throws CustomException{
        if(userRepository.findByEmail(userSignUpDto.getEmail())!=null){
            throw new CustomException(ErrorCode.EMAIL_EXSIST);
        }

        User newUser = userSignUpDto.toEntity();
        newUser.hashPassword(bCryptPasswordEncoder);
        User save = userRepository.save(newUser);
        UserDto savedDto = UserDto.builder()
                .name(save.getName())
                .email(save.getEmail())
                .autoLogin(save.isAutoLogin())
                .build();
        return savedDto;
    }
    public UserBodyInformationDto enterBodyInformation(UserBodyInformationDto userInformationDto) throws CustomException{
        if(userInformationDto==null){
            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND);
        }
        // 더티채킹
        User user = userRepository.findByEmail(userInformationDto.getEmail());
        user.setUserBodyInformation(userInformationDto.getName(),
                userInformationDto.getBirthday(),
                userInformationDto.getHeight(),
                userInformationDto.getWeight(),
                userInformationDto.getGender());

        return userInformationDto;
    }


    public UserBodyInformationDto getUserBodyInformation(UserDto userDto) throws CustomException {
        if(userDto.getEmail() == null){
            throw new CustomException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        User user = userRepository.findByEmail(userDto.getEmail());
        return UserBodyInformationDto.builder()
                .height(user.getHeight())
                .weight(user.getWeight())
                .birthday(user.getBirthday())
                .gender(user.getGender())
                .build();
    }


    // 환경 설정 -> 비밀번호 변경 시 사용할 password check
    public UserDto changePassword(UserPasswordChangeDto userPasswordChangeDto) throws CustomException{
        User findUser = userRepository.findByEmail(userPasswordChangeDto.getEmail());
        if(findUser == null){
            throw new CustomException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        findUser.updatePassword(userPasswordChangeDto.getChangedPassword());
        findUser.hashPassword(bCryptPasswordEncoder);

        return UserDto.builder()
                .name(findUser.getName())
                .email(findUser.getPassword())
                .build();
    }
    public boolean checkPassword(UserPasswordChangeDto userPasswordChangeDto) throws CustomException{
        if(userPasswordChangeDto.getEmail() == null){
            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
        }
        User findUser = userRepository.findByEmail(userPasswordChangeDto.getEmail());
        if(findUser==null){
            throw new CustomException(ErrorCode.ACCOUNT_NOT_FOUND);
        }
        if(!findUser.checkPassword(userPasswordChangeDto.getPassword(), bCryptPasswordEncoder)){
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }
        return true;
    }

}