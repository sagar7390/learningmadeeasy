package com.learningmadeeasy.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.learningmadeeasy.DAO.CourseCategoryDAOInterface;

@Service
public class CourseCategoryServiceImpl implements CourseCategoryServiceInterface {
	
	@Autowired
	private CourseCategoryDAOInterface courseCategoryDAOinterface;
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public String showCategories() {
		
		List<Object[]> info = courseCategoryDAOinterface.showCategories();
				
		ObjectMapper mapper = new ObjectMapper();
		ArrayNode arrayNode = mapper.createArrayNode();
		
		for(int i=0;i<info.size();i++) {
			ObjectNode objectNode1 = mapper.createObjectNode();
			Object[] row = info.get(i);
	        objectNode1.put("categoryUrl", (String)row[2]);
	        objectNode1.put("courseCategory", (String)row[0]);
	        objectNode1.put("coursesUnderCategoryCount", (long)row[1]);
   
	       
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
	public Map<String, Object> studentsEnrolled() {
		
		HashMap<String,Object> tempMap = new HashMap<>();
		
		List<Object[]> result = courseCategoryDAOinterface.studentsEnrolled();
		
		int size = result.size();
		
		for(int i=0;i<size;i++) {
			Object[] temp = result.get(i);
			HashMap<String,Object> tempMap2 = new HashMap<>();
			
			tempMap2.put("courseId", temp[0]);
			tempMap2.put("studentsEnrolled", temp[1]);
		
			tempMap.put("Categorydetails"+(i), tempMap2);			
		}
		return tempMap;		
	}
	
	@Override
	@Transactional
	public Map<String,Object> countofEnrolledCourseCategory(String courseCategoryName) {
		
		List<Object[]> result  = courseCategoryDAOinterface.countofEnrolledCourseCategory(courseCategoryName);
		
		int size = result.size();
		int count = 0;
		for(int i=0;i<size;i++) {
			Object[] temp = result.get(i);
			int courseId2 = (int) temp[0];
			Long studentCount = (Long) temp[1];
			//System.out.println("Debugging : " + courseId2);
			
			String categoryName = (String) entityManager.createQuery("select courseCategory from Course where courseId=:courseId")
										.setParameter("courseId", courseId2).getSingleResult();
			
			if(categoryName.compareTo(courseCategoryName)==0) {
				count+=studentCount;
			}	
		}
		System.out.println("Total stundents Enrolled under this category" + count);
		
		HashMap<String,Object> tempMap = new HashMap<>();
		
		tempMap.put("CategoryName", courseCategoryName);
		tempMap.put("StudentsEnrolled", count);
		
		return tempMap;
	}

	@Override
	public Map<String, Object> getAllCoursesForThisCategory(String categoryName) {
		
	List<Object[]> result  = courseCategoryDAOinterface.getAllCoursesForThisCategory(categoryName);
	
		int size = result.size();
		
		HashMap<String,Object> tempMap = new HashMap<>();
		
		for(int i=0;i<size;i++) {
			
			HashMap<String,Object> tempMap2 = new HashMap<>();
			
			Object[] temp = result.get(i);
			
			tempMap2.put("courseId", temp[0]);
			tempMap2.put("courseName", temp[1]);
			
			tempMap.put("Course No. " + (i+1), tempMap2);
			
		}
	
		return tempMap;
	}
}
