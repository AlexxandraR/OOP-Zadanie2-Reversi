package oop.gui;

import oop.logika.Logika;

import javax.swing.*;
import java.awt.*;

public class Hra {

    public Hra() {
        int rozmer = 6;
        Logika logika = new Logika();

        JFrame ram = logika.getRam();
        ram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ram.setSize(614, 689);
        ram.setResizable(false);
        ram.setLayout(new BorderLayout());

        //ram.setFocusable(true);
        ram.addKeyListener(logika);
        ram.setFocusable(true);

        JButton klavesRestart = new JButton("RESTART");
        klavesRestart.addActionListener(logika);
        klavesRestart.setFocusable(false);

        JLabel tah = new JLabel("Na tahu je Hrac (cierne)", SwingConstants.CENTER);

        JLabel vypisRozmeru = logika.getVypisRozmeru();

        JComboBox<String> box = logika.getBox();
        box.addActionListener(logika);
        box.setFocusable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,2));

        panel.add(klavesRestart);
        panel.add(tah);
        panel.add(vypisRozmeru);
        panel.add(box);

        panel.setBackground(new Color(160, 238, 8));

        ram.add(panel, BorderLayout.PAGE_START);

        Plocha plocha = logika.getPlocha();
        plocha.setRozmer(rozmer);

        ram.add(plocha, BorderLayout.CENTER);

        plocha.addMouseMotionListener(logika);
        plocha.addMouseListener(logika);

        ram.setVisible(true);
    }
}