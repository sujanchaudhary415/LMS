package com.example.demo.security.request;

import com.example.demo.enums.AppRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignupRequest {
    @NotBlank(message = "First name is required")
    @Size(max=50,message = "First name must not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50,message = "Last name must not exceed 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    @Size(max=150,message = "Email not exceed 150 characters")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8,max=255,message = "Password must be between 8 and 255 characters")
    private String password;

    @NotNull
    private Set<AppRole> roles;
}
