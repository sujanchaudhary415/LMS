package com.example.demo.service;
import com.example.demo.dto.request.CourseRequest;
import com.example.demo.dto.request.CourseUpdateRequest;
import com.example.demo.dto.response.CourseResponse;
import com.example.demo.wrapper.PagedResponse;

public interface CourseService {
    CourseResponse createCourse(CourseRequest request);
    CourseResponse getCourseById(Long courseId);
    PagedResponse<CourseResponse> getCoursesByInstructors(Long instructorId,int pageNumber, int pageSize, String sortBy, String sortDir);
    PagedResponse<CourseResponse> getAllCourses(int pageNumber,int pageSize,String sortBy,String sortDir);
    CourseResponse updateCourseById(Long courseId, Long instructorId, CourseUpdateRequest request);
    void deleteCourseById(Long courseId,Long instructorId);
}
