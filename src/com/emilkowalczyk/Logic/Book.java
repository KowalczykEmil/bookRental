package com.emilkowalczyk.Logic;

import javax.xml.crypto.Data;
import java.util.Date;

public class Book {
    private String title;
    private Integer yearOfPublic;
    private Integer id;
    private Author author;
    private boolean rented;

    public Book(String title, Integer yearOfPublic, Integer id,
                String authorName, String authorLastName, Integer authorId, boolean rented) {
        this.title = title;
        this.yearOfPublic = yearOfPublic;
        this.id = id;
        this.author = new Author(authorName, authorLastName, authorId);
        this.rented = rented;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYearOfPublic() {
        return yearOfPublic;
    }

    public void setYearOfPublic(Integer yearOfPublic) {
        this.yearOfPublic = yearOfPublic;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", yearOfPublic=" + yearOfPublic +
                ", id=" + id +
                '}';
    }
}
