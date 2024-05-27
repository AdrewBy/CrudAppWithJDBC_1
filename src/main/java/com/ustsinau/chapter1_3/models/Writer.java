package com.ustsinau.chapter1_3.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;



public class Writer {

    @SerializedName("ID")
    private  long id;
    @SerializedName("FirstName")
    private String firstName;
    @SerializedName("LastName")
    private String lastName;
    @SerializedName("Posts list")
    private List<Post> posts;
    @SerializedName("Status")
    Status status ;

    public Writer() {
    }

    public Writer(long id, String firstName, String lastName, Status status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }



    public Writer(long id, String firstName, String lastName, List<Post> posts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
    }

    public  long getId() {
        return id;
    }

    public  void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Writer writer = (Writer) o;
        return id == writer.id && Objects.equals(firstName, writer.firstName)
                && Objects.equals(lastName, writer.lastName)
                && Objects.equals(posts, writer.posts) && status == writer.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, posts, status);
    }

    @Override
    public String toString() {
        return "Writer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", posts=" + posts +
                ", status=" + status +
                '}';
    }
}
