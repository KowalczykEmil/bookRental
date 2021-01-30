package com.emilkowalczyk.Logic;
import java.time.format.DateTimeFormatter;

public class RentManager {
    private int borrowingPeriod = 14; // in days
    public boolean rent(Person person, Integer book_id) {
        DataBaseMenager dbm = new DataBaseMenager();
        return dbm.rent(person, book_id, borrowingPeriod);
    }
}
