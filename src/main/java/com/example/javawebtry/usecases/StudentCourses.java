package com.example.javawebtry.usecases;

import com.example.javawebtry.entities.Course;
import com.example.javawebtry.entities.Student;
import com.example.javawebtry.interceptors.LoggedInvocation;
import com.example.javawebtry.persistence.CoursesDAO;
import com.example.javawebtry.persistence.StudentsDAO;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Named
@SessionScoped
public class StudentCourses implements Serializable {
    @Inject
    private StudentsDAO studentsDAO;

    @Inject
    private CoursesDAO coursesDAO;

    @Getter
    private List<Course> studentCourses;

    @Getter @Setter
    private List<Course> availableCourses = new ArrayList<>();

    @Getter @Setter
    private Student student;

    @Getter
    private Integer studentId;
    private Integer previousStudentId = null;
    public void setStudentId(Integer studentId) {
        if (!Objects.equals(this.studentId, studentId)) {
            this.studentId = studentId;
            loadStudentData();
        }
    }

    public void loadStudentData() {
        if (!Objects.equals(studentId, previousStudentId)) {
            this.student = studentsDAO.findOne(studentId);
            refreshCourseLists();
            previousStudentId = studentId;
        }
    }
    private void loadAllCourses(){

//        studentCourses = coursesDAO.loadAllStudentCourses(id);
        studentCourses = student.getCourses();
    }

    @PostConstruct
    private void init() {
    }

    public void loadAllAvailableCourses(){
        availableCourses = coursesDAO.findCoursesNotEnrolledByStudent(studentId);
    }

    public void refreshCourseLists(){
        loadAllAvailableCourses();
        loadAllCourses();
    }

    @Transactional
    public String addStudentToCourse(Integer courseId){
        Course course = coursesDAO.findOne(courseId);
        student.getCourses().add(course);
        course.getStudents().add(student);
        studentsDAO.update(student);

        return "studentCourses.xhtml?faces-redirect=true&studentId=" + studentId;
    }

}
