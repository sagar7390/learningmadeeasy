
package com.learningmadeeasy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learningmadeeasy.entity.Teacher;
import com.learningmadeeasy.service.TeacherServiceInterface;


@RestController
@RequestMapping("/teachers")
@CrossOrigin(origins = "http://localhost:4200")
public class TeacherController {
	
	@Autowired
	private TeacherServiceInterface teacherserviceinterface;

	@PostMapping("/")
	public Teacher createTeacher(@RequestBody Teacher theTeacher) {
		theTeacher.setTeacherId(0);
		teacherserviceinterface.save(theTeacher);
		return theTeacher;
	}
	
	@GetMapping("/top10Teachers")
	public String top10Teachers(){
		return teacherserviceinterface.top10Teachers();
	}
	
	
	

	@GetMapping("/teacher/{teacherId}")
	public Teacher getSingleTeacher(@PathVariable int teacherId) {
		return teacherserviceinterface.findTeacherById(teacherId);
	}
	
	@GetMapping("/teachersCourses/{teacherId}")
	public String allCoursesOfTeacher(@PathVariable int teacherId){
		return teacherserviceinterface.allCoursesOfTeacher(teacherId);
	}
	
	@GetMapping(value="/teachers",  produces={ "application/json" })
	public String getAllTeachers() {
		return teacherserviceinterface.getAllTeachers();
	}
	
	// DON'T USE THIS API
	@GetMapping(value="/teacherDetails/{teacherId}", produces={ "application/json" })
	public String getTeacherDetail(@PathVariable int teacherId){
		return teacherserviceinterface.getTeacherDetail(teacherId);
	}
	
}

