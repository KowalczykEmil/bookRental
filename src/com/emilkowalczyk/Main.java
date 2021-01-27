package com.emilkowalczyk;

import com.emilkowalczyk.Logic.DataBaseMenager;
import com.emilkowalczyk.Logic.Interface;
import com.emilkowalczyk.Logic.Person;
import com.emilkowalczyk.Logic.PersonManager;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.sql.Connection;

public class Main {

    public static void main(String[] args) {
        // Person Emil = new Person("Emil","Kowalczyk", 11, 8,1999, "Pomocy@gmail.com","9901515");
        // rsonManager personManager = new PersonManager();
        // personManager.addNewPerson(Emil);

        String command = "E:\\Book_Rental\\mysql\\mysql-8.0.23-winx64\\bin\\mysqld.exe";
        try
        {
            Process process = Runtime.getRuntime().exec(command);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        Interface i = new Interface();
        i.showMenu();
    }
}