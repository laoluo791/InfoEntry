package com.ie.pojo;

public class Content {
    private Integer id;

    private Integer type;

    private Integer user;

    private String content;

    public Content(Integer id, Integer type, Integer user, String content) {
        this.id = id;
        this.type = type;
        this.user = user;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", type=" + type +
                ", user=" + user +
                ", content='" + content + '\'' +
                '}';
    }

    public Content() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}