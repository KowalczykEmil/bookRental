package com.emilkowalczyk;

import com.emilkowalczyk.Logic.Person;

public class Main {

    public static void main(String[] args) {
        Person person = new Person("Emil","Kowalczyk", 11, 8,1999, "Pomocy@gmail.com",9901515);

        System.out.println(person.toString());
    }
}