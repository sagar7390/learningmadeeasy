package com.learningmadeeasy.entity;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="teacher")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="teacherId")
public class Teacher {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="teacher_id")
	private int teacherId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="email")
	private String email;
	
	//@JsonIgnore
	@OneToMany(mappedBy="teacher")
	private List<Course> courses;
	
	@OneToOne
	@JoinColumn(name="teacher_details_id")
	private TeacherDetails teacherDetails;
	
	@ElementCollection()
    @CollectionTable(name = "review_teacher", joinColumns = @JoinColumn(name = "teacher_id"))
    private List<Review> review;

	public int getTeacherId() {
		return teacherId;
	}


	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public List<Review> getReview() {
		return review;
	}


	public void setReview(List<Review> review) {
		this.review = review;
	}


	public List<Course> getCourses() {
		return courses;
	}


	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
	
	public TeacherDetails getTeacherDetails() {
		return teacherDetails;
	}


	public void setTeacherDetails(TeacherDetails teacherDetails) {
		this.teacherDetails = teacherDetails;
	}


	public Teacher() {
		
	}

	public Teacher(String name, String email, List<Review> review) {
		this.name = name;
		this.email = email;
		this.review = review;
	}

}
