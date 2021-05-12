package com.test.cabmanagement.bean;

public class City {
    private Integer id;
    private String name;
    private static Integer IDCounter = 1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public City(String name) {
        this.id = IDCounter++;
        this.name = name;
    }

    @Override
    public String toString() {
        return "City [id=" + id + ", name=" + name + "]";
    }

}
