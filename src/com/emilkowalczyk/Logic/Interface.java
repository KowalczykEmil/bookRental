package com.emilkowalczyk.Logic;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Interface {
    Scanner scanner = new Scanner(System.in);
    PersonManager pm;
    DataBaseMenager dataBaseMenager = new DataBaseMenager();

    public void showMenu() {
        setUpPersonManager();
        System.out.println("Wybierz opcję:\n");
        System.out.println("1. Wybierz uzytkownika\n");
        System.out.println("2. Dodaj nowego użytkownika\n");
        System.out.println("3. Wyswietl wszystkie ksiazki\n");
        System.out.println("4. Wypozycz ksiazke\n");

        int choice = scanner.nextInt();

        if (choice == 1) {
            chooseUser();
        }

        if(choice == 2) {
            addUser();
        }

        if(choice == 3) {
            displayAllBooks();
        }

        if (choice == 4) {
            rentBook();
        }
    }

    private void setUpPersonManager() {
        pm = new PersonManager();
    }

    private void addUser() {
        System.out.println("Podaj dane nowego uzytkownika:");
        System.out.println("Imie:");
        String name = scanner.nextLine();

        System.out.println("Nazwisko:");
        String lastName = scanner.nextLine();

        System.out.println("Podaj date urodzenia");
        System.out.println("Dzien:");
        int dayOfBirth= Integer.valueOf(scanner.nextLine());

        System.out.println("Miesiac:");
        int monthOfBirth= Integer.valueOf(scanner.nextLine());

        System.out.println("Rok:");
        int yearOfBirth= Integer.valueOf(scanner.nextLine());

        System.out.println("Adres email:");
        String email = scanner.nextLine();

        System.out.println("Numer telefonu:");
        String phoneNumber = scanner.nextLine();

        Person person = new Person(name, lastName, dayOfBirth, monthOfBirth, yearOfBirth, email, phoneNumber);

        pm.addNewPerson(person);
    }

    public void login() {
        System.out.println("Podaj swoje ID");
        Integer id = scanner.nextInt();
        pm.currentPerson = dataBaseMenager.getUserById(id);
    }

    public void chooseUser() {
        System.out.println("Podaj id uzytkownika");
        Integer id = scanner.nextInt();
        pm.login(id);
        if(pm.currentPerson == null) {
            System.out.println("Wystapil blad podczas wczytywania uzytkownika");
        }
        System.out.println("Witaj: " + pm.currentPerson.getName() + " " + pm.currentPerson.getLastName() + "!");
    }

    private void displayAllBooks() {
        List<Book> books = dataBaseMenager.getAllBooks();

        System.out.println("Ksiazki dostepne w bibliotece:");
        for(Book book : books) {
            boolean rented = book.isRented();
            String status;
            if(rented) status = "TAK";
            else status = "NIE";
            System.out.println(book.getId() + ". tytul: " + book.getTitle() + ", data wydania: " + book.getYearOfPublic() + ", wypozyczona: "
                    + status);
        }
        System.out.println("Liczba wszystkcich ksiazek = " + books.size());
    }

    void rentBook() {
        displayAllBooks();
        System.out.println("Podaj id ksiazki, ktora chcesz wypozyczyc:");
        Integer id = scanner.nextInt();

        Rent rent = new Rent();
        rent.rent(pm.currentPerson, id);

    }
}
