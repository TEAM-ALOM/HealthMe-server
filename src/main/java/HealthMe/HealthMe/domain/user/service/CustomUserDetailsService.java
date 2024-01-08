package HealthMe.HealthMe.domain.user.service;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.exception.ErrorCode;
import HealthMe.HealthMe.domain.user.domain.User;
import HealthMe.HealthMe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            return userRepository.findByEmail(username)
                    .map(this::createUserDetails)
                    .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));
        } catch (CustomException e) {
            throw new RuntimeException(e);
        }
    }

    private UserDetails createUserDetails(User user){
        log.info("user password = {}", user.getPassword());
        return User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(List.of(user.getRoles().toArray(new String[0])))
                .build();
    }
}
