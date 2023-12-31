package HealthMe.HealthMe.common.configuration;

import HealthMe.HealthMe.common.token.AuthTokenProvider;
import HealthMe.HealthMe.common.token.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig{
    private final AuthTokenProvider authTokenProvider;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/user/signup").permitAll() // 로그인 api
                .requestMatchers("/api/user/login").permitAll() // 회원가입 api
                .requestMatchers("/api/refresh").permitAll() // 토큰 리프레쉬 api

                .requestMatchers("/api/email/verification-request").permitAll() // 이메일 요청
                .requestMatchers("/api/email/verification").permitAll() // 이메일 확인
                .requestMatchers("/v3/api-docs/**").permitAll() // 스웨거
                .requestMatchers("/swagger-ui/**").permitAll() // 스웨거
                .requestMatchers("/api/user/save-body-information").permitAll()

                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JwtFilter(authTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
