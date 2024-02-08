package HealthMe.HealthMe.user.service;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.exception.ErrorCode;
import HealthMe.HealthMe.common.token.dto.AuthToken;
import HealthMe.HealthMe.common.token.AuthTokenProvider;
import HealthMe.HealthMe.user.domain.User;
import HealthMe.HealthMe.user.dto.LoginDto;
import HealthMe.HealthMe.user.dto.UserDto;
import HealthMe.HealthMe.user.dto.UserSignUpDto;
import HealthMe.HealthMe.user.repository.UserRepository;
import HealthMe.HealthMe.user.dto.UserPasswordChangeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder bCryptPasswordEncoder;
    private final AuthTokenProvider authTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public UserDto signUp(UserSignUpDto userSignUpDto) throws CustomException{
        if(userSignUpDto == null){
            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND);
        }
        if(userSignUpDto.getEmail() == null){
            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
        }
        if(userSignUpDto.getPassword() ==null){
            throw new CustomException(ErrorCode.PASSWORD_NOT_FOUND);
        }

        if(userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()){
            throw new CustomException(ErrorCode.EMAIL_EXSIST);
        }

        User newUser = userSignUpDto.toEntity();
        newUser.hashPassword(bCryptPasswordEncoder);
        userRepository.save(newUser);

        UserDto savedDto = UserDto.builder()
                .name(newUser.getName())
                .email(newUser.getEmail())
                .weight(newUser.getWeight())
                .height(newUser.getHeight())
                .gender(newUser.getGender())
                .birthday(newUser.getBirthday())
                .build();

        return savedDto;
    }
    @Transactional
    public UserDto setBodyInformation(UserDto userInformationDto) throws CustomException{
        if(userInformationDto == null){
            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND);
        }
        String email = userInformationDto.getEmail();
        if(email == null){
            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
        }

        User user = userRepository.findByEmail(userInformationDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));


        user.setUserBodyInformation(userInformationDto.getName(),
                userInformationDto.getBirthday(),
                userInformationDto.getHeight(),
                userInformationDto.getWeight(),
                userInformationDto.getGender());

        return userInformationDto;
    }


    public UserDto getBodyInformation(UserDto userDto) throws CustomException {
        if(userDto == null){
            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND);
        }
        if(userDto.getEmail() == null){
            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
        }

        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));

        double bmi = user.getWeight()/((user.getHeight()/100)*(user.getHeight()/100))*10;

        return UserDto.builder()
                .name(user.getName())
                .height(user.getHeight())
                .weight(user.getWeight())
                .birthday(user.getBirthday())
                .gender(user.getGender())
                .bmi(bmi)
                .build();
    }


    @Transactional
    public UserDto changeForgetPassword(UserPasswordChangeDto userPasswordChangeDto) throws CustomException{
        if(userPasswordChangeDto == null){
            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND);
        }
        String email = userPasswordChangeDto.getEmail();
        String changedPassword = userPasswordChangeDto.getChangedPassword();
        if(email==null){
            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
        }
        if(changedPassword == null){
            throw new CustomException(ErrorCode.PASSWORD_NOT_FOUND);
        }
        User findUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));

        findUser.updatePassword(changedPassword);
        findUser.hashPassword(bCryptPasswordEncoder);

        return UserDto.builder()
                .name(findUser.getName())
                .email(findUser.getPassword())
                .build();
    }


    private void updateRefreshToken(String email, String refreshToken) throws CustomException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));
        user.updateRefreshToken(refreshToken);
        userRepository.save(user);
    }

    public AuthToken signIn(LoginDto loginDto) throws CustomException {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        AuthToken authToken = authTokenProvider.generateToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        this.updateRefreshToken(loginDto.getEmail(), authToken.getRefreshToken());
        return authToken;
    }

    public LoginDto logout(LoginDto loginDto) throws CustomException {
        String email = loginDto.getEmail();
        if(email == null){
            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));

        user.updateRefreshToken(null);
        userRepository.save(user);
        return loginDto;
    }

    @Transactional
    public UserDto changePassword(UserPasswordChangeDto userPasswordChangeDto) throws CustomException {
        if(userPasswordChangeDto == null){
            throw new CustomException(ErrorCode.OBJECT_NOT_FOUND);
        }
        String email = userPasswordChangeDto.getEmail();
        String password = userPasswordChangeDto.getPassword();
        String changedPassword = userPasswordChangeDto.getChangedPassword();
        if(email==null){
            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
        }

        if(password == null || changedPassword == null){
            throw new CustomException(ErrorCode.PASSWORD_NOT_FOUND);
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));

        if(user.checkPassword(password, bCryptPasswordEncoder)){
            user.updatePassword(changedPassword);
            user.hashPassword(bCryptPasswordEncoder);

        }
        else{
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }

        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}