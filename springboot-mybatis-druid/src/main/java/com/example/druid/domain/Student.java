package com.example.druid.domain;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 实体类
 *
 * @author kevin
 */
@Table(name = "student")
public class Student {
    @Id
    private Integer id;

    private String name;

    private Float chinese;

    private Float english;

    private Float math;

    private String gender;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return chinese
     */
    public Float getChinese() {
        return chinese;
    }

    /**
     * @param chinese
     */
    public void setChinese(Float chinese) {
        this.chinese = chinese;
    }

    /**
     * @return english
     */
    public Float getEnglish() {
        return english;
    }

    /**
     * @param english
     */
    public void setEnglish(Float english) {
        this.english = english;
    }

    /**
     * @return math
     */
    public Float getMath() {
        return math;
    }

    /**
     * @param math
     */
    public void setMath(Float math) {
        this.math = math;
    }

    /**
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
}