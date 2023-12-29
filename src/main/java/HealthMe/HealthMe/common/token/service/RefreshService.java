package HealthMe.HealthMe.common.token.service;


import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.exception.ErrorCode;
import HealthMe.HealthMe.common.token.AuthToken;
import HealthMe.HealthMe.common.token.AuthTokenProvider;
import HealthMe.HealthMe.domain.user.domain.User;
import HealthMe.HealthMe.domain.user.repository.UserRepository;
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
    public AuthToken refresh(AuthToken authToken) throws CustomException, ParseException {
        String refreshToken = authToken.getRefreshToken();
        this.validation(refreshToken);

        String payload = this.decode(refreshToken);
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(payload);
        String subject = object.get("sub").toString();

        Authentication authentication = authTokenProvider.getAuthentication(authToken.getAccessToken());
        AuthToken newAuthToken = authTokenProvider.generateToken(authentication);
        User findUser = userRepository.findByEmail(subject)
                .orElseThrow(()-> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));
        findUser.updateRefreshToken(newAuthToken.getRefreshToken());
        userRepository.save(findUser);

        return newAuthToken;
    }
    private boolean validation(String refreshToken) throws CustomException, ParseException {
        if(refreshToken.isEmpty()){
            throw new CustomException(ErrorCode.INVALID_JWT_TOKEN);
        }

        long now = System.currentTimeMillis();
        String payload = this.decode(refreshToken);
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(payload);
        long expirationTime = (long) object.get("exp") * 1000;


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

}
