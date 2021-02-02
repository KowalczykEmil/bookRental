package com.emilkowalczyk.Logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonManager {
    public Person currentPerson;
    DataBaseMenager dbm = new DataBaseMenager();
    List<Person> users = new ArrayList<>();

    public void addNewPerson(Person p) {
        users.add(p);
        DataBaseMenager dbm = new DataBaseMenager();
        dbm.addUser(p);
    }

    public void removePerson(String name, String lastName) {
        for(Person user : users) {
            if (user.name.equals(name) && user.lastName.equals(lastName)) {
                users.remove(user);
                DataBaseMenager dbm = new DataBaseMenager();
                dbm.removeUser(name, lastName);
                break;
            }
        }
    }

    public void login(Integer id) {
        currentPerson = dbm.getUserById(id);
    }

    public List<Book> checkUserRents(Integer id) {
        List<Book> books = dbm.getRentedBooks(id);
        if(!books.isEmpty()) {
            return books;
        }
        return null;
    }

    public List<Integer> getBooksToReturnShortly() {
        List<Book> books = checkUserRents(currentPerson.getId());
        List<Integer> toReturn = null;

        if (books != null) {
            toReturn = new ArrayList<>();
            Date date = java.util.Calendar.getInstance().getTime();

            for (Book book : books) {
                Rent rent = dbm.getRentInfo(book.getId());
                Date d2 = null;
                try {
                    d2 = new SimpleDateFormat("dd-MM-yyyy").parse(rent.getDate_of_return());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long diff = (d2.getTime() - date.getTime()) / (1000 * 60 * 60 * 24);
                // d2 - data zwrotu
                // date - aktualna data pobierana z systemu

                if (diff <= 15) {
                    toReturn.add(rent.getBook_id());
                }
            }
        }

        return toReturn;
    }
}
