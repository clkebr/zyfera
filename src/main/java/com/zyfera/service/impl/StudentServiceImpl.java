package com.zyfera.service.impl;

import com.zyfera.dto.incoming.StudentCreateForm;
import com.zyfera.dto.outgoing.StudentDetail;
import com.zyfera.entity.Grade;
import com.zyfera.entity.Student;
import com.zyfera.exception.StudentNotFoundException;
import com.zyfera.mapper.MapperUtil;
import com.zyfera.repository.StudentRepository;
import com.zyfera.service.StudentService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
  private final StudentRepository studentRepository;

  private final MapperUtil mapperUtil; // used mapper to convert dto to entity, or vice versa

  public StudentServiceImpl(StudentRepository studentRepository, MapperUtil mapperUtil) {
    this.studentRepository = studentRepository;
    this.mapperUtil = mapperUtil;
  }

  @Override
  public StudentDetail save(StudentCreateForm studentCreateForm) {
    // check if user already exist
    if (studentRepository.findByStdNumber(studentCreateForm.getStdNumber()).isEmpty()) {
      Student studentToBeSaved = mapperUtil.convertToType(studentCreateForm, new Student());

      // to check duplicate grade-code create a map to store grade value
      Map<String, Integer> gradeMap = new HashMap<>();

      // if map does not contain grade-code store code and value if contains update value as average
      for (Grade grade : studentToBeSaved.getGrades()) {
        if (!gradeMap.containsKey(grade.getCode())) {
          gradeMap.put(grade.getCode(), grade.getValue());
        } else {
          Integer existingValue = gradeMap.get(grade.getCode());
          Integer averageValue = (existingValue + grade.getValue()) / 2;
          gradeMap.put(grade.getCode(), averageValue);
        }
      }

      List<Grade> grades =
          gradeMap.entrySet().stream()
              .map(entry -> new Grade(entry.getKey(), entry.getValue()))
              .collect(Collectors.toList());

      // save updated data
      studentToBeSaved.setGrades(grades);

      Student saved = studentRepository.save(studentToBeSaved);
      return mapperUtil.convertToType(saved, new StudentDetail());
    } else { // trow custom runtime exception to inform user
      throw new StudentNotFoundException(
          "Student with number " + studentCreateForm.getStdNumber() + " already exist!");
    }
  }

  // todo: ask which fields are allowed to be updated. now i updated all
  // todo: what are the requirements, if a value with existing grade-code is updating. now i set average
  @Override
  public StudentDetail update(StudentCreateForm studentCreateForm, String stdNumber) {

    // find user by unique value(stdNumber)
    Optional<Student> student = studentRepository.findByStdNumber(stdNumber);

    Student studentToBeSaved = mapperUtil.convertToType(studentCreateForm, new Student());

    // if user exist update user
    if (student.isPresent()) {

      // save in a map existing Grades (code, value), use it as reference
      Map<String, Integer> existingGradesMap =
          student.get().getGrades().stream()
              .collect(Collectors.toMap(Grade::getCode, Grade::getValue));

      // if existing gradeList has the same grade code save average
      // loop to check each grade in the input
      for (Grade grade : studentToBeSaved.getGrades()) {

        // if the ref map(existingGradesMap) contains the grade-code save average as value
        if (existingGradesMap.containsKey(grade.getCode())) {
          Integer existingValue = existingGradesMap.get(grade.getCode());
          Integer averageValue = (existingValue + grade.getValue()) / 2;

          existingGradesMap.put(grade.getCode(), averageValue);
        } else {
          // if not contains save as it is
          existingGradesMap.put(grade.getCode(), grade.getValue());
        }
      }

      // convert map value as list of Grade obj to save the obj
      List<Grade> grades =
          existingGradesMap.entrySet().stream()
              .map(entry -> new Grade(entry.getKey(), entry.getValue()))
              .collect(Collectors.toList());

      // save updated data
      studentToBeSaved.setGrades(grades);
      studentToBeSaved.setId(student.get().getId());

      Student saved = studentRepository.save(studentToBeSaved);
      return mapperUtil.convertToType(saved, new StudentDetail());
    } else {
      throw new StudentNotFoundException(
          "Student with number " + studentCreateForm.getStdNumber() + " not found!");
    }
  }
}
