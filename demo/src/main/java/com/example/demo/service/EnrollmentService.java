package com.example.demo.service;
import com.example.demo.dto.response.EnrollmentResponse;
import com.example.demo.wrapper.PagedResponse;

public interface EnrollmentService {
    EnrollmentResponse enrollStudent(Long courseId);
    EnrollmentResponse getEnrollmentById(Long enrollmentId);
    PagedResponse<EnrollmentResponse> getStudentEnrollments(Long studentId, int pageNumber, int pageSize, String sortBy, String sortDir);
    PagedResponse<EnrollmentResponse> getCourseEnrollments(Long courseId,int pageNumber,int pageSize, String sortBy,String sortDir);
    void unenrollStudent(Long studentId,Long enrollmentId);
}
