package com.example.demo.security.util;

import com.example.demo.security.services.impl.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private SecurityUtil() {
    }

    public static UserDetailsImpl getCurrentUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }

    public static Long getCurrentUserId(){
         return getCurrentUser().getUserId();
    }

    public static String getCurrentUserEmail(){
        return getCurrentUser().getEmail();
    }
}
