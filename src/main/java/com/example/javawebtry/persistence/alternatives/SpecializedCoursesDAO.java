package com.example.javawebtry.persistence.alternatives;

import com.example.javawebtry.entities.Course;
import com.example.javawebtry.persistence.CoursesDAO;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@Specializes
@ApplicationScoped
public class SpecializedCoursesDAO extends CoursesDAO {

    @Inject
    private EntityManager em;
    private final Integer maxStudents = 3;

    @Override
    public List<Course> loadAll(){
        String jpql = "SELECT c FROM Course c WHERE SIZE(c.students) < :maxStudents";

        System.out.println("---------- Specialize: SpecializedCourseDAO loadAll");
        return em.createQuery(jpql, Course.class)
                .setParameter("maxStudents", maxStudents)
                .getResultList();
    }

}
