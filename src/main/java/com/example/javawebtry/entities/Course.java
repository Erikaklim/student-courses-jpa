package com.example.javawebtry.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity

@NamedQueries({
    @NamedQuery(name="Course.findAll", query = "select c from Course as c")
})

@Getter @Setter
public class Course {
    @Id
    @GeneratedValue
    private Integer id;

    @Basic(optional = false)
    private String title;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students;

    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer version;

    public Course(){

    }

}
