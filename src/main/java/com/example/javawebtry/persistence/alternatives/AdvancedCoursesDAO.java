package com.example.javawebtry.persistence.alternatives;

import com.example.javawebtry.decorators.ICoursesDAO;
import com.example.javawebtry.entities.Course;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Alternative
@ApplicationScoped
public class AdvancedCoursesDAO implements ICoursesDAO {

    @Inject
    private EntityManager em;

    @Override
    public List<Course> loadAll() {
//        String jpql = "SELECT c  FROM Course c ORDER BY LOWER(c.title)";
//        return em.createQuery(jpql, Course.class).getResultList();
        System.out.println("-------- Alternative: AdvancedCourseDAO loadAll");

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Course> cq = cb.createQuery(Course.class);
        Root<Course> course = cq.from(Course.class);
        cq.select(course).orderBy(cb.asc(cb.lower(course.get("title"))));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public List<Course> loadAllStudentCourses(Integer studentId){
        String jpql = "SELECT c FROM Course c JOIN c.students s WHERE s.id = :studentId";
        System.out.println("----- Specialize: SpecializedCourseDAO loadAll");
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

    @Override
    //void
    public Course update(Course course){return em.merge(course);}

}
