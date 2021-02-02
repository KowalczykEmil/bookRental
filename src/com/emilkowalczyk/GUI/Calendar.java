package com.emilkowalczyk.GUI;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

public class Calendar extends JFrame {
    public Calendar() {
        int x = 220;
        int y = 220;
        setSize(x, y);
        setTitle("");
        setLayout(new FlowLayout());
        UtilDateModel model = new UtilDateModel();
        model.setDate(2021,01,31);
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePanel.setBounds(0, 0, x, y);
        datePanel.setVisible(true);
        add(datePanel);
    }
}
