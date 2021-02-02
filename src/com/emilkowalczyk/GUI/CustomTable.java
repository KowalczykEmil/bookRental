package com.emilkowalczyk.GUI;

import com.emilkowalczyk.Logic.Book;
import com.emilkowalczyk.Logic.DataBaseMenager;
import com.emilkowalczyk.Logic.Person;
import com.emilkowalczyk.Logic.Rent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CustomTable {
    private DataBaseMenager dataBaseMenager = new DataBaseMenager();
    private JTable table;
    private JScrollPane sp;
    private DefaultTableModel dtm;

    CustomTable(Integer type) {
        initializeBaseTable();
        setModel(type);
        initializeScrollPane();
    }

    private void initializeBaseTable() {
        table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(450,63));
        table.setFillsViewportHeight(true);
    }

    private void initializeScrollPane() {
        sp = new JScrollPane(table);
        sp.setBounds(500, 160, 650, 300);
        sp.setFont(new Font(Font.SERIF, Font.BOLD, 20));
    }

    private void setModel(Integer model) {
        dtm = new DefaultTableModel(0,0);

        switch (model) {
            case 1 -> setUsersModel();
            case 2 -> setAllBooksModel();
            case 3 -> setAllRentsModel();
        }
    }

    private void setUsersModel() {
        String columns[] = new String[] { "ID", "Imię", "Nazwisko" };
        dtm.setColumnIdentifiers(columns);
        table.setModel(dtm);

        List<Person> users = dataBaseMenager.getAllUsers();

        for(Person user : users) {
            dtm.addRow(new Object[] { user.getId(), user.getName(), user.getLastName() });
        }
    }

    private void setAllBooksModel() {
        String columns[] = new String[] { "ID", "Tytuł", "Autor", "Rok wydania", "Dostępność" };
        dtm.setColumnIdentifiers(columns);
        table.setModel(dtm);

        List<Book> books = dataBaseMenager.getAllBooks();
        for(Book book : books) {
            boolean rented = book.isRented();
            String status;
            if(rented) status = "NIE";
            else status = "TAK";

            dtm.addRow(new Object[] { book.getId(), book.getTitle(), book.getAuthor(), book.getYearOfPublic(), status });
        }
    }

    private void setAllRentsModel() {
        String columns[] = new String[] { "Tytuł", "UserID", "Data zwrotu" };
        dtm.setColumnIdentifiers(columns);
        table.setModel(dtm);

        List<Rent> rents = dataBaseMenager.getAllRents();
        for(Rent rent : rents) {
            Book book = dataBaseMenager.getBook(rent.getBook_id());
            dtm.addRow(new Object[] { book.getTitle(), rent.getUser_id(), rent.getDate_of_return() });
        }
    }

    public JTable getTable() {
        return table;
    }

    public JScrollPane getSp() {
        return sp;
    }
}