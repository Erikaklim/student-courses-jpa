package com.example.javawebtry.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@NamedQueries({
        @NamedQuery(name="Student.findAll", query = "select s from Student as s"),
        @NamedQuery(name = "Student.findAllByGroupId", query = "SELECT s FROM Student s WHERE s.group.id = :groupId"),
        @NamedQuery(name="Student.findStudentsNotInCourse", query = "select s from Student s where :course not member of s.courses"),
        @NamedQuery(name = "Student.findAllByCourseId",
                query = "SELECT s FROM Student s JOIN s.courses c WHERE c.id = :courseId")
})


@Entity
@EqualsAndHashCode
@Setter @Getter
public class Student {

    @Id
    @GeneratedValue
    private Integer id;

    @Basic(optional = false)
    private String name;

    @Basic(optional = false)
    private String lastName;

    @ManyToMany
    @JoinTable(
            name= "students_course",
            joinColumns = @JoinColumn(name="students_id"),
            inverseJoinColumns = @JoinColumn(name="courses_id")
    )
    private List<Course> courses;

    @ManyToOne
    private StudentGroup group;

    public Student(){

    }

}
