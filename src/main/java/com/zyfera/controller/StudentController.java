package com.zyfera.controller;

import com.zyfera.dto.incoming.StudentCreateForm;
import com.zyfera.dto.outgoing.StudentDetail;
import com.zyfera.entity.ResponseWrapper;
import com.zyfera.service.StudentService;
import jakarta.validation.Valid;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students") // to top create endpoint
public class StudentController {
  private final StudentService studentService;

  // autowired field
  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  // used responseWrapper to use same output structure for all endpoints
  @PostMapping()
  public ResponseEntity<ResponseWrapper> createStudent(
      @Valid @RequestBody StudentCreateForm studentCreateForm) {

    StudentDetail saved = studentService.save(studentCreateForm);
    return ResponseEntity.of(
        Optional.of(
            new ResponseWrapper("student is successfully created", saved, HttpStatus.CREATED)));
  }

  @PutMapping("/{stdNumber}")
  public ResponseEntity<ResponseWrapper> updateStudent(
      @Valid @RequestBody StudentCreateForm studentCreateForm, @PathVariable String stdNumber) {

    StudentDetail saved = studentService.update(studentCreateForm, stdNumber);
    return ResponseEntity.of(
        Optional.of(new ResponseWrapper("student is successfully updated", saved, HttpStatus.OK)));
  }
}
