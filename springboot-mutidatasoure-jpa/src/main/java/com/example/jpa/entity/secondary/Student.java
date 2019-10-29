package com.example.jpa.entity.secondary;

import javax.persistence.*;

/**
 * student
 *
 * @author star
 **/
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", columnDefinition = "nvarchar(20)")
    private String name;

    @Column(name = "chinese", columnDefinition = "int default 0")
    private Integer chinese;

    @Column(name = "english", columnDefinition = "int default 0")
    private Integer english;

    @Column(name = "math", columnDefinition = "int default 0")
    private Integer math;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getChinese() {
        return chinese;
    }

    public void setChinese(Integer chinese) {
        this.chinese = chinese;
    }

    public Integer getEnglish() {
        return english;
    }

    public void setEnglish(Integer english) {
        this.english = english;
    }

    public Integer getMath() {
        return math;
    }

    public void setMath(Integer math) {
        this.math = math;
    }
}
