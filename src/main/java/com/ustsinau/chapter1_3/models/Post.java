package com.ustsinau.chapter1_3.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class Post {
    @SerializedName("ID")
    private  long id;
    @SerializedName("Title")
    private String title;
    @SerializedName("Content")
    private String content;
    @SerializedName("Labels list")
    private List<Label> labels;


    public Post(long id, String title, String content, List<Label> labels) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.labels = labels;
    }
    public Post(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }


    public long getId() {
        return id;
    }

    public  void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(title, post.title) && Objects.equals(content, post.content) && Objects.equals(labels, post.labels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, content, labels);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", labels=" + labels +
                '}';
    }
}
