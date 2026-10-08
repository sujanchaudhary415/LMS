package com.example.demo.dto.response;

import com.example.demo.enums.AppRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InstructorResponse {
     private Long userId;
     private String firstName;
     private String lastName;
     private String email;
     private AppRole role;
     private List<CourseResponse> courses;
}
