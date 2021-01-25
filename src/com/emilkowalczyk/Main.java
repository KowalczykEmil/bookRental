package com.emilkowalczyk;

import com.emilkowalczyk.Logic.Interface;
import com.emilkowalczyk.Logic.Person;
import com.emilkowalczyk.Logic.PersonManager;

public class Main {

    public static void main(String[] args) {
        // Person Emil = new Person("Emil","Kowalczyk", 11, 8,1999, "Pomocy@gmail.com","9901515");
        // rsonManager personManager = new PersonManager();
        // personManager.addNewPerson(Emil);

        Interface i = new Interface();
        i.showMenu();
    }
}