package com.example.demo.controller;

import com.example.demo.constant.AppConstant;
import com.example.demo.dto.request.CourseRequest;
import com.example.demo.dto.request.CourseUpdateRequest;
import com.example.demo.dto.response.CourseResponse;
import com.example.demo.service.CourseService;
import com.example.demo.wrapper.ApiResponse;
import com.example.demo.wrapper.PagedResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Tag(name="Courses",description = "Course management APIs")
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/instructors/courses")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<ApiResponse<CourseResponse>>createCourse( @RequestBody @Valid CourseRequest request){
       CourseResponse response =courseService.createCourse(request);
       return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success("Course created successfully",response));
    }

    @GetMapping("/courses/{courseId}")
    public ResponseEntity<ApiResponse<CourseResponse>>getCourseById(@PathVariable @Positive Long courseId){
        CourseResponse response=courseService.getCourseById(courseId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Course retrieved successfully",response));
    }

    @GetMapping("/courses/instructor/{instructorId}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<PagedResponse<CourseResponse>>>getCoursesByInstructors( @PathVariable @Positive Long instructorId,@RequestParam(name = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER)int pageNumber, @RequestParam(name = "pageSize",defaultValue = AppConstant.PAGE_SIZE)int pageSize, @RequestParam(name = "sortBy",defaultValue = AppConstant.SORT_BY_COURSE)String sortBy, @RequestParam(name = "sortDir",defaultValue = AppConstant.SORT_DIR)String sortDir){

      PagedResponse<CourseResponse>response=courseService.getCoursesByInstructors(instructorId,pageNumber, pageSize, sortBy, sortDir);
      return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("All courses of instructor fetched successfully",response));
    }

    @GetMapping("/courses")
    public ResponseEntity<ApiResponse<PagedResponse<CourseResponse>>>getAllCourses(@RequestParam(name = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER)int pageNumber, @RequestParam(name = "pageSize",defaultValue = AppConstant.PAGE_SIZE)int pageSize, @RequestParam(name = "sortBy",defaultValue = AppConstant.SORT_BY_COURSE)String sortBy, @RequestParam(name = "sortDir",defaultValue = AppConstant.SORT_DIR)String sortDir){
        PagedResponse<CourseResponse>response=courseService.getAllCourses( pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("All courses fetched successfully",response));
    }

    @PatchMapping("/courses/{courseId}/instructor/{instructorId}")
    public ResponseEntity<ApiResponse<CourseResponse>>updateCourseById(@PathVariable Long courseId, @PathVariable Long instructorId,@RequestBody @Valid CourseUpdateRequest request){
        CourseResponse response  =courseService.updateCourseById(courseId,instructorId,request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Course updated successfully",response));
    }

    @DeleteMapping("/courses/{courseId}/instructor/{instructorId}")
    public ResponseEntity<ApiResponse<Void>>deleteCourseById(@PathVariable Long courseId,@PathVariable Long instructorId){
        courseService.deleteCourseById(courseId,instructorId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Course deleted successfully"));
    }
}
