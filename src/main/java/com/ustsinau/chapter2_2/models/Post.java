package com.ustsinau.chapter2_2.models;



import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Post {

    private long id;

    private String content;

    private List<Label> labels;

    private Date created;

    private Date updated;
    PostStatus postStatus;


    public Post(long id, String content, PostStatus postStatus, Date updated, List<Label> labels) {
        this.id = id;
        this.content = content;
        this.postStatus = postStatus;
        this.labels = labels;
        this.updated = updated;
    }

    public Post(String content, Date created) {
        this.content = content;
        this.created = created;
    }

    public Post() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getCreated() {
        return created = new Date();
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated = new Date();
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public PostStatus getPostStatus() {
        return PostStatus.UNDER_REVIEW;
    }

    public void setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id && Objects.equals(content, post.content) && Objects.equals(labels, post.labels) && Objects.equals(created, post.created) && Objects.equals(updated, post.updated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, labels, created, updated);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", created= " + created +
                ", updated= " + updated +
                ", postStatus=" + postStatus +
                ",\n        labels:\n           " + labels +
                '}';
    }
}
