package com.example.javawebtry.decorators;

import com.example.javawebtry.entities.Course;

import java.util.List;

public interface ICoursesDAO {
    List<Course> loadAll();
    List<Course> loadAllStudentCourses(Integer studentId);
    List<Course> findCoursesNotEnrolledByStudent(Integer studentId);
    void persist(Course course);
    Course findOne(Integer id);

    Course update(Course course);
}


