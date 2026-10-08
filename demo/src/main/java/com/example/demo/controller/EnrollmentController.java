package com.example.demo.controller;
import com.example.demo.constant.AppConstant;
import com.example.demo.dto.response.EnrollmentResponse;
import com.example.demo.service.EnrollmentService;
import com.example.demo.wrapper.ApiResponse;
import com.example.demo.wrapper.PagedResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/enrollments")
@Tag(name="Enrollment",description = "Enrollment management APIs")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("/students/courses/{courseId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ApiResponse<EnrollmentResponse>>enrollStudent(@PathVariable @Positive Long courseId){
           EnrollmentResponse response =enrollmentService.enrollStudent(courseId);
           return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Student enrolled successfully",response));
    }

    @GetMapping("/{enrollmentId}")
    public ResponseEntity<ApiResponse<EnrollmentResponse>>getEnrollmentById(@PathVariable @Positive Long enrollmentId){
           EnrollmentResponse response=enrollmentService.getEnrollmentById(enrollmentId);
           return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Enrollment fetched successfully",response));
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<ApiResponse<PagedResponse<EnrollmentResponse>>>getStudentEnrollments(@PathVariable @Positive Long studentId, @RequestParam(name = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER)int pageNumber,@RequestParam(name = "pageSize",defaultValue = AppConstant.PAGE_SIZE)int pageSize,@RequestParam(name = "sortBy",defaultValue = AppConstant.SORT_BY_ENROLLMENT)String sortBy,@RequestParam(name = "sortDir",defaultValue = AppConstant.SORT_DIR)String sortDir){
        PagedResponse<EnrollmentResponse> response =enrollmentService.getStudentEnrollments(studentId,pageNumber,pageSize,sortBy,sortDir);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Enrollments fetched successfully",response));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<PagedResponse<EnrollmentResponse>>>getCourseEnrollments(@PathVariable @Positive Long courseId, @RequestParam(name = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER)int pageNumber,@RequestParam(name = "pageSize",defaultValue = AppConstant.PAGE_SIZE)int pageSize,@RequestParam(name = "sortBy",defaultValue = AppConstant.SORT_BY_COURSE)String sortBy,@RequestParam(name = "sortDir",defaultValue = AppConstant.SORT_DIR)String sortDir){
        PagedResponse<EnrollmentResponse>response=enrollmentService.getCourseEnrollments(courseId, pageNumber, pageSize, sortBy, sortDir);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Enrollments fetched successfully",response));
    }

    @DeleteMapping("/students/{studentId}/enrollments/{enrollmentId}")
    public ResponseEntity<ApiResponse<Void>> unenrollStudent(@PathVariable @Positive Long studentId,@PathVariable @Positive Long enrollmentId){
        enrollmentService.unenrollStudent(studentId,enrollmentId);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Student unenrolled successfully"));
    }

}
