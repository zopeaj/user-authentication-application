package com.app.auth.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path = "/management/api/v1/students")
public class StudentManagementController {
	   private static final List<Student> STUDENTS = Arrays.asList(
			      new Student(1, "James Bond"),
			      new Student(2, "Maria Jones"),
			      new Student(3, "Anna Smith")
			  );
	
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')") //hasAnyRole('ROLE_'), hasRole('ROLE_'), 
	public List<Student> getAllStudents() {
		return STUDENTS;
	}
	  
	@PostMapping
	@PreAuthorize("hasAnyAuthority('student:write')") //hasAuthority('permission') hasAnyAuthority('permission')
	public void registerNewStudent(@RequestBody Student student) {
		System.out.println(student);
	}
	
	@DeleteMapping
	@PreAuthorize("hasAnyAuthority('student:write')")
	public void deleteStudent(@PathVariable("studentId") Integer studentId) {
		System.out.println(studentId);
	}
	
	@PutMapping(path = "{studentId}")
	@PreAuthorize("hasAnyAuthority('student:write')")
	public void updateStudent(@PathVariable("studentId") Integer studentId, @RequestBody Student student) {
		System.out.println(String.format("%s %s", student, studentId));
	}
}
