package com.learningmadeeasy.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.learningmadeeasy.entity.Teacher;

@Repository
public class TeacherDAOImpl implements TeacherDAOInterface {
	
	@Autowired
	private EntityManager entityManager;
	
	@Override
	public void save(Teacher theTeacher) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		currentSession.saveOrUpdate(theTeacher);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> allCoursesOfTeacher(int teacherId) {
		
		String nativeSqlQuery = "select c.course_id, c.course_name, t.name , (select count(*) from course_student cs  where cs.course_id=c.course_id ), \r\n" + 
				"(select count(*) from review_course cr  where cr.course_id=c.course_id ), (select avg(cr.rating) from review_course cr  where cr.course_id=c.course_id )\r\n" + 
				" from course c join \r\n" + 
				"teacher t on t.teacher_id=c.teacher_id where t.teacher_id=:teacherId";
		
		Query query = entityManager.createNativeQuery(nativeSqlQuery);
		query.setParameter("teacherId", teacherId);
		List<Object[]> response= query.getResultList();
		
		return response;
	}
  
	@Override
	public Teacher findTeacherById(int teacherId) {
		
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// get the Teacher
		Teacher theTeacher  = currentSession.get(Teacher.class, teacherId);
		return theTeacher;
		
	}
  
  @Override
  public List<Object[]> top10Teachers(){
	  
	  String query ="select t.name, td.expert_category, (select count(*) from Course c where c.teacher_id=t.teacher_id) , \r\n"
	  		+ "(select avg(rt.rating) from review_teacher rt where rt.teacher_id=t.teacher_id) teacher_score \r\n"
	  		+ "from teacher t join teacher_details td on td.teacher_details_id=t.teacher_details_id order by teacher_score desc limit 10";
	  
	  List<Object[]> resultSet = entityManager.createNativeQuery(query).getResultList();
		
	  return resultSet;
  }
  
  
  
  

	@Override
	public List<Object[]> getAllTeachers() {
		
		List<Object[]> allTeachers = entityManager.createQuery("select tr.teacherId, tr.name, tr.teacherDetails.expertCategory from Teacher tr", Object[].class)
								     .getResultList();
		return allTeachers;
	}

	@Override
	public List<Object[]> getTeacherDetail(int teacherId) {
		List<Object[]> teacherDetails = entityManager.createQuery("select td.teacherDetails.about, td.teacherDetails.achievements, td.teacherDetails.myobjectives from Teacher td where td.teacherId=:teacherId", Object[].class)
				                        .setParameter("teacherId", teacherId)
				                        .getResultList();
		return teacherDetails;
	}

}