package com.learningmadeeasy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.learningmadeeasy.entity.Course;
import com.learningmadeeasy.entity.Student;
import com.learningmadeeasy.service.StudentServiceInterface;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {
	
	@Autowired
	private StudentServiceInterface studentServiceInterface;
	
	@PostMapping("/students")
	private void save(@RequestBody Student theStudent) {
		studentServiceInterface.save(theStudent);
	}
	
	///future requirements
	@GetMapping("/{studentId}/{courseId}")
	public void linkStudentWithCourse(@PathVariable int studentId, @PathVariable int courseId) {
		
		studentServiceInterface.savecourseforstudent(studentId,courseId);
		
	}
	
	@GetMapping("/findcourses/{studentId}")
	public List<Course> getAllCourses(@PathVariable int studentId){
		
		return studentServiceInterface.getAllCourses(studentId);
	}
}
