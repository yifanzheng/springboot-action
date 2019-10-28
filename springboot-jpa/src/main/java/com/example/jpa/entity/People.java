package com.example.jpa.entity;

import javax.persistence.*;

/**
 * People
 *
 * @author star
 */
@Entity
@Table(name = "people")
public class People extends AbstractAuditingEntity {

    private static final long serialVersionUID = -2189163594057781698L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "nvarchar(20)")
    private String name;

    @Column(name = "age", columnDefinition = "int")
    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
