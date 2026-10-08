package com.example.demo.security.services.impl;

import com.example.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private final Long userId;
    private final String email;

    @JsonIgnore
    private final String password;
    private final Collection<? extends GrantedAuthority>authorities;

    public static UserDetailsImpl build(User user){
        List<SimpleGrantedAuthority> authorities=user.getRoles().stream()
                .map(role->new SimpleGrantedAuthority("ROLE_"+role.name()))
                .toList();

        return new UserDetailsImpl(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
               authorities
        );
    }

    @Override
    public String getUsername() {
        return email;
    }
}
