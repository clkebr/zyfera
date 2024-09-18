package com.zyfera.controller;

import com.zyfera.dto.StudentDto;
import com.zyfera.entity.ResponseWrapper;
import com.zyfera.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students") // to top create endpoint
public class StudentController {
	private final StudentService studentService;

	//autowired field
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}


	// used responseWrapper to use same output structure for all endpoints
	@PostMapping("/create")
	public ResponseEntity<ResponseWrapper> createStudent(@Valid @RequestBody StudentDto studentDto){

		StudentDto saved = studentService.save(studentDto);
		return ResponseEntity.ok(new ResponseWrapper("student is successfully created", saved, HttpStatus.CREATED));
	}
	@PutMapping("/update")
	public ResponseEntity<ResponseWrapper> updateStudent(@Valid @RequestBody StudentDto studentDto){

		StudentDto saved = studentService.update(studentDto);
		return ResponseEntity.ok(new ResponseWrapper("student is successfully updated", saved, HttpStatus.OK));
	}
}
