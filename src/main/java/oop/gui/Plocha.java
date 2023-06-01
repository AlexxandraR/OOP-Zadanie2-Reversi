package oop.gui;

import lombok.Getter;
import lombok.Setter;
import oop.pole.Pole;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Plocha extends JPanel {
    private Dimension dimenzie;
    private int sirka;
    private int vyska;
    @Setter
    @Getter
    private int rozmer;
    @Getter
    private int vyskaStvorca;
    @Getter
    private int sirkaStvorca;
    @Setter
    private int hrac;
    @Setter
    private Pole aktualnePole;
    @Setter
    private int indexZvyraznenia;
    @Setter
    private boolean koniec;
    @Setter
    private String vitaz;
    @Setter
    private String body;

    public Plocha() {
        this.dimenzie = getSize();
        this.sirka = dimenzie.width;
        this.vyska = dimenzie.height;
        this.rozmer = 6;
        this.vyskaStvorca = this.vyska / this.rozmer;
        this.sirkaStvorca = this.sirka / this.rozmer;
        this.setBackground(new Color(56, 177, 80));
        this.koniec = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.dimenzie = getSize();
        this.sirka = dimenzie.width;
        this.vyska = dimenzie.height;

        this.vyskaStvorca = this.vyska / this.rozmer;
        this.sirkaStvorca = this.sirka / this.rozmer;

        int yRiadku = 0;
        int xStlpca = 0;

        g.setColor(new Color(15, 100, 30));

        //vykreslenie ciar
        for (int i = 0; i < this.rozmer; i++) {
            g.drawLine(0, yRiadku, this.sirka - 1, yRiadku);
            yRiadku += this.vyskaStvorca;
            g.drawLine(xStlpca, 0, xStlpca, this.vyska - 1);
            xStlpca += this.sirkaStvorca;
        }

        //vykreslenie kamenov
        for(int i = 0; i < this.aktualnePole.getAktualnePole().size(); i++){
            if (this.aktualnePole.getAktualnePole().get(i) != 0) {
                if(this.aktualnePole.getAktualnePole().get(i) == 1) {
                    g.setColor(new Color(0, 0, 0));
                }
                else{
                    g.setColor(new Color(255, 255, 255));
                }
                int[] suradnice = suradnice(i);
                g.fillOval(suradnice[1], suradnice[0], this.sirkaStvorca, this.vyskaStvorca);
            }
        }

        this.vyznacMoznosti(g); //kam mozem umiestnit kamen
        if (this.indexZvyraznenia < this.rozmer * this.rozmer && this.aktualnePole.getMoznostiPole().get(this.indexZvyraznenia) != null) {
            this.zvyrazneniePolicka(g); // mys na policku
        }
        if(this.koniec){
            this.koniecHry(g);
        }
    }

    public void vyznacMoznosti(Graphics g){
        for(int k = 0; k < this.aktualnePole.getMoznostiPole().size(); k++){
            if(this.aktualnePole.getMoznostiPole().get(k) != null){
                if(this.hrac == 1){
                    g.setColor(new Color(0,0,0, 85));
                }
                else{
                    g.setColor(new Color(255,255,255, 129));
                }
                int[] suradnice = this.suradnice(k);
                g.fillOval(suradnice[1], suradnice[0], this.sirkaStvorca, this.vyskaStvorca);
            }
        }
    }

    public int[] suradnice(int index){
        int riadok = index/this.rozmer;
        int stlpec = index%this.rozmer;
        int x = this.sirkaStvorca*riadok;
        int y = this.vyskaStvorca*stlpec;
        return new int[] {x, y};
    }

    public void zvyrazneniePolicka(Graphics g){
        if(this.hrac == 1){
            g.setColor(new Color(0,0,0, 95));
        }
        else{
            g.setColor(new Color(255,255,255, 153));
        }
        int[] suradnice = suradnice(this.indexZvyraznenia);
        g.fillOval(suradnice[1], suradnice[0], this.sirkaStvorca, this.vyskaStvorca);
    }

    public void koniecHry(Graphics g){
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 45));
        FontMetrics metrika = getFontMetrics(g.getFont());
        g.drawString("Koniec Hry!", (this.sirka - metrika.stringWidth("Koniec Hry!"))/2, this.vyska/3);
        if(!Objects.equals(vitaz, "Remiza")){
            g.drawString("Vyhral " + vitaz + " " + body, (this.sirka - metrika.stringWidth("Vyhral " + vitaz + " " + body))/2, 2*this.vyska/3);
        }
        else{
            g.drawString(vitaz + " " + body, (this.sirka - metrika.stringWidth(vitaz + " " + body))/2, 2*this.vyska/3);
        }
    }

}
