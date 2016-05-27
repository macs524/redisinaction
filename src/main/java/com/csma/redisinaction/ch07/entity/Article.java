package com.csma.redisinaction.ch07.entity;

import java.io.Serializable;

/**
 * 文章实体类
 * Created by machangsheng on 16/5/26.
 */
public class Article implements Serializable {

    private Long id;
    private String author;
    private Long createdTime;
    private String content;
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", createdTime=" + createdTime +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
