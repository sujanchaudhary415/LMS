package com.example.demo.dto.response;

import com.example.demo.enums.AppRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentResponse {
    private Long userId;
    private String lastName;
    private String firstName;
    private String email;
    private AppRole role;
}
