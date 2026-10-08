package com.example.demo.security.response;

import com.example.demo.enums.AppRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private Set<AppRole> roles;
}
