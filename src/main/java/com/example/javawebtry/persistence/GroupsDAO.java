package com.example.javawebtry.persistence;

import com.example.javawebtry.entities.StudentGroup;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

@ApplicationScoped
public class GroupsDAO {
    @Inject
    private EntityManager em;

    public List<StudentGroup> loadAll(){ return em.createNamedQuery("StudentGroup.findAll", StudentGroup.class).getResultList();}

    public List<StudentGroup> loadAllByYear(Integer year){ return em.createNamedQuery("StudentGroup.findAllByYear", StudentGroup.class)
            .setParameter("year", year).
            getResultList();}
    public void persist(StudentGroup group){em.persist(group);}

    public StudentGroup findOne(Integer id){return em.find(StudentGroup.class, id);}

}
