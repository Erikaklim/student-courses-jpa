package com.example.javawebtry.usecases;

import com.example.javawebtry.entities.Course;
import com.example.javawebtry.entities.Student;
import com.example.javawebtry.entities.StudentGroup;
import com.example.javawebtry.interceptors.LoggedInvocation;
import com.example.javawebtry.persistence.CoursesDAO;
import com.example.javawebtry.persistence.GroupsDAO;
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
import java.util.*;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class Students implements Serializable {
    @Inject
    private StudentsDAO studentsDAO;
    @Inject
    private CoursesDAO coursesDAO;

    @Getter @Setter
    private Course course;

    @Getter @Setter
    private Integer groupId;

    @Getter
    private Integer courseId;

    @Getter @Setter
    private Student studentToCreate = new Student();

    @Getter
    private List<Student> allAvailableStudents = new ArrayList<>();

    @Getter @Setter
    private List<Student> courseStudents = new ArrayList<>();

    private Integer previousCourseId = null;

    public void setCourseId(Integer courseId) {
        if (!Objects.equals(this.courseId, courseId)) {
            this.courseId = courseId;
            loadCourseData(); // Immediately load or refresh data when courseId changes
        }
    }
    public void loadCourseData() {
        if (!Objects.equals(courseId, previousCourseId)) {
            this.course = coursesDAO.findOne(courseId);
            refreshStudentLists();
            previousCourseId = courseId;
        }
    }

    @PostConstruct
    public void init(){
//        Map<String, String> requestParameters = FacesContext
//                .getCurrentInstance()
//                .getExternalContext()
//                .getRequestParameterMap();
//        String courseIdString = requestParameters.get("courseId");
//        courseId = Integer.parseInt(courseIdString);
    }

    private void loadCourseStudents(){
//        courseStudents = course.getStudents();
        courseStudents = studentsDAO.loadCourseStudents(courseId);
    }

    public void refreshStudentLists(){
        loadCourseStudents();
        loadAllAvailableStudents();
    }

    private void loadAllAvailableStudents() {
        allAvailableStudents = studentsDAO.loadStudentsNotInCourse(courseId);
    }

    @LoggedInvocation
    @Transactional
    public String addStudentToCourse(Integer studentId){
        Student student = studentsDAO.findOne(studentId);
        student.getCourses().add(course);
//        course.getStudents().add(student);

        studentsDAO.update(student);

        return "students.xhtml?faces-redirect=true&courseId=" + courseId;
    }

}
