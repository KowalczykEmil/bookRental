package com.emilkowalczyk.GUI;

import com.emilkowalczyk.Logic.DataBaseMenager;
import com.emilkowalczyk.Logic.Person;
import com.emilkowalczyk.Logic.PersonManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class GUI extends JFrame implements ActionListener {
        JMenuBar menuBar;
        JMenu menuPlik, menuKalendarz, menuWyglad, menuPomoc, menuMotywy;
        JMenuItem mXML, mCSV, mWyjscie,mWyswietlKalendarz, mWybierzKolorCzcionki, mMetal, mNimbus, mWindows, moProgramie;
        JLabel header, lMenu, wpiszId, jPodajDane, jImie, jNazwisko,jEmail,jTelefon, jDzien, jMiesiac, jRok, lZalogowano, lDodano;
        JButton lWyswietlUzytkownikow, lwybierz, lStworz, lWyswietlKsiazki, lWypozycz, lOddaj, lWyswietlWypoz, bCreate, bZaloguj;
        JTextField Wybierz, tImie, tNazwisko, tEmail, tTelefon, tDzien, tMiesiac, tRok;
        ImageIcon icon;
        int id, dzien, miesiac, rok;
        String imie, nazwisko, email, telefon;

        PersonManager pm;
        DataBaseMenager dataBaseMenager = new DataBaseMenager();
        boolean endStatus = false;
        boolean loginStatus = false;
        private void setUpPersonManager() {
        pm = new PersonManager();
    }

        public GUI(){
            setSize(1250,720);
            setTitle("Biblioteka - Kowalczyk Emil, Świąder Dominik");
            setLayout(null);
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                SwingUtilities.updateComponentTreeUI(this);
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (InstantiationException instantiationException) {
                instantiationException.printStackTrace();
            } catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            } catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
                unsupportedLookAndFeelException.printStackTrace();
            }

            if(!loginStatus) setUpPersonManager();

            icon = new ImageIcon("res/LibraryIcon.png");
            setIconImage(icon.getImage());

            menuBar = new JMenuBar();                                       //  Pasek narzędzi

            menuPlik = new JMenu("Plik");                                // Plik
            mXML = new JMenuItem("Eksportuj do XML");
            mXML.addActionListener(this);
            mXML.setAccelerator(KeyStroke.getKeyStroke("ctrl shift X"));
            mCSV = new JMenuItem("Eksportuj do CSV");
            mCSV.addActionListener(this);
            mCSV.setAccelerator(KeyStroke.getKeyStroke("ctrl shift C"));
            mWyjscie = new JMenuItem("Wyjdź z programu");
            mWyjscie.addActionListener(this);
            mWyjscie.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
            menuPlik.add(mXML);
            menuPlik.add(mCSV);
            menuPlik.addSeparator();
            menuPlik.add(mWyjscie);

            menuKalendarz = new JMenu("Kalendarz");                     // Kalendarz
            mWyswietlKalendarz = new JMenuItem("Wyświetl kalendarz");
            mWyswietlKalendarz.addActionListener(this);
            mWyswietlKalendarz.setAccelerator(KeyStroke.getKeyStroke("ctrl shift K"));
            menuKalendarz.add(mWyswietlKalendarz);

            menuWyglad = new JMenu("Wygląd");
            menuMotywy = new JMenu("Wybierz wygląd programu");
            mMetal = new JMenuItem("Metal");
            mMetal.addActionListener(this);
            mMetal.setAccelerator(KeyStroke.getKeyStroke("ctrl shift M"));
            mNimbus = new JMenuItem("Nibus");
            mNimbus.addActionListener(this);
            mNimbus.setAccelerator(KeyStroke.getKeyStroke("ctrl shift N"));
            mWindows = new JMenuItem("Windows");
            mWindows.addActionListener(this);
            mWindows.setAccelerator(KeyStroke.getKeyStroke("ctrl shift W"));

            menuMotywy.add(mMetal);
            menuMotywy.add(mNimbus);
            menuMotywy.add(mWindows);

            mWybierzKolorCzcionki = new JMenuItem("Ustaw kolor czcionki w programie");
            mWybierzKolorCzcionki.addActionListener(this);
            mWybierzKolorCzcionki.setAccelerator(KeyStroke.getKeyStroke("ctrl shift F"));
            menuWyglad.add(menuMotywy);
            menuWyglad.add(mWybierzKolorCzcionki);

            menuPomoc = new JMenu("Pomoc");                            // Pomoc
            moProgramie = new JMenuItem("O programie");
            moProgramie.addActionListener(this);
            menuPomoc.add(moProgramie);

            setJMenuBar(menuBar);
            menuBar.add(menuPlik);
            menuBar.add(menuKalendarz);
            menuBar.add(menuWyglad);
            menuBar.add(Box.createHorizontalGlue());
            menuBar.add(menuPomoc);


            header = new JLabel("Witamy w naszej bibliotece! ");       // Header
            header.setBounds(370,20,550,50);
            header.setFont(new Font(Font.SERIF, Font.BOLD, 35));
            add(header);

            lMenu = new JLabel("Menu programu: ");
            lMenu.setBounds(50,110,250,40);
            lMenu.setFont(new Font(Font.SERIF,Font.PLAIN,30));
            add(lMenu);

            lwybierz = new JButton("1. Wybierz czytelnika. ");
            lwybierz.setBounds(50,160,330,40);
            lwybierz.setFont(new Font(Font.SERIF,Font.PLAIN,16));
            add(lwybierz);
            lwybierz.addActionListener(this);

            lStworz = new JButton("2. Stwórz nowego czytelnika. ");
            lStworz.setBounds(50,210,330,40);
            lStworz.setFont(new Font(Font.SERIF,Font.PLAIN,16));
            add(lStworz);
            lStworz.addActionListener(this);

            lWyswietlUzytkownikow = new JButton("3. Wyświetl użytkowników. ");
            lWyswietlUzytkownikow.setBounds(50,260,330,40);
            lWyswietlUzytkownikow.setFont(new Font(Font.SERIF,Font.PLAIN,16));
            add(lWyswietlUzytkownikow);
            lWyswietlUzytkownikow.addActionListener(this);

            lWyswietlKsiazki = new JButton("4. Wyświetl wszystkie książki. ");
            lWyswietlKsiazki.setBounds(50,310,330,40);
            lWyswietlKsiazki.setFont(new Font(Font.SERIF,Font.PLAIN,16));
            add(lWyswietlKsiazki);
            lWyswietlKsiazki.addActionListener(this);

            lWypozycz = new JButton("5. Wypożycz książkę. ");
            lWypozycz.setBounds(50,360,330,40);
            lWypozycz.setFont(new Font(Font.SERIF,Font.PLAIN,16));
            add(lWypozycz);
            lWypozycz.addActionListener(this);

            lOddaj = new JButton("6. Oddaj książkę. ");
            lOddaj.setBounds(50,410,330,40);
            lOddaj.setFont(new Font(Font.SERIF,Font.PLAIN,16));
            add(lOddaj);
            lOddaj.addActionListener(this);

            lWyswietlWypoz = new JButton("7. Wyświetl informacje dotyczące wypożyczeń. ");
            lWyswietlWypoz.setBounds(50,460,330,40);
            lWyswietlWypoz.setFont(new Font(Font.SERIF,Font.PLAIN,16));
            add(lWyswietlWypoz);
            lWyswietlWypoz.addActionListener(this);

            wpiszId = new JLabel("Wpisz ID czytelnika. ");
            wpiszId.setBounds(782,255,550,50);
            wpiszId.setFont(new Font(Font.SERIF, Font.BOLD, 16));
            add(wpiszId);
            wpiszId.setVisible(false);

            bZaloguj = new JButton("Zaloguj ");
            bZaloguj.setBounds(900,380,300,50);
            bZaloguj.setFont(new Font(Font.SERIF,Font.PLAIN,16));
            add(bZaloguj);
            bZaloguj.addActionListener(this);
            bZaloguj.setVisible(false);


            Wybierz = new JTextField("");
            Wybierz.setBounds(700,300, 300, 40);
            Wybierz.addActionListener(this);
            add(Wybierz);
            Wybierz.setVisible(false);

            jPodajDane = new JLabel("Podaj dane czytelnika: ");         // Stwórz użytkownika
            jPodajDane.setBounds(730,130,350,85);
            jPodajDane.setFont(new Font(Font.SERIF, Font.BOLD, 20));
            add(jPodajDane);
            jPodajDane.setVisible(false);

            jImie = new JLabel("Imie: ");
            jImie.setBounds(550,205,550,50);
            jImie.setFont(new Font(Font.SERIF, Font.BOLD, 16));
            add(jImie);
            jImie.setVisible(false);

            jNazwisko = new JLabel("Nazwisko: ");
            jNazwisko.setBounds(550,255,550,50);
            jNazwisko.setFont(new Font(Font.SERIF, Font.BOLD, 16));
            add(jNazwisko);
            jNazwisko.setVisible(false);

            jEmail = new JLabel("Email: ");
            jEmail.setBounds(550,305,550,50);
            jEmail.setFont(new Font(Font.SERIF, Font.BOLD, 16));
            add(jEmail);
            jEmail.setVisible(false);

            jTelefon= new JLabel("Nr telefonu: ");
            jTelefon.setBounds(550,355,550,50);
            jTelefon.setFont(new Font(Font.SERIF, Font.BOLD, 16));
            add(jTelefon);
            jTelefon.setVisible(false);

            jDzien = new JLabel("Dzień: ");
            jDzien.setBounds(880,205,550,50);
            jDzien.setFont(new Font(Font.SERIF, Font.BOLD, 16));
            add(jDzien);
            jDzien.setVisible(false);

            jMiesiac = new JLabel("Miesiąc: ");
            jMiesiac .setBounds(880,255,550,50);
            jMiesiac .setFont(new Font(Font.SERIF, Font.BOLD, 16));
            add(jMiesiac);
            jMiesiac.setVisible(false);

            jRok = new JLabel("Rok:  ");
            jRok.setBounds(880,305,550,50);
            jRok.setFont(new Font(Font.SERIF, Font.BOLD, 16));
            add(jRok);
            jRok.setVisible(false);

            tImie = new JTextField("");
            tImie.setBounds(632,215,150,35);
            tImie.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
            add(tImie);
            tImie.addActionListener(this);
            tImie.setVisible(false);

            tNazwisko = new JTextField("");
            tNazwisko.setBounds(632,265,150,35);
            tNazwisko.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
            add(tNazwisko);
            tNazwisko.addActionListener(this);
            tNazwisko.setVisible(false);

            tEmail= new JTextField("");
            tEmail.setBounds(632,311,150,35);
            tEmail.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
            add(tEmail);
            tEmail.addActionListener(this);
            tEmail.setVisible(false);

            tTelefon= new JTextField("");
            tTelefon.setBounds(632,365,150,35);
            tTelefon.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
            add(tTelefon);
            tTelefon.addActionListener(this);
            tTelefon.setVisible(false);

            tDzien= new JTextField("");
            tDzien.setBounds(945,215,150,35);
            tDzien.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
            add(tDzien);
            tDzien.addActionListener(this);
            tDzien.setVisible(false);

            tMiesiac= new JTextField("");
            tMiesiac.setBounds(945,265,150,35);
            tMiesiac.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
            add(tMiesiac);
            tMiesiac.addActionListener(this);
            tMiesiac.setVisible(false);

            tRok= new JTextField("");
            tRok.setBounds(945,311,150,35);
            tRok.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
            add(tRok);
            tRok.addActionListener(this);
            tRok.setVisible(false);

            bCreate = new JButton("Dodaj czytelnika do bazy. ");
            bCreate.setBounds(900,380,300,50);
            bCreate.setFont(new Font(Font.SERIF,Font.PLAIN,16));
            add(bCreate);
            bCreate.addActionListener(this);
            bCreate.setVisible(false);

            lZalogowano = new JLabel("Pomyślnie zalogowano! ");
            lZalogowano.setBounds(800,450,300,50);
            lZalogowano.setFont(new Font(Font.SERIF, Font.BOLD, 16));
            add(lZalogowano);
            lZalogowano.setVisible(false);

            lDodano = new JLabel("Dodano użytkownika do bazy danych! ");
            lDodano.setBounds(800,450,300,50);
            lDodano.setFont(new Font(Font.SERIF, Font.BOLD, 16));
            add(lDodano);
            lDodano.setVisible(false);

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object src = e.getSource();
            if(src == mWyjscie)
            {
                int odp = JOptionPane.showConfirmDialog(this,"Czy na pewno chcesz wyjść?", "Pytanie", JOptionPane.YES_NO_OPTION);
                if(odp==JOptionPane.YES_OPTION)
                {
                    dispose();
                }
                else if(odp==JOptionPane.NO_OPTION)
                {
                    JOptionPane.showMessageDialog(this, "Tak myślałem! =)");
                }
                else if(odp==JOptionPane.CLOSED_OPTION)
                {
                    JOptionPane.showMessageDialog(this,"Tak nie robimy.");
                }
            }
            else if(src == mWyswietlKalendarz)
            {
                //konstruktor klasy kalendarz
            }
            else if(src == moProgramie)
            {
                JOptionPane.showMessageDialog(this, "Aplikacja została wykonana przez: \n Emil Kowalczyk, nr indeksu: 230178,  \n Dominik Świąder, nr indeksu: 230214 ", "O programie", JOptionPane.WARNING_MESSAGE );
            }
            else if(src == mMetal)
            {
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (InstantiationException instantiationException) {
                    instantiationException.printStackTrace();
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                } catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
                    unsupportedLookAndFeelException.printStackTrace();
                }
                SwingUtilities.updateComponentTreeUI(this);
            }
            else if(src == mNimbus)
            {
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (InstantiationException instantiationException) {
                    instantiationException.printStackTrace();
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                } catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
                    unsupportedLookAndFeelException.printStackTrace();
                }
            }
            else if(src == mWindows)
            {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    SwingUtilities.updateComponentTreeUI(this);
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                } catch (InstantiationException instantiationException) {
                    instantiationException.printStackTrace();
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                } catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
                    unsupportedLookAndFeelException.printStackTrace();
                }
            }
            else if(src == lwybierz)
            {
                Wybierz.setVisible(true);
                wpiszId.setVisible(true);       lDodano.setVisible(false);
                bZaloguj.setVisible(true);      lZalogowano.setVisible(false);

                jPodajDane.setVisible(false);
                jImie.setVisible(false);         tImie.setVisible(false);
                jNazwisko.setVisible(false);     tNazwisko.setVisible(false);
                jEmail.setVisible(false);        tEmail.setVisible(false);
                jTelefon.setVisible(false);      tTelefon.setVisible(false);
                jDzien.setVisible(false);        tDzien.setVisible(false);
                jMiesiac.setVisible(false);      tMiesiac.setVisible(false);
                jRok.setVisible(false);          tRok.setVisible(false);
                bCreate.setVisible(false);



            }
            else if(src == bZaloguj) {
                int id = Integer.parseInt(Wybierz.getText());

                pm.login(id);
                if(pm.currentPerson == null) {
                    System.out.println("Wystapil blad podczas wczytywania uzytkownika");
                }
                else {
                    lZalogowano.setVisible(true);
                }
            }
            else if(src == lStworz)
            {
                Wybierz.setVisible(false);
                wpiszId.setVisible(false);
                lZalogowano.setVisible(false);
                bZaloguj.setVisible(false);
                lDodano.setVisible(false);


                jPodajDane.setVisible(true);
                jImie.setVisible(true);         tImie.setVisible(true);
                jNazwisko.setVisible(true);     tNazwisko.setVisible(true);
                jEmail.setVisible(true);        tEmail.setVisible(true);
                jTelefon.setVisible(true);      tTelefon.setVisible(true);
                jDzien.setVisible(true);        tDzien.setVisible(true);
                jMiesiac.setVisible(true);      tMiesiac.setVisible(true);
                jRok.setVisible(true);          tRok.setVisible(true);

                bCreate.setVisible(true);
            }
            else if (src == bCreate)
            {
                if(tImie.equals(""))
                {
                    JOptionPane.showMessageDialog(this, "Pole nie może być puste! ", "Ostrzerzenie! ", JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                    imie = tImie.getText();
                }
                if(tNazwisko.equals(""))
                {
                    JOptionPane.showMessageDialog(this, "Pole nie może być puste! ", "Ostrzerzenie! ", JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                    nazwisko = tNazwisko.getText();
                }
                if(tEmail.equals(""))
                {
                    JOptionPane.showMessageDialog(this, "Pole nie może być puste! ", "Ostrzerzenie! ", JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                    email = tEmail.getText();
                }
                if(tTelefon.equals(""))
                {
                    JOptionPane.showMessageDialog(this, "Pole nie może być puste! ", "Ostrzerzenie! ", JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                    telefon = tTelefon.getText();
                }
                if(tDzien.equals(""))
                {
                    JOptionPane.showMessageDialog(this, "Pole nie może być puste! ", "Ostrzerzenie! ", JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                    dzien = Integer.parseInt(tDzien.getText());
                }
                if(tMiesiac.equals(""))
                {
                    JOptionPane.showMessageDialog(this, "Pole nie może być puste! ", "Ostrzerzenie! ", JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                    miesiac = Integer.parseInt(tMiesiac.getText());
                }
                if(tRok.equals(""))
                {
                    JOptionPane.showMessageDialog(this, "Pole nie może być puste! ", "Ostrzerzenie! ", JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                    rok = Integer.parseInt(tRok.getText());
                }


                Person person = new Person(-1, imie, nazwisko, dzien, miesiac, rok , email, telefon);

                pm.addNewPerson(person);
                lDodano.setVisible(true);
            }
            else if (src == lWyswietlUzytkownikow)
            {
                Wybierz.setVisible(false);
                wpiszId.setVisible(false);       lDodano.setVisible(false);
                bZaloguj.setVisible(false);      lZalogowano.setVisible(false);
                jPodajDane.setVisible(false);
                jImie.setVisible(false);         tImie.setVisible(false);
                jNazwisko.setVisible(false);     tNazwisko.setVisible(false);
                jEmail.setVisible(false);        tEmail.setVisible(false);
                jTelefon.setVisible(false);      tTelefon.setVisible(false);
                jDzien.setVisible(false);        tDzien.setVisible(false);
                jMiesiac.setVisible(false);      tMiesiac.setVisible(false);
                jRok.setVisible(false);          tRok.setVisible(false);
                bCreate.setVisible(false);
            }
            else if (src == mXML)
            {

            }

        }
    }

