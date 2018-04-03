package com.carrfour;

import java.io.Serializable;

public class Student implements Serializable {

    private int idStudent;
    private int universityIdUniversity;
    private String name;


    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public int getUniversityIdUniversity() {
        return universityIdUniversity;
    }

    public void setUniversityIdUniversity(int universityIdUniversity) {
        this.universityIdUniversity = universityIdUniversity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "idStudent=" + idStudent +
                ", idUniversity=" + universityIdUniversity +
                ", name='" + name + '\'' +
                '}';
    }
}
