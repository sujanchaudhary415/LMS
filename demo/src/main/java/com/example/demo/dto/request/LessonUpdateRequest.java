package com.example.demo.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LessonUpdateRequest {
    @Size(min = 4,max = 50,message = "Title should be between 4 to 50 characters")
    private String title;

    private String content;
}
