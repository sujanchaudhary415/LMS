package com.example.demo.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CourseUpdateRequest {
    @Size(max = 100,message = "Title must not exceed 100 characters")
    private String title;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;
}
