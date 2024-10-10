package com.example.javawebtry.decorators;

import com.example.javawebtry.entities.Course;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;
import java.util.List;

@Decorator
public abstract class CourseDecorator implements ICoursesDAO {
    @Inject
    @Delegate
    private ICoursesDAO delegate;

    @Override
    public List<Course> loadAll() {
        System.out.println("--------- Decorator - Before loadAll");
        List<Course> courses = delegate.loadAll();
        System.out.println("--------- Decorator - After loadAll");
        return courses;
    }

    @Override
    public List<Course> loadAllStudentCourses(Integer studentId) {
        System.out.println("----------- Decorator - Before loadAllStudentCourses");
        List<Course> courses = delegate.loadAllStudentCourses(studentId);
        System.out.println("----------- Decorator - After loadAllStudentCourses");
        return courses;
    }

    @Override
    public List<Course> findCoursesNotEnrolledByStudent(Integer studentId) {
        System.out.println("--------- Decorator - Before findCoursesNotEnrolledByStudent");
        List<Course> courses = delegate.findCoursesNotEnrolledByStudent(studentId);
        System.out.println("---------- Decorator - After findCoursesNotEnrolledByStudent");
        return courses;
    }

    @Override
    public void persist(Course course) {
        System.out.println("--------- Decorator - Before persist");
        delegate.persist(course);
        System.out.println("--------- Decorator - After persist");
    }

    @Override
    public Course findOne(Integer id) {
        System.out.println("---------- Decorator - Before findOne");
        Course course = delegate.findOne(id);
        System.out.println("---------- Decorator - After findOne");
        return course;
    }

    @Override
    public Course update(Course course) {
        System.out.println("---------- Decorator - Before update");
        Course updatedCourse = delegate.update(course);
        System.out.println("---------- Decorator - After update");
        return updatedCourse;
    }
}

