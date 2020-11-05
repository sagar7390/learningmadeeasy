package com.learningmadeeasy.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.learningmadeeasy.DAO.TeacherDAOInterface;
import com.learningmadeeasy.entity.Teacher;
import com.learningmadeeasy.serializers.CourseSerializer;

@Service
public class TeacherServieImpl implements TeacherServiceInterface {
	
	@Autowired
	private TeacherDAOInterface teacherDAOInterface;
	
	@Autowired
	private CourseSerializer courseSerializer;
	
	@Override
	@Transactional
	public void save(Teacher theTeacher) {
		teacherDAOInterface.save(theTeacher);
	}

	@Override
	@Transactional
	public String allCoursesOfTeacher(int teacherId) {
		List<Object[]> fetchedData =  teacherDAOInterface.allCoursesOfTeacher(teacherId);
		
		String response = courseSerializer.serilizationOfCourse(fetchedData);
		return response;
    }

	@Override
	@Transactional
	public Teacher findTeacherById(int TeacherId) {
		return teacherDAOInterface.findTeacherById(TeacherId);
	}

	@Override
	@Transactional
	public String getAllTeachers() {
		List<Object[]> response = teacherDAOInterface.getAllTeachers();
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode arrayNode = mapper.createArrayNode();
		
		for(int i=0;i<response.size();i++) {
			ObjectNode objectNode1 = mapper.createObjectNode();
			Object[] row = response.get(i);
	        objectNode1.put("courseId", (int)row[0]);
	        objectNode1.put("name", (String)row[1]);
	        objectNode1.put("expertCategory", (String)row[2]);
	        arrayNode.add(objectNode1);
		}
		
		String serialized = null;
		try {
			serialized = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return serialized;
	}

	@Override
	@Transactional
	public String getTeacherDetail(int teacherId) {
		List<Object[]> response = teacherDAOInterface.getTeacherDetail(teacherId);
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode objectNode1 = mapper.createObjectNode();
		objectNode1.put("about", (String)response.get(0)[0]);
        objectNode1.put("achievements", (String)response.get(0)[1]);
        objectNode1.put("myobjectives", (String)response.get(0)[2]);
        
        String serialized = null;
        try {
			serialized = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectNode1);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
        return serialized;
	}
	
	@Override
	public String top10Teachers(){
		
		List<Object[]> info = teacherDAOInterface.top10Teachers();
		
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode arrayNode = mapper.createArrayNode();
		
		for(int i=0;i<info.size();i++) {
			ObjectNode objectNode1 = mapper.createObjectNode();
			Object[] row = info.get(i);
	        objectNode1.put("teacherName", (String)row[0]);
	        objectNode1.put("expertCategory", (String)row[1]);
	        objectNode1.put("courseCount", (BigInteger)row[2]);
	        
	        if(row[3]==null)
		        objectNode1.put("avgRating", 0);
	        else
	        	objectNode1.put("avgRating", (BigDecimal)row[3]);
   
	       
	        arrayNode.add(objectNode1);
		}
		
		String serialized = null;
		try {
			serialized = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(arrayNode);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return serialized;
	}
	
	
	
}
