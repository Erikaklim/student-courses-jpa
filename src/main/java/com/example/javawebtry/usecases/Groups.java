package com.example.javawebtry.usecases;

import com.example.javawebtry.entities.Course;
import com.example.javawebtry.entities.Student;
import com.example.javawebtry.entities.StudentGroup;
import com.example.javawebtry.persistence.GroupsDAO;
import com.example.javawebtry.persistence.StudentsDAO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Model
public class Groups {

    @Inject
    private StudentsDAO studentsDAO;
    @Inject
    private GroupsDAO groupsDAO;

    @Getter @Setter
    private StudentGroup groupToCreate = new StudentGroup();

    @Getter
    private List<StudentGroup> allGroups = new ArrayList<>();

    @Getter @Setter
    private Integer selectedGroupId;

    @Getter
    private List<Student> allStudents = new ArrayList<>();

    @Getter @Setter
    private Integer courseId;

    @Getter
    private Integer nextGroupNumber;

    @Getter
    private Integer groupId;
    private void loadAllGroupStudents(Integer id){
        allStudents = studentsDAO.loadAllByGroupId(id);
    }

    @PostConstruct
    public void init(){
//            loadAllGroups();
    }

    public void initializeGroupPage(){
        Map<String, String> requestParameters = FacesContext
                .getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();
        String parameter = requestParameters.get("groupId");
        if(parameter != null) {
            groupId = Integer.parseInt(parameter);
            loadAllGroupStudents(groupId);
        }
    }

    public void onYearChange(){
        List<StudentGroup> yearGroups = groupsDAO.loadAllByYear(groupToCreate.getYear());
        nextGroupNumber = yearGroups.size() + 1;
    }

//    private void loadAllGroups(){
//        allGroups = groupsDAO.loadAll();
//
//        nextGroupNumber = allGroups.size() + 1;
//    }

    public String findYear(Integer groupId){
        if(groupId != null){
            return "Year " + groupsDAO.findOne(groupId).getYear() +": ";
        }
        else{
            return "";
        }
    }

    public String findGroupNumber(Integer groupId){
        if(groupId != null){
            return "Group " + groupsDAO.findOne(groupId).getGroupNumber() +": ";
        }
        else{
            return "";
        }
    }



    @Transactional
    public String createGroup(){
        onYearChange();
        groupsDAO.persist(groupToCreate);
        groupToCreate.setGroupNumber(nextGroupNumber);
        return "newStudent.xhtml?faces-redirect=true&courseId=" + courseId;
    }


}
