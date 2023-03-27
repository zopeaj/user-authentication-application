package com.app.auth.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
	
	private static final List<Student> STUDENTS = Arrays.asList(
			new Student(1, "James Bond"), 
			new Student(2, "Maria Jones"), 
			new Student(3, "Anna Smith"), 
			new Student(4, "Nana Koffie")
	);
	
	@GetMapping(path = "{studentId}")
	public Student getStudent(@PathVariable("studentId") Integer studentId) {
	    return STUDENTS.stream().filter(student -> studentId.equals(student.getStudentId()))
	    		.findFirst()
	    		.orElseThrow(() -> new IllegalStateException("Student " + studentId + " does not exists"));
	}
	
	@GetMapping(path = "all")
	public List<Student> getAllStudents() {
		return STUDENTS;
	}
}
