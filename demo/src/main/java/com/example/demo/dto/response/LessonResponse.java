package com.example.demo.dto.response;

import com.example.demo.entity.Course;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonResponse {
     private Long id;
     private String title;
     private String content;
     private Long courseId;
}
