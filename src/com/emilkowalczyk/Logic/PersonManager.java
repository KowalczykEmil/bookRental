package com.emilkowalczyk.Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PersonManager {
    Interface interf = new Interface();
    Person currentPerson;
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

    public void logout() {

    }





}
