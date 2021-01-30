package com.emilkowalczyk.Logic;

import java.util.Date;

public class RentXML {
    private Integer id;
    private Integer book_id;
    private Integer user_id;
    private String title;
    private Date date_of_rent;
    private Date date_of_return;


    public RentXML(Integer id, Integer book_id, Integer user_id, String title, Date date_of_rent, Date date_of_return) {
        this.id = id;
        this.book_id = book_id;
        this.user_id = user_id;
        this.title = title;
        this.date_of_rent = date_of_rent;
        this.date_of_return = date_of_return;
    }
}
