package com.zyfera.service;

import com.zyfera.dto.incoming.StudentCreateForm;
import com.zyfera.dto.outgoing.StudentDetail;

public interface StudentService {
  StudentDetail save(StudentCreateForm studentCreateForm);

  StudentDetail update(StudentCreateForm studentCreateForm, String stdNumber);
}
