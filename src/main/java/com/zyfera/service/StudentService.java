package com.zyfera.service;

import com.zyfera.dto.StudentDto;

public interface StudentService {
	StudentDto save(StudentDto studentDto);

	StudentDto update(StudentDto studentDto);
}
