package com.zyfera.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity
@Table(name = "students")
public class Student extends BaseEntity {


	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String surname;

	@Column(unique = true, nullable = false)
	private String stdNumber;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@ToString.Exclude
	private List<Grade> grades;

}
