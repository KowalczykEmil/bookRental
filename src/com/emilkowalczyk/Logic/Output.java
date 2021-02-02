package com.emilkowalczyk.Logic;

import java.beans.XMLEncoder;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Output {
    private static List<RentXML> allRents = new ArrayList<>();
    private DataBaseMenager dbm = new DataBaseMenager();

    public void initializeRentList() {
        List<Rent> rents = dbm.getAllRents();
        for(Rent rent : rents) {
            Book book = dbm.getBook(rent.getBook_id());
            RentXML newObj = new RentXML(rent.getId(), rent.getBook_id(),
                    rent.getUser_id(), book.getTitle(), rent.getDate_of_rent(), rent.getDate_of_return());
            allRents.add(newObj);
        }
    }

    public Output() {
        initializeRentList();
    }

    //XML
    public static void eksportXML() throws IOException {
        eksportListaWypozyczenXML();
    }
    public static void eksportListaWypozyczenXML() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("RentList.xml");
        XMLEncoder encoderx = new XMLEncoder(fileOutputStream);
        encoderx.writeObject(allRents);
        encoderx.flush();
        encoderx.close();
        fileOutputStream.close();
    }
    /*public static void ustawieniaGuiXML() throws IOException{
        FileOutputStream fileOutputStream = new FileOutputStream("SettingsGUI.xml");
        XMLEncoder encoderx = new XMLEncoder(fileOutputStream);
        //encoderx.writeObject();
        encoderx.flush();
        encoderx.close();
        fileOutputStream.close();
    }

     */

    /**
     * Eksportuje Zdarzenia z programu do pliku .csv
     */

    //CSV
    /*static void ExportCSV(List<Event> base) throws IOException{
        File plik = new File("base.csv");
        try {
            plik.createNewFile();
            FileWriter stream = new FileWriter(plik);
            for(int i=0;i<Data.AllEvents.size();i++) {
                Event E = Data.AllEvents.get(i);
                String obj = E.getName()+";"+E.getDescription()+";"+E.getPlace()+";"+E.getStart()+";"+E.getEnd()+";"+E.getAlarm()+";"+E.getImportance()+"\n";
                stream.write(obj);
            }
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
