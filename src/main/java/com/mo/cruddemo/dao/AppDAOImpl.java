package com.mo.cruddemo.dao;

import com.mo.cruddemo.entity.Course;
import com.mo.cruddemo.entity.Instructor;
import com.mo.cruddemo.entity.InstructorDetail;
import com.mo.cruddemo.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO{

    // define field for entity manger
    private EntityManager entityManager;

    // inject entity manger using constructor injection
    @Autowired
    public AppDAOImpl(EntityManager entityManager){
        this.entityManager=entityManager;
    }

    @Override
    @Transactional
    public void save(Instructor theinstructor) {
      entityManager.persist(theinstructor);
    }

    @Override
    public Instructor findInstructorById(int theId) {
        return entityManager.find(Instructor.class,theId);
    }

    @Override
    @Transactional
    public void deleteInstructorById(int theId) {
        //retrieve the instructor
      Instructor tempInstructor = entityManager.find(Instructor.class,theId);
      //get the courses
        List<Course> courses=tempInstructor.getCourses();
        //break associated of all courses for the instructor
        for(Course tempCourse: courses){
            tempCourse.setInstructor(null);

        }
        //delete the instructor
        entityManager.remove(tempInstructor);
    }

    @Override
    public InstructorDetail findInstructorDetailById(int theId) {
        return entityManager.find(InstructorDetail.class,theId);
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int theId) {
        //retrieve instructorDetail

        InstructorDetail tempInstructorDetail=entityManager.find(InstructorDetail.class,theId);
       //remove the associated object reference
        //break bi-directional link

        tempInstructorDetail.getInstructor().setInstructorDetail(null);


        //delete instructorDetail
        entityManager.remove(tempInstructorDetail);
    }

    @Override
    public List<Course> findCoursesByInstructorId(int theId) {
//create query
        TypedQuery<Course> query=entityManager.createQuery(
    "from Course where instructor.id = :data", Course.class);
        query.setParameter("data",theId);
        //execute query
List<Course> courses=query.getResultList();

        return courses;
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int theId) {
        //create query
        TypedQuery<Instructor> query=entityManager.createQuery("select i from Instructor i "
                + "Join Fetch i.courses "
                + "Join Fetch i.instructorDetail "
        +"where i.id = :data", Instructor.class);
        query.setParameter("data",theId);
        //execute query
        Instructor instructor=query.getSingleResult();

        return instructor;
    }

    @Override
    @Transactional
    public void update(Instructor tempinstructor) {
        entityManager.merge(tempinstructor);
    }

    @Override
    @Transactional
    public void update(Course tempCourse) {
        entityManager.merge(tempCourse);
    }

    @Override
    public Course findCourseById(int theId) {
        return entityManager.find(Course.class, theId);

    }

    @Override
    public Course findCourseAndReviewByCourseId(int theId) {
        // create query
        TypedQuery<Course>query=entityManager.createQuery(
                "select c from Course c " +
                        "JOIN FETCH c.reviews " +
                        "where c.id = :data", Course.class);
        query.setParameter("data",theId);
        //execute query
        Course course=query.getSingleResult();
        return course;
    }

    @Override
    @Transactional
    public void deleteCourseById(int theId) {
        //retrieve the courses
        Course tempCourse =entityManager.find(Course.class,theId);
        //delete the course
        entityManager.remove(tempCourse);

    }

    @Override
    @Transactional
    public void save(Course theCourse) {
        entityManager.persist(theCourse);
    }

    @Override
    public Course findCourseAndStudentsByCourseId(int theId) {
        //create query
        TypedQuery<Course>query=entityManager.createQuery(
                "select c from Course c " +
                        "JOIN FETCH c.students " +
                        "where c.id = :data", Course.class);
        query.setParameter("data",theId);
        //execute query
        Course course=query.getSingleResult();
        return course;
    }

    @Override
    public Student findStudentAndCoursesByStudentId(int theId) {
        //create query
        TypedQuery<Student>query=entityManager.createQuery(
                "select s from Student s " +
                        "JOIN FETCH s.courses " +
                        "where s.id = :data", Student.class);
        query.setParameter("data",theId);
        //execute query
        Student student=query.getSingleResult();
        return student;
    }

    @Override
    @Transactional
    public void update(Student tempStudent) {
        entityManager.merge(tempStudent);
    }

    @Override
    @Transactional
    public void deleteStudentById(int theId) {
        //retrieve the Student
Student tempStudent = entityManager.find(Student.class,theId);
        //delete the Student
entityManager.remove(tempStudent);
    }
}
