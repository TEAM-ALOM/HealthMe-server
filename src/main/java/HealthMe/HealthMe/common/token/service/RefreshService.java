package HealthMe.HealthMe.common.token.service;


import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.exception.ErrorCode;
import HealthMe.HealthMe.common.token.dto.AuthToken;
import HealthMe.HealthMe.common.token.AuthTokenProvider;
import HealthMe.HealthMe.common.token.dto.RefreshDto;
import HealthMe.HealthMe.user.domain.User;
import HealthMe.HealthMe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.Base64;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshService {
    private final UserRepository userRepository;

    private final AuthTokenProvider authTokenProvider;
    public AuthToken refresh(RefreshDto refreshDto) throws CustomException, ParseException {
        if(validation(refreshDto)) {
            String subject = getSubject(refreshDto);

            Authentication authentication = authTokenProvider.getAuthentication(refreshDto.getAccessToken());

            AuthToken newAuthToken = authTokenProvider.generateToken(authentication);
            User findUser = userRepository.findByEmail(subject)
                    .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));

            findUser.updateRefreshToken(newAuthToken.getRefreshToken());
            userRepository.save(findUser);
            return newAuthToken;
        }
        return null;
    }
    private boolean validation(RefreshDto refreshDto) throws CustomException, ParseException {
        String refreshToken = refreshDto.getRefreshToken();
        String email = refreshDto.getEmail();
        if(refreshToken == null){
            throw new CustomException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        if(email == null){
            throw new CustomException(ErrorCode.EMAIL_NOT_FOUND);
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));

        String userRefreshToken = user.getRefreshToken();
        if(!userRefreshToken.equals(refreshToken)){
            throw new CustomException(ErrorCode.INCORRECT_REFRESH_TOKEN);
        }

        long now = System.currentTimeMillis();
        long expirationTime = getExpireTime(refreshToken);

        if(expirationTime - now <= 0){
            throw new CustomException(ErrorCode.JWT_EXPIRED);
        }
        return true;
    }
    private String decode(String refreshToken){
        Base64.Decoder decoder = Base64.getDecoder();
        String[] splitJwt = refreshToken.split("\\.");

        String payloadStr = new String(decoder.decode(splitJwt[1].getBytes()));

        return payloadStr;
    }

    private long getExpireTime(String refreshToken) throws ParseException {
        String payload = this.decode(refreshToken);
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(payload);
        long expirationTime = (long) object.get("exp") * 1000;

        return expirationTime;
    }
    private String getSubject(RefreshDto refreshDto) throws ParseException {
        String refreshToken = refreshDto.getRefreshToken();
        String payload = this.decode(refreshToken);
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(payload);
        String subject = object.get("sub").toString();
        return subject;
    }
}
