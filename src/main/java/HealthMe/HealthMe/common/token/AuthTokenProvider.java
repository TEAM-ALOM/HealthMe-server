package HealthMe.HealthMe.common.token;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AuthTokenProvider {
    private final Key key;

    public AuthTokenProvider(@Value("${spring.jwt.secret}") String secretKey){
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }
    public AuthToken generateToken(Authentication authentication) throws CustomException {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", "USER")
                .setExpiration(new Date(System.currentTimeMillis() + (long) 1000*60*3))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setExpiration(new Date(System.currentTimeMillis() + (long) 1000*60*60*24*15))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return AuthToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public Authentication getAuthentication(String accessToken) throws CustomException {
        Claims claims = parseClaims(accessToken);

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject() , "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String token) throws CustomException {
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }
        catch (SecurityException e) {
            log.info("Invalid JWT Signature");
            throw new CustomException(ErrorCode.INVALID_JWT_TOKEN);
        } catch (MalformedJwtException e) {
            log.info("Invalid JWT token.");
            throw new CustomException(ErrorCode.INVALID_JWT_TOKEN);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            throw new CustomException(ErrorCode.JWT_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            throw new CustomException(ErrorCode.INVALID_JWT_TOKEN);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            throw new CustomException(ErrorCode.INVALID_JWT_TOKEN);
        }
    }
    private Claims parseClaims(String accessToken){
        try{
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        }
        catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }

    public Key getKey() {
        return key;
    }
}
