package com.newegg.core.service.domain;

public class Enployee {

    private Integer id; // 员工唯一标识
    private String name; // 员工中文名
    private String englishName; // 员工英文名
    private String department; // 员工所在部门

    public Enployee() {
    }

    public Enployee(Integer id, String name, String englishName, String department) {
        this.id = id;
        this.name = name;
        this.englishName = englishName;
        this.department = department;
    }

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

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


}
