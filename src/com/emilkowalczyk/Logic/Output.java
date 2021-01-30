package com.emilkowalczyk.Logic;

import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Output {

    //XML
    public static void eksportXML() throws IOException {
        eksportListaWypozyczenXML();
        ustawieniaGuiXML();
    }
    public static void eksportListaWypozyczenXML() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("DBXMLListaWypozyczen.xml");
        XMLEncoder encoderx = new XMLEncoder(fileOutputStream);
        encoderx.writeObject();
        encoderx.flush();
        encoderx.close();
        fileOutputStream.close();
    }
    public static void ustawieniaGuiXML() throws IOException{
        FileOutputStream fileOutputStream = new FileOutputStream("SettingsGUI.xml");
        XMLEncoder encoderx = new XMLEncoder(fileOutputStream);
        //encoderx.writeObject(OknoGlowne.getZnacznikKolor());
        encoderx.flush();
        encoderx.close();
        fileOutputStream.close();
    }

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
