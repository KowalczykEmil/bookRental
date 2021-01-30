package com.emilkowalczyk.Logic;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Rent {
    private Integer id;
    private Integer book_id;
    private Integer user_id;
    private Date date_of_rent;
    private Date date_of_return;

    public Rent(Integer id, Integer book_id, Integer user_id, Date date_of_rent, Date date_of_return) {
        this.id = id;
        this.book_id = book_id;
        this.user_id = user_id;
        this.date_of_rent = date_of_rent;
        this.date_of_return = date_of_return;
    }

    public Integer getId() {
        return id;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public String getDate_of_rent() {
        return formatData(date_of_rent);
    }

    public String getDate_of_return() {
        return formatData(date_of_return);
    }

    private String formatData(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String parsedDate = format.format(date);
        return parsedDate;
    }
}

