package com.emilkowalczyk.Logic;

import com.emilkowalczyk.GUI.GUI;

import javax.sound.midi.Soundbank;
import javax.swing.*;
import javax.xml.crypto.Data;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Interface {
    Scanner scanner = new Scanner(System.in);
    PersonManager pm;
    DataBaseMenager dataBaseMenager = new DataBaseMenager();
    boolean endStatus = false;
    boolean loginStatus = false;

    public void showMenu() {
        do {
            if(!loginStatus) setUpPersonManager();
            System.out.println("Witaj w naszej bibliotece! =) \n");
            System.out.println("Wybierz opcję:");
            System.out.println("1. Wybór uzytkownika");
            System.out.println("2. Nowy użytkownik");
            System.out.println("3. Wszystkie ksiazki");
            System.out.println("4. Wypożyczenie ksiazki");
            System.out.println("5. Wyświetl listę wypożyczeń");
            System.out.println("6. Wypożyczone książki");
            System.out.println("7. Zwrot książki");
            System.out.println("8. Tryb graficzny");
            System.out.println("9. Wyświetl użytkowników. ");
            System.out.println("0. Zakończ");

            int choice = Integer.valueOf(scanner.nextLine());

            switch(choice) {
                case 0 -> exit();
                case 1 -> chooseUser();
                case 2 -> addUser();
                case 3 -> {
                    System.out.println("Ksiazki dostepne w bibliotece:");
                    Integer numberOfBooks = displayAllBooks();
                    System.out.println("Liczba wszystkich ksiazek: " + numberOfBooks);
                }
                case 4 -> rentBook();
                case 5 -> showRents();
                case 6 -> showRentedBooks();
                case 7 -> returnBook();
                case 8 -> showGUI();
                case 9 -> showUsers();
            }
        }
        while (!endStatus);
    }

    private void setUpPersonManager() {
        pm = new PersonManager();
    }

    private void removeUser(){
        System.out.println("Podaj dane użytkownika, którego chcesz usunąć");
        System.out.println("Imię: ");
        String name = scanner.nextLine();
        System.out.println("Nazwisko: ");
        String lastName = scanner.nextLine();
        pm.removePerson(name, lastName);
    }

    private void addUser() {
        System.out.println("Podaj dane nowego uzytkownika:");
        System.out.println("Imie:");
        boolean status = false;
        String name = "";
        do {
            try{
                name = scanner.nextLine();
                status = true;
            }
            catch(java.util.InputMismatchException ex) {
                System.out.println("Podałeś złą wartość.");
                ex.printStackTrace();
            }
        }
        while (!status);

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

        Person person = new Person(-1, name, lastName, dayOfBirth, monthOfBirth, yearOfBirth, email, phoneNumber);

        pm.addNewPerson(person);

        System.out.println("Uzytkownik zostal pomyslnie dodany do bazy danych.");
    }

    private void showRents() {
        List<Rent> rents = dataBaseMenager.getAllRents();
        for(Rent rent : rents) {
            Book book = getBookById(rent.getBook_id());

            System.out.println("Tytul: " + book.getTitle() + ", UserID: " +rent.getUser_id() + ", data zwrotu: " + rent.getDate_of_return());
        }
    }

    private void chooseUser() {
        System.out.println("Wprowadź ID użytkownika:");
        Integer id = Integer.valueOf(scanner.nextLine());
        pm.login(id);
        if(pm.currentPerson == null) {
            System.out.println("Wystapil blad podczas wczytywania uzytkownika");
        }
        System.out.println("Użytkownik: " + pm.currentPerson.getName() + " " + pm.currentPerson.getLastName());
    }

    private Integer displayAllBooks() {
        List<Book> books = dataBaseMenager.getAllBooks();
        for(Book book : books) {
            boolean rented = book.isRented();
            String status;
            if(rented) status = "NIE";
            else status = "TAK";
            System.out.println(book.getId() + ". \t=== " +  book.getTitle() + "=== \n\tAutor: "
                    + book.getAuthor() + "\n\tRok wydania: " + book.getYearOfPublic() + "\n\tDostępna: "
                    + status + "\n");
        }
        return books.size();
    }

    private Book getBookById(Integer id) {
        Book book = dataBaseMenager.getBook(id);
        return book;
    }

    private void rentBook() {
        checkLogin();

        System.out.println("Książki dostępne w bibliotece:");
        displayAllBooks();
        System.out.println("Podaj id wypożyczanej książki:");

        Integer id = Integer.valueOf(scanner.nextLine());

        RentManager rm = new RentManager();
        boolean status = rm.rent(pm.currentPerson, id);

        Rent rent = dataBaseMenager.getRentInfo(id);
        String dateOfReturn = rent.getDate_of_return();

        if(!status) {
            System.out.println("Wybrana książka jest wypożyczona do " + dateOfReturn + "!\n");
            Book book = getBookById(id);
        }
        else {
            System.out.println("Wypożyczenie zostało zarejestrowane. Data zwrotu: " + dateOfReturn + ".\n");
        }
    }

    private void showRentedBooks() {
        checkLogin();

        List<Book> books = dataBaseMenager.getRentedBooks(pm.currentPerson.getId());

        if(books.isEmpty()) {
            System.out.println("Brak wypożyczonych książek.");
        } else {
            System.out.println("Wypożyczone książki: \n");
            for(Book book : books) {
                boolean rented = book.isRented();
                String status;
                if(rented) status = "NIE";
                else status = "TAK";
                Rent rent = dataBaseMenager.getRentInfo(book.getId());
                System.out.println(book.getId() + ". \t=== " +  book.getTitle() + "=== \n\t" +
                        "Autor: " + book.getAuthor() + "\n\t" +
                        "Rok wydania: " + book.getYearOfPublic() + "\n\t" +
                        "Data zwrotu: " + rent.getDate_of_return() + "\n");
            }
        }
    }

    private void returnBook() {
        checkLogin();
        showRentedBooks();
        System.out.println("Podaj ID zwracanej książki:");
        Integer id = Integer.valueOf(scanner.nextLine());

        if(dataBaseMenager.returnBook(id)) {
            System.out.println("Zwrot został zarejestrowany pomyślnie.\n");
        }
        else {
            System.out.println("Książka o podanym ID nie jest wypożyczona.\n");
        }
    }

    void checkLogin() {
        if(!loginStatus) {
            chooseUser();
            loginStatus = true;
        }
    }

    void showGUI(){
        GUI gui = new GUI();
        gui.setVisible(true);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void showUsers() {
            List<Person> users = dataBaseMenager.getAllUsers();
            for(Person user : users) {
                System.out.println(user.getId() + ". " + user.getName() + " " + user.getLastName());
            }
    }

    private void exit() {
        endStatus = true;
    }
}
