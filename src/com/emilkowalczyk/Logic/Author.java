package com.emilkowalczyk.Logic;

public class Author {
    private String name;
    private String lastName;
    private Integer ID;


    public Author(String name, String lastName, Integer ID) {
        this.name = name;
        this.lastName = lastName;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return name + " " + lastName;
    }

}
