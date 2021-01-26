package com.emilkowalczyk.Logic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Rent {
    public boolean rent(Person person, Integer book_id) {
        DataBaseMenager dbm = new DataBaseMenager();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        return dbm.rent(person, book_id, now);
    }
}
