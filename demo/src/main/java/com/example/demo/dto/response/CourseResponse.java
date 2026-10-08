package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CourseResponse {
   private Long id;
   private String title;
   private String description;
   private Long instructorId;
   private List<LessonResponse> lessons=new ArrayList<>();
}
