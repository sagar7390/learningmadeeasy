
package com.learningmadeeasy.DAO;

import java.util.List;

import com.learningmadeeasy.entity.Teacher;

public interface TeacherDAOInterface {

	void save(Teacher theTeacher);
  
    Teacher findTeacherById(int teacherId);

	List<Object[]> allCoursesOfTeacher(int teacherId);

	List<Object[]> getAllTeachers();

	List<Object[]> getTeacherDetail(int teacherId);

	List<Object[]> top10Teachers();	

}
