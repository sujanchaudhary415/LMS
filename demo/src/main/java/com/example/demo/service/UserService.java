package com.example.demo.service;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.InstructorResponse;
import com.example.demo.dto.response.StudentResponse;
import com.example.demo.wrapper.PagedResponse;

public interface UserService {
    StudentResponse getStudentById(Long userId );
    InstructorResponse getInstructorById(Long userId);
    PagedResponse<StudentResponse> getAllStudents(int pageNumber,int pageSize,String sortBy,String sortDir);
    PagedResponse<InstructorResponse>getAllInstructor(int pageNumber,int pageSize,String sortBy,String sortDir);
    void deleteUserById(Long userId);
    StudentResponse updateStudentById(Long userId, UserUpdateRequest request);
    InstructorResponse updateInstructorById(Long userId,UserUpdateRequest request);

}
