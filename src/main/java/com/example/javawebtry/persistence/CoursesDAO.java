package com.example.javawebtry.persistence;

import com.example.javawebtry.decorators.ICoursesDAO;
import com.example.javawebtry.entities.Course;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CoursesDAO implements ICoursesDAO {
    @Inject
    private EntityManager em;

    @Override
    public List<Course> loadAll(){
        return em.createNamedQuery("Course.findAll", Course.class).getResultList();
    }

    @Override
    public List<Course> loadAllStudentCourses(Integer studentId){
        String jpql = "SELECT c FROM Course c JOIN c.students s WHERE s.id = :studentId";
        return em.createQuery(jpql, Course.class)
                .setParameter("studentId", studentId)
                .getResultList();
    }

    @Override
    public List<Course> findCoursesNotEnrolledByStudent(Integer studentId) {
        return em.createQuery(
                        "SELECT c FROM Course c WHERE c NOT IN (SELECT c2 FROM Course c2 JOIN c2.students s WHERE s.id = :studentId)",
                        Course.class)
                .setParameter("studentId", studentId)
                .getResultList();
    }
    @Override
    public void persist(Course course){em.persist(course);}
    @Override
    public Course findOne(Integer id){return em.find(Course.class, id);}

    @Transactional
    @Override
    public Course update(Course course){ try{
        return em.merge(course);
    }catch (Exception ole){
        System.out.println("------ Optimistic lock exception occurred-----");
        return null;
        }

    }

}
