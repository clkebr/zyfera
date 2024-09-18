package com.zyfera.service.impl;

import com.zyfera.dto.StudentDto;
import com.zyfera.entity.Grade;
import com.zyfera.entity.Student;
import com.zyfera.exception.StudentNotFoundException;
import com.zyfera.mapper.MapperUtil;
import com.zyfera.repository.StudentRepository;
import com.zyfera.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
	private final StudentRepository studentRepository;

	private final MapperUtil mapperUtil; // used mapper to convert dto to entity, or vice versa

	public StudentServiceImpl(StudentRepository studentRepository, MapperUtil mapperUtil) {
		this.studentRepository = studentRepository;
		this.mapperUtil = mapperUtil;
	}

	@Override
	public StudentDto save(StudentDto studentDto) {
		//check if user already exist
		if (studentRepository.findByStdNumber(studentDto.getStdNumber()).isEmpty()){
			Student studentToBeSaved = mapperUtil.convertToType(studentDto, new Student());

			// to check duplicate grade-code create a map to store grade value
			Map<String, Integer> gradeMap = new HashMap<>();

			// if map does not contain grade-code store code and value if contains update value as average
			for (Grade grade : studentToBeSaved.getGrades()) {
				if(!gradeMap.containsKey(grade.getCode())){
					gradeMap.put(grade.getCode(),grade.getValue());
				}else{
					Integer existingValue = gradeMap.get(grade.getCode());
					Integer averageValue = (existingValue + grade.getValue()) / 2 ;
					gradeMap.put(grade.getCode(), averageValue);
				}
			}

			List<Grade> grades = gradeMap.entrySet().stream()
					.map(entry -> new Grade(entry.getKey(), entry.getValue()))
					.collect(Collectors.toList());

			// save updated data
			studentToBeSaved.setGrades(grades);

			Student saved = studentRepository.save(studentToBeSaved);
			return mapperUtil.convertToType(saved,new StudentDto());
		}else{ // trow custom runtime exception to inform user
			throw new StudentNotFoundException("Student with number"+ studentDto.getStdNumber()+ " already exist!");
		}

	}

	//todo: ask which fields are allowed to be updated. now i updated all
	//todo: what are the requirements, if a value with existing grade-code is updating. now i set average
	//todo: ask based on which object should we check whether the student is exist if student number is not required in payload
	@Override
	public StudentDto update(StudentDto studentDto) {

		// find user by unique value(stdNumber)
		Optional<Student> student = studentRepository.findByStdNumber(studentDto.getStdNumber());

		Student studentToBeSaved = mapperUtil.convertToType(studentDto, new Student());


		// if user exist update user
		if (student.isPresent()) {

			// save in a map existing Grades (code, value), use it as reference
			Map<String, Integer> existingGradesMap = student.get().getGrades().stream()
					.collect(Collectors.toMap(Grade::getCode, Grade::getValue));

			// if existing gradeList has the same grade code save average
			// loop to check each grade in the input
			for (Grade grade : studentToBeSaved.getGrades()) {

				// if the ref map(existingGradesMap) contains the grade-code save average as value
				if ( existingGradesMap.containsKey(grade.getCode())){
					Integer existingValue = existingGradesMap.get(grade.getCode());
					Integer averageValue = (existingValue + grade.getValue()) / 2 ;

					existingGradesMap.put(grade.getCode(), averageValue);
				}else {
					// if not contains save as it is
					existingGradesMap.put(grade.getCode(), grade.getValue());
				}

			}

			//convert map value as list of Grade obj to save the obj
			List<Grade> grades = existingGradesMap.entrySet().stream()
					.map(entry -> new Grade(entry.getKey(), entry.getValue()))
					.collect(Collectors.toList());

			// save updated data
			studentToBeSaved.setGrades(grades);
			studentToBeSaved.setId(student.get().getId());

			Student saved = studentRepository.save(studentToBeSaved);
			return mapperUtil.convertToType(saved,new StudentDto());
		}else{
			throw new StudentNotFoundException("Student with number"+ studentDto.getStdNumber()+ " not found!");
		}
	}
}
