package com.mo.cruddemo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "course")
public class Course {
    //define our fields
    //define constructor
    //define getter setter
    //define toString
    //annotate fields
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "id")
    private int id;
@Column(name = "title")
    private  String title;
@ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
@JoinColumn(name = "instructor_id")
    private Instructor instructor;
@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
@JoinColumn(name = "course_id")
    private List<Review> reviews;
@ManyToMany(fetch =FetchType.LAZY,cascade = {CascadeType.DETACH,
        CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
@JoinTable(name = "course_student",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")


)
private List<Student>students;

public void addStudent(Student theStudent){
    if(students==null){
        students=new ArrayList<>();
    }
students.add(theStudent);
}
    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Course(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Course(){

    }
   public void addReview(Review theReview){
        if(reviews==null){
            reviews=new ArrayList<>();
        }
      reviews.add(theReview);
    }
}
