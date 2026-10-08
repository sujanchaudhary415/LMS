package com.example.demo.service.impl;

import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.InstructorResponse;
import com.example.demo.dto.response.StudentResponse;
import com.example.demo.entity.User;
import com.example.demo.enums.AppRole;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.util.PageableUtil;
import com.example.demo.util.PaginationUtil;
import com.example.demo.wrapper.PagedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;


    @Override
    public StudentResponse getStudentById(Long userId) {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId",userId));

        if(user.getRoles().contains(AppRole.STUDENT)){
            throw new ResourceNotFoundException("Student","userId",userId);
        }
        return userMapper.toStudentResponse(user);
    }

    @Override
    public InstructorResponse getInstructorById(Long userId) {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId",userId));

        if(user.getRoles().contains(AppRole.INSTRUCTOR)){
            throw new ResourceNotFoundException("Instructor","userId",userId);
        }
        return userMapper.toInstructorResponse(user);
    }

    @Override
    public PagedResponse<StudentResponse> getAllStudents(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Pageable pageDetails= PageableUtil.create(pageNumber,pageSize,sortBy,sortDir);
        Page<User>studentPage =userRepository.findByRolesContaining(AppRole.STUDENT,pageDetails);
        List<StudentResponse>content=studentPage.getContent()
                .stream()
                .map(userMapper::toStudentResponse)
                .toList();
        return PaginationUtil.build(studentPage,content);
    }

    @Override
    public PagedResponse<InstructorResponse> getAllInstructor(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Pageable pageDetails=PageableUtil.create(pageNumber,pageSize,sortBy,sortDir);
        Page<User>instructorPage =userRepository.findByRolesContaining(AppRole.INSTRUCTOR,pageDetails);
        List<InstructorResponse>content =instructorPage.getContent()
                .stream()
                .map(userMapper::toInstructorResponse)
                .toList();
        return PaginationUtil.build(instructorPage,content);
    }

    @Override
    public void deleteUserById(Long userId) {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId",userId));
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public StudentResponse updateStudentById(Long userId, UserUpdateRequest request) {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId",userId));

        if(user.getRoles().contains(AppRole.STUDENT)){
            throw new ResourceNotFoundException("Student","userId",userId);
        }

        if(request.getEmail()!=null && userRepository.existsByEmailIgnoreCase(request.getEmail()) && !user.getEmail().equalsIgnoreCase(request.getEmail()) ){
            throw new DuplicateResourceException("Email already exists");
        }

        userMapper.updateUserFromRequest(request,user);
        return userMapper.toStudentResponse(user);
    }

    @Override
    public InstructorResponse updateInstructorById(Long userId, UserUpdateRequest request) {
        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","userId",userId));

        if(user.getRoles().contains(AppRole.INSTRUCTOR)){
            throw new ResourceNotFoundException("Instructor","userId",userId);
        }

        if(request.getEmail()!=null && userRepository.existsByEmailIgnoreCase(request.getEmail()) && !user.getEmail().equalsIgnoreCase(request.getEmail()) ){
            throw new DuplicateResourceException("Email already exists");
        }

        userMapper.updateUserFromRequest(request,user);
        return userMapper.toInstructorResponse(user);
    }


    private String normalizedName(String name){
        return name.trim().replaceAll("\\s+"," ");
    }
}
