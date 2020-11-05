package com.learningmadeeasy.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.learningmadeeasy.DAO.CourseDAOInterface;
import com.learningmadeeasy.entity.Course;
import com.learningmadeeasy.serializers.CourseSerializer;

@Service
public class CourseServiceImpl implements CourseServiceInterface {
	
	@Autowired
	private CourseDAOInterface courseDAOInterface;
	
	@Autowired
	private CourseSerializer courseSerializer;
	
	@Override
	@Transactional
	public String showAllCourses(){
		
		List<Object[]> fetchedData =  courseDAOInterface.showAllCourses();
		String response = courseSerializer.serilizationOfCourse(fetchedData);
		return response;
		
	}
	
	@Override
	@Transactional
	public Course showParticularCourse(int courseId) {
		return courseDAOInterface.showParticularCourse(courseId);
	}

	@Override
	@Transactional
	public int saveNewCourse(Map<String, ?> theCourse) {
		return courseDAOInterface.saveNewCourse(theCourse);
	}

	@Override
	@Transactional
	public String getCourseFeatures(int courseId) {
		List<Object[]> response = courseDAOInterface.getCourseFeatures(courseId);
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode objectNode1 = mapper.createObjectNode();
		objectNode1.put("courseId", (int)response.get(0)[0]);
        objectNode1.put("duration", ((BigDecimal)response.get(0)[1]).toBigInteger());
        objectNode1.put("quizzes", 5);
        objectNode1.put("numberOfLectures", (BigInteger)response.get(0)[2]);
        objectNode1.put("numberOfStudents", (BigInteger)response.get(0)[3]);
        
        String serialized = null;
        try {
			serialized = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectNode1);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        return serialized;
	}

	@Override
	public String getSimilarCourseForCategory(String courseCategory) {
		List<Object[]> fetchedData = courseDAOInterface.getSimilarCourseForCategory(courseCategory);
		String response = courseSerializer.serilizationOfCourse(fetchedData);
		return response;
	}
	
	@Override
	@Transactional
	public String top10Courses(){
		
		List<Object[]> theCourses = courseDAOInterface.top10Courses();
		String response = courseSerializer.serilizationOfCourse(theCourses);
		return response;
	}
	

}
