package com.example.demo.security.cookie;
import com.example.demo.security.jwt.JwtProperties;
import com.example.demo.security.jwt.JwtService;
import com.example.demo.security.services.impl.UserDetailsImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class CookieService {

    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    public ResponseCookie generateAccessTokenCookie(UserDetailsImpl userPrincipal){
        String jwt=jwtService.generateAccessToken(userPrincipal);
        return ResponseCookie.from(jwtProperties.getAccessCookie(),jwt)
                .path("/api")
                .httpOnly(true)
                .sameSite("lax")
                .maxAge(Duration.ofMillis(jwtProperties.getAccessTokenExpiration()))
                .secure(false)
                .build();
    }

    public String getAccessTokenFromCookie(HttpServletRequest request){
        Cookie cookie= WebUtils.getCookie(request,jwtProperties.getAccessCookie());
        return cookie !=null ? cookie.getValue() : null;
    }

    public ResponseCookie cleanAccessTokenCookie(){
        return ResponseCookie.from(jwtProperties.getAccessCookie(),"")
                .path("/api")
                .httpOnly(true)
                .sameSite("Lax")
                .maxAge(0)
                .secure(false)
                .build();
    }

    //RefreshToken
    public ResponseCookie generateRefreshTokenCookie(String refreshToken){
        return ResponseCookie.from(jwtProperties.getRefreshCookie(),refreshToken)
                .path("/api/auth/refresh")
                .httpOnly(true)
                .sameSite("Lax")
                .maxAge(jwtProperties.getRefreshTokenExpiration()/1000)
                .secure(false)
                .build();
    }

    public String getRefreshTokenFromCookie(HttpServletRequest request){
        Cookie cookie=WebUtils.getCookie(request,jwtProperties.getRefreshCookie());
        return cookie !=null ? cookie.getValue() : null;
    }

    public ResponseCookie cleanRefreshTokenCookie(){
        return ResponseCookie.from(jwtProperties.getRefreshCookie(),"")
                .path("/api/auth/refresh")
                .httpOnly(true)
                .sameSite("Lax")
                .maxAge(0)
                .secure(false)
                .build();
    }

}
