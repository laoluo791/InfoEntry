package com.ie.pojo;

public class User {
    private Integer id;

    private Integer no;

    private String name;

    public User(Integer id, Integer no, String name) {
        this.id = id;
        this.no = no;
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", no=" + no +
                ", name='" + name + '\'' +
                '}';
    }

    public User() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}