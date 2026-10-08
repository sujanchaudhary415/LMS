package com.example.demo.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequest {
    @Size(max=50,message = "First name must not exceed 50 characters")
    private String firstName;

    @Size(max = 50,message = "Last name must not exceed 50 characters")
    private String lastName;

    @Size(max=150,message = "Email not exceed 150 characters")
    @Email(message = "Invalid email")
    private String email;

}
