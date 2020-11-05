package com.learningmadeeasy.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learningmadeeasy.entity.Course;
import com.learningmadeeasy.service.CourseServiceInterface;


@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "http://localhost:4200")
public class CourseController {
	
	@Autowired
	private CourseServiceInterface courseServiceInterface;
	
	@GetMapping("/courses")
	public String ShowAllCourses(){
		return courseServiceInterface.showAllCourses();
	}
	
	@GetMapping("/courses/{courseId}")
	public Course ShowParticularCourse(@PathVariable int courseId) {
		return courseServiceInterface.showParticularCourse(courseId);
	}
	
	@PostMapping("/create")
	public int CreateCourse(@RequestBody Map<String,?> theCourse) {
		return courseServiceInterface.saveNewCourse(theCourse);
	}
	
	@GetMapping("/top10Courses")
	public String top10Courses(){
		return courseServiceInterface.top10Courses();
	}
	
	@GetMapping("/timeInformation/{courseId}")
	public String getCourseFeatures(@PathVariable int courseId){
		return courseServiceInterface.getCourseFeatures(courseId);
	}
	
	@GetMapping("/similarCourse/{courseCategory}")
	public String getSimilarCourseForCategory(@PathVariable String courseCategory) {
		return courseServiceInterface.getSimilarCourseForCategory(courseCategory);
	}
	
}
