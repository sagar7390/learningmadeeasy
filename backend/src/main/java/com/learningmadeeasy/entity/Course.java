package com.learningmadeeasy.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="course")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="courseId")
public class Course {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="course_id")
	private int courseId;
	
	@Column(name="course_name")
	private String courseName;
	
	@ManyToOne
	@JoinColumn(name="teacher_id")
	private Teacher teacher;
	
	@OneToMany
	@JoinColumn(name="course_id") // Uni-directional mapping
	private List<Question> questions;
	
	@Column(name="course_category")
	private String courseCategory;
	
	@Column(name="course_summary")
	private String courseSummary;
	
	@Column(name="requirements")
	private String requirements;
	
	
	//LazyCollection.EXTRA = .size() and .contains() won't initialize the whole collection so 
	//it is a performance enhancement
		
	@LazyCollection(LazyCollectionOption.EXTRA)
	@JsonIgnore
	@ManyToMany()
	@JoinTable(
			name="course_student",
			joinColumns=@JoinColumn(name="course_id"),
			inverseJoinColumns=@JoinColumn(name="student_id")
			)
	private List<Student> students;
	
	
	
	@OneToMany(mappedBy="course")
	private List<Video> videos;
	

	@ElementCollection()
    @CollectionTable(name = "review_course", joinColumns = @JoinColumn(name = "course_id"))
    private List<Review> review;	
	
	public Course() {
		
	}

	public Course(String courseName, String courseCategory, String courseSummary, String requirements,
			List<Review> review) {
		this.courseName = courseName;
		this.courseCategory = courseCategory;
		this.courseSummary = courseSummary;
		this.requirements = requirements;
		this.review = review;
	}


	public String getCourseSummary() {
		return courseSummary;
	}

	public void setCourseSummary(String courseSummary) {
		this.courseSummary = courseSummary;
	}

	public String getRequirements() {
		return requirements;
	}

	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}


	public List<Review> getReview() {
		return review;
	}

	public void setReview(List<Review> review) {
		this.review = review;
	}

	public String getCourseCategory() {
		return courseCategory;
	}


	public void setCourseCategory(String courseCategory) {
		this.courseCategory = courseCategory;
	}


	public void addVideo(Video newVideo) {
		
		if (videos == null) {
			videos = new ArrayList<>();
		}
		
		videos.add(newVideo);
		newVideo.setCourse(this);
	}
	
	public void addStudent(Student theStudent) {
		
		if (students == null) {
			students = new ArrayList<>();
		}
		
		students.add(theStudent);
		
		// question here as why not students.setCourse()???
	}
}
