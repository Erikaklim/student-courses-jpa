package com.example.javawebtry.usecases;

import com.example.javawebtry.mybatis.dao.CourseMapper;
import com.example.javawebtry.mybatis.model.Course;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class CoursesMyBatis {
    @Inject
    private CourseMapper courseMapper;

    @Getter @Setter
    private Course courseToCreate = new Course();

    @Getter
    private List<Course> allCourses;

    @PostConstruct
    public void init(){
        loadAllCourses();
    }

    public void loadAllCourses(){
        allCourses = courseMapper.selectAll();
    }

    @Transactional
    public String createCourse(){
        courseMapper.insert(courseToCreate);
        return "/myBatis/courses?faces-redirect=true";
    }
}
