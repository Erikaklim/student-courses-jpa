package com.example.javawebtry.usecases;

import com.example.javawebtry.entities.Course;
import com.example.javawebtry.entities.Student;
import com.example.javawebtry.entities.StudentGroup;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named
@SessionScoped
public class NewStudent implements Serializable {
    @Inject
    private StudentsDAO studentsDAO;
    @Inject
    private GroupsDAO groupsDAO;

    @Getter @Setter
    private Integer courseId;

    @Getter @Setter
    private boolean skipValidation = false;

    @Getter @Setter
    private Integer groupId;

    @Getter @Setter
    private Student studentToCreate = new Student();

    @Getter
    private List<StudentGroup> groupList;

    @Getter @Setter
    private Integer selectedYear;

    @PostConstruct
    public void init(){
//        Map<String, String> requestParameters = FacesContext
//                .getCurrentInstance()
//                .getExternalContext()
//                .getRequestParameterMap();
//        String courseIdString = requestParameters.get("courseId");
//        if(courseIdString != null){
//            Integer courseId = Integer.parseInt(requestParameters.get("courseId"));
//            course = coursesDAO.findOne(courseId);
//        }
//        loadGroupList(selectedYear);

    }

    public void refreshGroupList(){
        if(selectedYear != null){
            loadGroupList(selectedYear);
        }
    }

    private void loadGroupList(Integer selectedYear){
        groupList = groupsDAO.loadAllByYear(selectedYear);
    }

    @Transactional
    public String createStudent() {
        List<Course> courses = new ArrayList<>();
        studentToCreate.setCourses(courses);
        studentToCreate.setGroup(findGroup(groupId));
        studentsDAO.persist(studentToCreate);

        return "students?faces-redirect=true&courseId=" + courseId;
    }

    public StudentGroup findGroup(Integer id){
        return groupsDAO.findOne(id);

    }


}
