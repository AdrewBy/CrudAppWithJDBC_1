package com.ustsinau.chapter1_3.models;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Label {

    @SerializedName("ID")
    private  long id;
    @SerializedName("Name")
    private  String name;

    public Label() {
    }

    public Label(String name) {
        this.name=name;
    }

    public Label(long id, String name) {
        this.name=name;
        this.id=id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Label label = (Label) o;
        return id == label.id && Objects.equals(name, label.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Label{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
