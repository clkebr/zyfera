package com.zyfera.service;

import com.zyfera.dto.incoming.StudentCreateDto;
import com.zyfera.dto.outgoing.StudentDto;

public interface StudentService {
  StudentDto save(StudentCreateDto studentCreateDto);

  StudentDto update(StudentCreateDto studentCreateDto, String stdNumber);
}
