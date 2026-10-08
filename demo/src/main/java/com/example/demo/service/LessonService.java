package com.example.demo.service;

import com.example.demo.dto.request.LessonRequest;
import com.example.demo.dto.request.LessonUpdateRequest;
import com.example.demo.dto.response.LessonResponse;
import com.example.demo.wrapper.PagedResponse;

public interface LessonService {
   LessonResponse createLesson(Long courseId, LessonRequest request);
   LessonResponse getLessonById(Long courseId,Long lessonId);
   PagedResponse<LessonResponse> getLessonsByCourse(Long courseId,int pageNumber,int pageSize,String sortBy,String sortDir);
   void deleteLessonById(Long lessonId,Long instructorId);
   LessonResponse UpdateLessonById(Long lessonId, Long instructorId, LessonUpdateRequest request);
}
