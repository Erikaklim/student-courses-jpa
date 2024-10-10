package com.example.javawebtry.persistence;

import com.example.javawebtry.entities.Course;
import com.example.javawebtry.entities.Student;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class StudentsDAO {

    @Inject
    private EntityManager em;

    public List<Student> loadAll(){
        return em.createNamedQuery("Student.findAll", Student.class).getResultList();
    }

    public List<Student> loadAllByGroupId(Integer groupId){
        return em.createNamedQuery("Student.findAllByGroupId", Student.class)
                .setParameter("groupId", groupId)
                .getResultList();
    }

    public List<Student> loadStudentsNotInCourse(Integer courseId){
        return em.createNamedQuery("Student.findStudentsNotInCourse", Student.class)
                .setParameter("course", em.find(Course.class, courseId))
                .getResultList();
    }

    public List<Student> loadCourseStudents(Integer courseId){
        return em.createNamedQuery("Student.findAllByCourseId", Student.class)
                .setParameter("courseId", courseId)
                .getResultList();
    }

    public void persist(Student student) {
        em.persist(student);
    }

    public Student findOne(Integer id) {
        return em.find(Student.class, id);
    }

    public Student update(Student student) {
        return em.merge(student);
    }

    public void refresh(Student student){
        em.refresh(student);
    }


}
