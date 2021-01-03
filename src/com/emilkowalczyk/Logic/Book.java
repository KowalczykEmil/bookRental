package com.emilkowalczyk.Logic;

public class Book {
    private String title;
    private Integer yearOfPublic;
    private Integer id;

    public Book(String title, Integer yearOfPublic, Integer id) {
        this.title = title;
        this.yearOfPublic = yearOfPublic;
        this.id = id;
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
