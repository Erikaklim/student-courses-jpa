package com.example.javawebtry.entities;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.List;

@NamedQueries({
        @NamedQuery(name="StudentGroup.findAll", query = "select g from StudentGroup as g"),
        @NamedQuery(name="StudentGroup.findAllByYear", query = "select g from StudentGroup g where g.year = :year")
})

@Entity
@Getter @Setter
public class StudentGroup {
    @GeneratedValue
    @Id
    private Integer id;

    @Basic
    private Integer year;

    @Basic
    private Integer groupNumber;

    @OneToMany(mappedBy = "group")
    private List<Student> students;

    public StudentGroup(){

    }

}
