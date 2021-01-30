package com.emilkowalczyk.Logic;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
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

    public Book getBook(Integer id) {
        if(!connectStatus) connect();
        String strSelect = "select b.id, b.title, b.author_id, b.year_of_public, b.rented," +
                " a.id as author_id, a.name, a.last_name from books as b, authors as a, rents as r WHERE b.author_id = a.id " +
                "AND r.book_id = b.id AND b.id = " + id;

        Book book = null;
        try ( ResultSet rset = stmt.executeQuery(strSelect) )
        {
            while(rset.next()) {
                String title = rset.getString("title");
                Integer yearOfPublic = rset.getInt("year_of_public");
                Integer book_id = rset.getInt("id");
                String authorName = rset.getString("name");
                String authorLastName = rset.getString("last_name");
                Integer authorId = rset.getInt("author_id");
                boolean rented = rset.getBoolean("rented");

                book = new Book(title, yearOfPublic, book_id, authorName, authorLastName, authorId, rented);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        return book;
    }

    public List<Book> getAllBooks() {
        if(!connectStatus) connect();
        String strSelect = "select b.id, b.title, b.author_id, b.year_of_public, b.rented," +
                " a.id as author_id, a.name, a.last_name from books as b, authors as a WHERE b.author_id = a.id ";
        List<Book> books = new ArrayList<>();
        try ( ResultSet rset = stmt.executeQuery(strSelect) )
        {
            while(rset.next()) {
                String title = rset.getString("title");
                Integer yearOfPublic = rset.getInt("year_of_public");
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

    public void removeUser(String name, String lastName) {
        String insertQuery = "DELETE FROM users WHERE name = " + name + " AND lastName = " + lastName +";";
        executeQuery(insertQuery);
    }

    public List<Person> getAllUsers() {
        if(!connectStatus) connect();
        String selectQuery = "select * from users";
        List<Person> users = new ArrayList<>();
        try ( ResultSet rset = stmt.executeQuery(selectQuery) )
        {
            while(rset.next()) {
                Integer user_id = rset.getInt("id");
                String name = rset.getString("name");
                // zmienic to na normalny typ Date w bazie i w Person :)
                String lastName = rset.getString("lastName");
                Integer dayOfBirth = rset.getInt("dayOfBirth");
                Integer monthOfBirth = rset.getInt("monthOfBirth");
                Integer yearOfBirth = rset.getInt("yearOfBirth");
                String email = rset.getString("email");
                String phoneNumber = rset.getString("phoneNumber");
                Person newPerson = new Person(user_id, name, lastName, dayOfBirth, monthOfBirth, yearOfBirth, email, phoneNumber);

                users.add(newPerson);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return users;
    }

    public boolean rent(Person person, Integer book_id, Integer borrowingPeriod) {
        if(!connectStatus) connect();
        boolean status = getRentStatus(book_id);
        if(!status) {
            String insertQuery = "INSERT INTO rents VALUES (NULL, " + person.getId() + ", " + book_id
                    + ", now(), " + " DATE_ADD(now(), INTERVAL " + borrowingPeriod + " DAY));";
            executeQuery(insertQuery);
            String updateQuery = "UPDATE books SET rented = TRUE WHERE id = " + book_id + ";";
            executeQuery(updateQuery);
            return true;
        }
        return false;
    }

    private boolean getRentStatus(Integer id) {
        String strSelect = "SELECT rented FROM books WHERE id = " + id + ";";
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
        return status;
    }

    public List<Rent> getAllRents() {
        if(!connectStatus) connect();
        String strSelect = "SELECT * FROM rents;";
        List<Rent> rents = new ArrayList<>();
        try ( ResultSet rset = stmt.executeQuery(strSelect) )
        {
            while(rset.next()) {
                Integer id = rset.getInt("id");
                Integer book_id = rset.getInt("book_id");
                Integer user_id = rset.getInt("user_id");
                Date date_of_rent = rset.getDate("date_of_rent");
                Date date_of_return = rset.getDate("date_of_return");

                Rent newRent = new Rent(id, book_id, user_id, date_of_rent, date_of_return);
                rents.add(newRent);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        return rents;
    }

    public Rent getRentInfo(Integer book_id) {
        if(!connectStatus) connect();
        String selectQuery = "select * from rents where book_id = " + book_id + ";";
        Rent rent = null;
        try ( ResultSet rset = stmt.executeQuery(selectQuery) )
        {
            while(rset.next()) {
                Integer id = rset.getInt("id");
                Integer book_Id_To_Fetch = rset.getInt("book_id");
                Integer user_id = rset.getInt("user_id");
                Date date_of_rent = rset.getDate("date_of_rent");
                Date date_of_return = rset.getDate("date_of_return");
                rent = new Rent(id, book_Id_To_Fetch, user_id, date_of_rent, date_of_return );
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return rent;
    }

    public Person getUserById(Integer id) {
        if(!connectStatus) connect();
        String selectQuery = "select * from users where id = " + id + ";";
        Person person = null;
        try ( ResultSet rset = stmt.executeQuery(selectQuery) )
        {
            while(rset.next()) {
                Integer user_id = rset.getInt("id");
                String name = rset.getString("name");
                // zmienic to na normalny typ Date w bazie i w Person :)
                String lastName = rset.getString("lastName");
                Integer dayOfBirth = rset.getInt("dayOfBirth");
                Integer monthOfBirth = rset.getInt("monthOfBirth");
                Integer yearOfBirth = rset.getInt("yearOfBirth");
                String email = rset.getString("email");
                String phoneNumber = rset.getString("phoneNumber");
                person = new Person(user_id, name, lastName, dayOfBirth, monthOfBirth, yearOfBirth, email, phoneNumber);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return person;
    }

    public List<Book> getRentedBooks(Integer user_id) {
        if(!connectStatus) connect();
        String strSelect = "select b.id, b.title, b.author_id, b.year_of_public, b.rented," +
                "a.name, a.last_name from books as b, authors as a, rents as r, users as u " +
                "WHERE b.author_id = a.id AND r.user_id = u.id AND r.book_id = b.id AND u.id = " + user_id +";";
        List<Book> books = new ArrayList<>();
        try ( ResultSet rset = stmt.executeQuery(strSelect) )
        {
            while(rset.next()) {
                String title = rset.getString("title");
                Integer yearOfPublic = rset.getInt("year_of_public");
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

    public boolean returnBook(Integer id) {
        if(getRentStatus(id)) {
            String deleteQuery = "DELETE FROM rents WHERE book_id = " + id + ";";
            executeQuery(deleteQuery);
            String updateQuery = "UPDATE books SET rented = FALSE WHERE id = " + id + ";";
            executeQuery(updateQuery);
            return true;
        }
        return false;
    }




}
