package com.emilkowalczyk.Logic;
import javax.xml.crypto.Data;
import javax.xml.transform.Result;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataBaseMenager  {

    private Connection conn;
    private Statement stmt;
    private boolean connectStatus = false;


    public void connect() {
        try{
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/bookrental?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "root", "Haslo123");

            stmt = conn.createStatement();
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void executeQuery(String query) {
        if(!connectStatus) connect();
        System.out.println(query);
        try{
            stmt.executeUpdate(query);
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<Book> getAllBooks() {
        if(!connectStatus) connect();
        String strSelect = "select b.id, b.title, b.author_id, b.yearOfPublic, b.rented," +
                " a.id as author_id, a.name, a.last_name from books as b, authors as a;";
        List<Book> books = new ArrayList<>();
        try ( ResultSet rset = stmt.executeQuery(strSelect) )
        {
            while(rset.next()) {
                String title = rset.getString("title");
                Integer yearOfPublic = rset.getInt("yearOfPublic");
                Integer id = rset.getInt("id");
                String authorName = rset.getString("name");
                String authorLastName = rset.getString("last_name");
                Integer authorId = rset.getInt("author_id");
                boolean rented = rset.getBoolean("rented");

                Book newBook = new Book(title, yearOfPublic, id, authorName, authorLastName, authorId, rented);
                books.add(newBook);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        return books;
    }

    public void addUser(Person person) {
        String insertQuery = "insert into users values (NULL, '" + person.getName() + "', '" + person.getLastName() + "', "
                + person.getDayOfBirth() + ", " + person.getMonthOfBirth() + ", " + person.getYearOfBirth() + ", '"
                + person.getEmail() + "', '" + person.getPhoneNumber() + "');";
        executeQuery(insertQuery);
    }

    public void removeUser(Person person) {

    }

    public boolean rent(Person person, Integer book_id, LocalDateTime dayOfRent) {
        if(!connectStatus) connect();
        String strSelect = "select reneted from books where id = " + book_id + ";";
        boolean status = false;
        try ( ResultSet rset = stmt.executeQuery(strSelect) ) {
            while(rset.next()) {
                boolean rented = rset.getBoolean("rented");
                if (rented) status = true; // jesli ksiazka jest wypozyczona
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        if(!status) {
            String insertQuery = "insert into rents values (NULL, " + person.getId() + ", " + book_id + "TO_DATE('"
                    + dayOfRent + "', 'DD/MM/YYYY'), (SELECT DATEADD(week, 2, '"+  dayOfRent + "2017/08/25'));";
            executeQuery(insertQuery);
            return true;
        }
        return false;
    }

    public Person getUserById(Integer id) {
        if(!connectStatus) connect();
        String selectQuery = "select * from users where id = " + id + ";";
        Person person = null;
        try ( ResultSet rset = stmt.executeQuery(selectQuery) )
        {
            while(rset.next()) {
                String name = rset.getString("name");
                // zmienic to na normalny typ Date w bazie i w Person :)
                String lastName = rset.getString("lastName");
                Integer dayOfBirth = rset.getInt("dayOfBirth");
                Integer monthOfBirth = rset.getInt("monthOfBirth");
                Integer yearOfBirth = rset.getInt("yearOfBirth");
                String email = rset.getString("email");
                String phoneNumber = rset.getString("phoneNumber");
                person = new Person(name, lastName, dayOfBirth, monthOfBirth, yearOfBirth, email, phoneNumber);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return person;
    }




}
