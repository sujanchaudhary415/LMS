package com.example.demo.controller;

import com.example.demo.constant.AppConstant;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.InstructorResponse;
import com.example.demo.dto.response.StudentResponse;
import com.example.demo.service.UserService;
import com.example.demo.wrapper.ApiResponse;
import com.example.demo.wrapper.PagedResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api")
@Tag(name="User",description = "User management APIs")
public class UserController {

    private final UserService userService;


    @GetMapping("/students/{userId}")
    public ResponseEntity<ApiResponse<StudentResponse>> getStudentById(@PathVariable @Positive Long userId){
        StudentResponse response=userService.getStudentById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Student fetched successfully",response));
    }

    @GetMapping("/instructors/{userId}")
    public ResponseEntity<ApiResponse<InstructorResponse>> getInstructorById(@PathVariable @Positive Long userId){
        InstructorResponse response= userService.getInstructorById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Instructor fetched successfully",response));
    }

    @GetMapping("/students")
    public ResponseEntity<ApiResponse<PagedResponse<StudentResponse>>> getAllStudents(@RequestParam(name = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER)int pageNumber,
      @RequestParam(name = "pageSize",defaultValue = AppConstant.PAGE_SIZE)int pageSize,
      @RequestParam(name = "sortBy",defaultValue = AppConstant.SORT_BY_USER)String sortBy,
      @RequestParam(name = "sortDir",defaultValue = AppConstant.SORT_DIR)String sortDir                                                                                ){
        PagedResponse<StudentResponse>response =userService.getAllStudents(pageNumber,pageSize,sortBy,sortDir);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("All students retrieved successfully",response));
    }

    @GetMapping("/instructors")
    public ResponseEntity<ApiResponse<PagedResponse<InstructorResponse>>> getAllInstructors(
            @RequestParam(name = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER)int pageNumber,
            @RequestParam(name = "pageSize",defaultValue = AppConstant.PAGE_SIZE)int pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstant.SORT_BY_USER)String sortBy,
            @RequestParam(name = "sortDir",defaultValue = AppConstant.SORT_DIR)String sortDir
    ){
        PagedResponse<InstructorResponse>response =userService.getAllInstructor(pageNumber,pageSize,sortBy,sortDir);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("All instructors retrieved successfully",response));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUserById(@PathVariable @Positive Long userId){
        userService.deleteUserById(userId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("User deleted successfully"));
    }

    @PatchMapping("/students/{userId}")
    public ResponseEntity<ApiResponse<StudentResponse>> updateStudentById(@PathVariable @Positive Long userId, @RequestBody @Valid UserUpdateRequest request){
        StudentResponse response  =userService.updateStudentById(userId,request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Student updated successfully",response));
    }

    @PatchMapping("/instructors/{userId}")
    public ResponseEntity<ApiResponse<InstructorResponse>> updateInstructorById(@PathVariable Long userId,@RequestBody @Valid UserUpdateRequest request){
        InstructorResponse response=userService.updateInstructorById(userId,request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Instructor updated successfully",response));
    }




}
