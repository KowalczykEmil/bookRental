package com.emilkowalczyk.GUI;

import com.emilkowalczyk.Logic.*;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class GUI extends JFrame implements ActionListener {
    JMenuBar menuBar;
    JMenu menuPlik, menuKalendarz, menuWyglad, menuPomoc, menuMotywy;
    JMenuItem mXML, mCSV, mWyjscie, mWyswietlKalendarz, mWybierzKolorCzcionki, mMetal, mNimbus, mWindows, moProgramie;
    JLabel header, lMenu, wpiszId, jPodajDane, jImie, jNazwisko, jEmail, jTelefon, jDzien, jMiesiac, jRok, lZalogowano, lDodano, lPodajIdKsiazki,lPodajIDKsiazkiZ, lInfoRent;
    JButton lWyswietlUzytkownikow, lwybierz, lStworz, lWyswietlKsiazki, lWypozycz, lOddaj, lWyswietlWypoz, bCreate, bZaloguj, bWypozyczKsiazke, bOddajKsiazke;
    JTextField Wybierz, tImie, tNazwisko, tEmail, tTelefon, tDzien, tMiesiac, tRok, tIdKsiazki, tIdKsiazkiZ;
    JScrollPane spUsers, spBooks, spRents;
    ImageIcon icon;
    int idUser = -1, dzien, miesiac, rok;
    String dateOfReturn;
    String imie, nazwisko, email, telefon;
    JLabel lAnnouncement;

    JDatePanelImpl datePanel;

    PersonManager pm;
    DataBaseMenager dataBaseMenager = new DataBaseMenager();
    boolean endStatus = false;
    boolean loginStatus = false;
    private Set<Integer> displayedBoxes = new HashSet<>();

    private void setUpPersonManager() {
        pm = new PersonManager();
    }

    public GUI() {
        setSize(1250, 720);
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

        if (!loginStatus) setUpPersonManager();

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
        header.setBounds(370, 20, 550, 50);
        header.setFont(new Font(Font.SERIF, Font.BOLD, 35));
        add(header);

        lMenu = new JLabel("Menu programu: ");
        lMenu.setBounds(50, 110, 250, 40);
        lMenu.setFont(new Font(Font.SERIF, Font.PLAIN, 30));
        add(lMenu);

        lwybierz = new JButton("1. Wybierz czytelnika. ");
        lwybierz.setBounds(50, 160, 330, 40);
        lwybierz.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        add(lwybierz);
        lwybierz.addActionListener(this);

        lStworz = new JButton("2. Stwórz nowego czytelnika. ");
        lStworz.setBounds(50, 210, 330, 40);
        lStworz.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        add(lStworz);
        lStworz.addActionListener(this);

        lWyswietlUzytkownikow = new JButton("3. Wyświetl użytkowników. ");
        lWyswietlUzytkownikow.setBounds(50, 260, 330, 40);
        lWyswietlUzytkownikow.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        add(lWyswietlUzytkownikow);
        lWyswietlUzytkownikow.addActionListener(this);

        lWyswietlKsiazki = new JButton("4. Wyświetl wszystkie książki. ");
        lWyswietlKsiazki.setBounds(50, 310, 330, 40);
        lWyswietlKsiazki.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        add(lWyswietlKsiazki);
        lWyswietlKsiazki.addActionListener(this);

        lWypozycz = new JButton("5. Wypożycz książkę. ");
        lWypozycz.setBounds(50, 360, 330, 40);
        lWypozycz.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        add(lWypozycz);
        lWypozycz.addActionListener(this);

        lOddaj = new JButton("6. Oddaj książkę. ");
        lOddaj.setBounds(50, 410, 330, 40);
        lOddaj.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        add(lOddaj);
        lOddaj.addActionListener(this);

        lWyswietlWypoz = new JButton("7. Wyświetl informacje dotyczące wypożyczeń. ");
        lWyswietlWypoz.setBounds(50, 460, 330, 40);
        lWyswietlWypoz.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        add(lWyswietlWypoz);
        lWyswietlWypoz.addActionListener(this);

        wpiszId = new JLabel("Wpisz ID czytelnika. ");
        wpiszId.setBounds(782, 255, 550, 50);
        wpiszId.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        add(wpiszId);
        wpiszId.setVisible(false);

        bZaloguj = new JButton("Zaloguj ");
        bZaloguj.setBounds(900, 380, 300, 50);
        bZaloguj.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        add(bZaloguj);
        bZaloguj.addActionListener(this);
        bZaloguj.setVisible(false);


        Wybierz = new JTextField("");
        Wybierz.setBounds(700, 300, 300, 40);
        Wybierz.addActionListener(this);
        add(Wybierz);
        Wybierz.setVisible(false);

        jPodajDane = new JLabel("Podaj dane czytelnika: ");         // Stwórz użytkownika
        jPodajDane.setBounds(730, 130, 350, 85);
        jPodajDane.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        add(jPodajDane);
        jPodajDane.setVisible(false);

        jImie = new JLabel("Imie: ");
        jImie.setBounds(550, 205, 550, 50);
        jImie.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        add(jImie);
        jImie.setVisible(false);

        jNazwisko = new JLabel("Nazwisko: ");
        jNazwisko.setBounds(550, 255, 550, 50);
        jNazwisko.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        add(jNazwisko);
        jNazwisko.setVisible(false);

        jEmail = new JLabel("Email: ");
        jEmail.setBounds(550, 305, 550, 50);
        jEmail.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        add(jEmail);
        jEmail.setVisible(false);

        jTelefon = new JLabel("Nr telefonu: ");
        jTelefon.setBounds(550, 355, 550, 50);
        jTelefon.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        add(jTelefon);
        jTelefon.setVisible(false);

        jDzien = new JLabel("Dzień: ");
        jDzien.setBounds(880, 205, 550, 50);
        jDzien.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        add(jDzien);
        jDzien.setVisible(false);

        jMiesiac = new JLabel("Miesiąc: ");
        jMiesiac.setBounds(880, 255, 550, 50);
        jMiesiac.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        add(jMiesiac);
        jMiesiac.setVisible(false);

        jRok = new JLabel("Rok:  ");
        jRok.setBounds(880, 305, 550, 50);
        jRok.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        add(jRok);
        jRok.setVisible(false);

        tImie = new JTextField("");
        tImie.setBounds(632, 215, 150, 35);
        tImie.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        add(tImie);
        tImie.addActionListener(this);
        tImie.setVisible(false);

        tNazwisko = new JTextField("");
        tNazwisko.setBounds(632, 265, 150, 35);
        tNazwisko.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        add(tNazwisko);
        tNazwisko.addActionListener(this);
        tNazwisko.setVisible(false);

        tEmail = new JTextField("");
        tEmail.setBounds(632, 311, 150, 35);
        tEmail.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        add(tEmail);
        tEmail.addActionListener(this);
        tEmail.setVisible(false);

        tTelefon = new JTextField("");
        tTelefon.setBounds(632, 365, 150, 35);
        tTelefon.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        add(tTelefon);
        tTelefon.addActionListener(this);
        tTelefon.setVisible(false);

        tDzien = new JTextField("");
        tDzien.setBounds(945, 215, 150, 35);
        tDzien.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        add(tDzien);
        tDzien.addActionListener(this);
        tDzien.setVisible(false);

        tMiesiac = new JTextField("");
        tMiesiac.setBounds(945, 265, 150, 35);
        tMiesiac.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        add(tMiesiac);
        tMiesiac.addActionListener(this);
        tMiesiac.setVisible(false);

        tRok = new JTextField("");
        tRok.setBounds(945, 311, 150, 35);
        tRok.setFont(new Font(Font.SERIF, Font.PLAIN, 14));
        add(tRok);
        tRok.addActionListener(this);
        tRok.setVisible(false);

        bCreate = new JButton("Dodaj czytelnika do bazy. ");
        bCreate.setBounds(900, 380, 300, 50);
        bCreate.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        add(bCreate);
        bCreate.addActionListener(this);
        bCreate.setVisible(false);

        lZalogowano = new JLabel("Pomyślnie zalogowano! ");
        lZalogowano.setBounds(800, 450, 300, 50);
        lZalogowano.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        add(lZalogowano);
        lZalogowano.setVisible(false);

        lDodano = new JLabel("Dodano użytkownika do bazy danych! ");
        lDodano.setBounds(800, 450, 300, 50);
        lDodano.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        add(lDodano);
        lDodano.setVisible(false);

        lPodajIdKsiazki = new JLabel("Wpisz ID książki, którą chcesz wypożyczyć. ");
        lPodajIdKsiazki.setBounds(705, 255, 550, 50);
        lPodajIdKsiazki.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        add(lPodajIdKsiazki);
        lPodajIdKsiazki.setVisible(false);

        tIdKsiazki = new JTextField("");
        tIdKsiazki.setBounds(700, 300, 300, 40);
        tIdKsiazki.addActionListener(this);
        add(tIdKsiazki);
        tIdKsiazki.setVisible(false);

        bWypozyczKsiazke = new JButton("Wypożycz ");
        bWypozyczKsiazke.setBounds(900, 380, 300, 50);
        bWypozyczKsiazke.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        add(bWypozyczKsiazke);
        bWypozyczKsiazke.addActionListener(this);
        bWypozyczKsiazke.setVisible(false);

        bOddajKsiazke = new JButton("Zwróć książkę ");
        bOddajKsiazke.setBounds(900, 380, 300, 50);
        bOddajKsiazke.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
        add(bOddajKsiazke);
        bOddajKsiazke.addActionListener(this);
        bOddajKsiazke.setVisible(false);

        lPodajIDKsiazkiZ = new JLabel("Wpisz ID książki, którą chcesz zwrócić. ");
        lPodajIDKsiazkiZ.setBounds(705, 255, 550, 50);
        lPodajIDKsiazkiZ.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        add(lPodajIDKsiazkiZ);
        lPodajIDKsiazkiZ.setVisible(false);

        tIdKsiazkiZ = new JTextField("");
        tIdKsiazkiZ.setBounds(700, 300, 300, 40);
        tIdKsiazkiZ.addActionListener(this);
        add(tIdKsiazkiZ);
        tIdKsiazkiZ.setVisible(false);

        lInfoRent = new JLabel("Dummy");
        lInfoRent.setBounds(782, 485, 550, 50);
        lInfoRent.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        add(lInfoRent);
        lInfoRent.setVisible(false);

        lAnnouncement = new JLabel("DummyX");
        lAnnouncement.setBounds(500, 160, 650, 300);
        lAnnouncement.setFont(new Font(Font.SERIF, Font.BOLD, 16));
        add(lAnnouncement);
        lAnnouncement.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == mWyjscie) {
            close();
        } else if (src == mWyswietlKalendarz) {
            Calendar c = new Calendar();
            c.setVisible(true);
        } else if (src == moProgramie) {
            JOptionPane.showMessageDialog(this, "Aplikacja została wykonana przez: \n Emil Kowalczyk, nr indeksu: 230178,  \n Dominik Świąder, nr indeksu: 230214 ", "O programie", JOptionPane.WARNING_MESSAGE);
        } else if (src == mMetal) {
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
        } else if (src == mNimbus) {
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
        } else if (src == mWindows) {
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
        else if (src == mXML) {
            Output output = new Output();
            try {
                output.eksportXML();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        else if (src == lwybierz) {
            display(1);
        } else if (src == bZaloguj) {
            idUser = Integer.parseInt(Wybierz.getText());
            pm.login(idUser);
            if (pm.currentPerson == null) {
                System.out.println("Wystapil blad podczas wczytywania uzytkownika");
            } else {
                display(2);
            }

        } else if (src == lStworz) {
            display(3);
        } else if (src == bCreate) {
            imie = tImie.getText();
            nazwisko = tNazwisko.getText();
            dzien = Integer.parseInt(tDzien.getText());
            miesiac = Integer.parseInt(tMiesiac.getText());
            rok = Integer.parseInt(tRok.getText());
            email = tEmail.getText();
            telefon = tTelefon.getText();


            Person person = new Person(-1, imie, nazwisko, dzien, miesiac, rok, email, telefon);
            pm.addNewPerson(person);

            display(4);
        } else if (src == lWyswietlUzytkownikow) {
            CustomTable tableGenerator = new CustomTable(1);
            spUsers = tableGenerator.getSp();
            add(spUsers);
            display(5);
        } else if (src == lWyswietlKsiazki) {
            CustomTable tableGenerator = new CustomTable(2);
            spBooks = tableGenerator.getSp();
            add(spBooks);
            display(6);
        } else if (src == lWyswietlWypoz) {
            CustomTable tableGenerator = new CustomTable(3);
            spRents = tableGenerator.getSp();
            add(spRents);
            display(7);
        }
        else if (src == lWypozycz) {
            display(8);
        }
        else if(src == bWypozyczKsiazke){
            if(idUser == -1) {
                lInfoRent.setText("Najpierw wybierz użytkownika!");
                display(9);
            } else {

                int bookId = Integer.parseInt(tIdKsiazki.getText());

                if(dataBaseMenager.getBookById(bookId) == null) {
                    lInfoRent.setText("Książka o podanym ID nie istnieje.");
                    display(9);
                }
                else {
                    RentManager rm = new RentManager();
                    boolean status = rm.rent(pm.currentPerson, bookId);

                    Rent rent = dataBaseMenager.getRentInfo(bookId);
                    dateOfReturn = rent.getDate_of_return();

                    if(!status) {
                        lInfoRent.setText("Wybrana książka jest wypożyczona do " + dateOfReturn);
                        display(9);
                    }
                    else {
                        lInfoRent.setText("Wypożyczenie zostało zarejestrowane. Data zwrotu: " + dateOfReturn);
                        display(9);
                    }
                }
            }
        }
        else if (src == lOddaj) {
            display(10);
        }
        else if (src == bOddajKsiazke) {
            if(idUser == -1) {
                lInfoRent.setText("Najpierw wybierz użytkownika!");
                display(9);
            }
            else {
                int bookId = Integer.parseInt(tIdKsiazkiZ.getText());

                if(dataBaseMenager.returnBook(bookId)) {
                    lInfoRent.setText("Zwrot został zarejestrowany pomyślnie.");
                }
                else {
                    lInfoRent.setText("Książka o podanym ID nie jest wypożyczona.");
                }

                display(9);
            }
        }
    }

    private void close() {
        int odp = JOptionPane.showConfirmDialog(this, "Czy na pewno chcesz wyjść?", "Pytanie", JOptionPane.YES_NO_OPTION);
        if (odp == JOptionPane.YES_OPTION) {
            dispose();
        } else if (odp == JOptionPane.NO_OPTION) {
            JOptionPane.showMessageDialog(this, "Tak myślałem! =)");
        } else if (odp == JOptionPane.CLOSED_OPTION) {
            JOptionPane.showMessageDialog(this, "Tak nie robimy.");
        }
    }

    private void showAnnouncement() {
        List<Integer> toReturn = pm.getBooksToReturnShortly();

        if(toReturn != null) {
            if(!toReturn.isEmpty()) {
                final Runnable runnable =
                        (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
                if (runnable != null) runnable.run();

                String message = new String();
                message += "<html><body>****************** UWAGA ********************<br>";
                message += "Książki, które należy wkrótce zwrócić: <br>";

                for (Integer bookId : toReturn) {
                    Book book = dataBaseMenager.getBook(bookId);
                    Rent rent = dataBaseMenager.getRentInfo(book.getId());
                    message += book.getId() + ".    === " + book.getTitle() + "  ===  " +
                            "Autor: " + book.getAuthor() + "    " +
                            "Data zwrotu: " + rent.getDate_of_return() + "<br>";
                }

                message += "</body></html>";

                lAnnouncement.setText(message);
                display(11);
            }
        }
    }

    private void chooseUser(boolean x) {
        Wybierz.setVisible(x);
        wpiszId.setVisible(x);
        bZaloguj.setVisible(x);
        if(x) displayedBoxes.add(1);
    }

    private void chooseUserLogged(boolean x) {
        lZalogowano.setVisible(x);
        if(x) showAnnouncement();
        if(x) displayedBoxes.add(2);
    }

    private void createUser(boolean x) {
        jPodajDane.setVisible(x);
        jImie.setVisible(x);
        tImie.setVisible(x);
        jNazwisko.setVisible(x);
        tNazwisko.setVisible(x);
        jEmail.setVisible(x);
        tEmail.setVisible(x);
        jTelefon.setVisible(x);
        tTelefon.setVisible(x);
        jDzien.setVisible(x);
        tDzien.setVisible(x);
        jMiesiac.setVisible(x);
        tMiesiac.setVisible(x);
        jRok.setVisible(x);
        tRok.setVisible(x);
        bCreate.setVisible(x);
        bCreate.setVisible(x);
        if(x) displayedBoxes.add(3);
    }

    private void createUserCreated(boolean x) {
        bCreate.setVisible(x);
        if(x) displayedBoxes.add(4);
    }

    private void displayTable(JScrollPane sp, boolean x, Integer screenId) {
        sp.setVisible(x);
        if(x) displayedBoxes.add(screenId);
        if(!x) remove(sp);
    }

    private void rentBook(boolean x) {
        lPodajIdKsiazki.setVisible(x);
        tIdKsiazki.setVisible(x);
        bWypozyczKsiazke.setVisible(x);
        if(x) displayedBoxes.add(8);
    }

    private void rentBookLabel(boolean x) {
        lInfoRent.setVisible(x);
        if(x) displayedBoxes.add(9);
    }

    private void returnBook(boolean x) {
        lPodajIDKsiazkiZ.setVisible(x);
        tIdKsiazkiZ.setVisible(x);
        bOddajKsiazke.setVisible(x);
        if(x) displayedBoxes.add(10);
    }

    private void announcement(boolean x) {
        lZalogowano.setVisible(x);
        lAnnouncement.setVisible(x);
        if(x) displayedBoxes.add(11);
    }

    void checkDisplay() {
        if(!displayedBoxes.isEmpty()) {
            for (Integer box : displayedBoxes) {
                hide(box);
            }
            displayedBoxes.clear();
        }
    }

    private void display(Integer screenId) {
        checkDisplay();
        switch(screenId) {
            case 1 -> chooseUser(true); // 1. Wybierz użytkownika
            case 2 -> chooseUserLogged(true);
            case 3 -> createUser(true);
            case 4 -> createUserCreated(true);
            case 5 -> displayTable(spUsers,true, 5);
            case 6 -> displayTable(spBooks,true, 6);
            case 7 -> displayTable(spRents,true, 7);
            case 8 -> rentBook(true);
            case 9 -> rentBookLabel(true);
            case 10 -> returnBook(true);
            case 11 -> announcement(true);
        }
    }

    private void hide(Integer screenId) {
        switch(screenId) {
            case 1 -> chooseUser(false); // 1. Wybierz użytkownika
            case 2 -> chooseUserLogged(false);
            case 3 -> createUser(false);
            case 4 -> createUserCreated(false);
            case 5 -> displayTable(spUsers,false, 5);
            case 6 -> displayTable(spBooks,false, 6);
            case 7 -> displayTable(spRents,false, 7);
            case 8 -> rentBook(false);
            case 9 -> rentBookLabel(false);
            case 10 -> returnBook(false);
            case 11 -> announcement(false);
        }
    }
}