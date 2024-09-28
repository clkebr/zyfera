package com.zyfera.service.impl;

import com.zyfera.dto.incoming.GradeCreateDto;
import com.zyfera.dto.incoming.StudentCreateDto;
import java.util.Arrays;
import java.util.List;

public class TestDocumentInitializer {

  public static StudentCreateDto getStudent() {
    return StudentCreateDto.builder()
        .name("John")
        .surname("Doe")
        .stdNumber("B012X00012")
        .grades(generateGrades())
        .build();
  }

  private static List<GradeCreateDto> generateGrades() {
    return Arrays.asList(
        new GradeCreateDto("MT101", 90),
        new GradeCreateDto("PH101", 75),
        new GradeCreateDto("CH101", 60),
        new GradeCreateDto("MT101", 70),
        new GradeCreateDto("HS101", 65));
  }
}
