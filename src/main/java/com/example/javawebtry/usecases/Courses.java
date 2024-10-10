package com.example.javawebtry.usecases;

import com.example.javawebtry.decorators.ICoursesDAO;
import com.example.javawebtry.entities.Course;
import com.example.javawebtry.entities.Student;
import com.example.javawebtry.interceptors.LoggedInvocation;
import com.example.javawebtry.persistence.StudentsDAO;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Courses {
    @Inject
    private StudentsDAO studentsDAO;
    @Inject
    private ICoursesDAO coursesDAO;

    @Getter @Setter
    private Course courseToCreate = new Course();

    @Getter
    private List<Course> allCourses;

    @PostConstruct
    public void init(){
        loadAllCourses();
    }

    private void loadAllCourses(){
        allCourses = coursesDAO.loadAll();
    }

    @LoggedInvocation
    @Transactional
    public void createCourse(){
        coursesDAO.persist(courseToCreate);
    }

    public Integer getNumberOfStudents(Integer courseId){
        List<Student> students = studentsDAO.loadCourseStudents(courseId);
        return students.size();
    }
}
