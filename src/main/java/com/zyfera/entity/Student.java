package com.zyfera.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;



@NoArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "students")
public class Student extends  BaseEntity {



	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String surName;

	@Column(unique = true, nullable = false)
	private String stdNumber;

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@ToString.Exclude
	private List<Grade> grades;

}
