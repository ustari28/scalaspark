package com.carrfour;

import java.io.Serializable;

public class University implements Serializable {

    private int idUniversity;
    private String name;
    private Long numStudents;
    private Long yearFounded;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getNumStudents() {
        return numStudents;
    }

    public void setNumStudents(Long numStudents) {
        this.numStudents = numStudents;
    }

    public Long getYearFounded() {
        return yearFounded;
    }

    public void setYearFounded(Long yearFounded) {
        this.yearFounded = yearFounded;
    }

    public int getIdUniversity() {
        return idUniversity;
    }

    public void setIdUniversity(int idUniversity) {
        this.idUniversity = idUniversity;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(idUniversity).append("|")
                .append(name).append("|")
                .append(numStudents).append("|")
                .append(yearFounded).toString();
    }


}
