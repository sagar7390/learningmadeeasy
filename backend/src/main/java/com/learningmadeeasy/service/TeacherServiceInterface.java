package com.learningmadeeasy.service;

import com.learningmadeeasy.entity.Teacher;

public interface TeacherServiceInterface {

	  void save(Teacher theTeacher);

	  String allCoursesOfTeacher(int teacherId);
  
    Teacher findTeacherById(int TeacherId);

	  String top10Teachers();

    String getAllTeachers();

    String getTeacherDetail(int teacherId);

}