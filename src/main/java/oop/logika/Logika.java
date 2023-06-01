package oop.logika;

import lombok.Getter;
import oop.pole.Pole;
import oop.pole.Smer;
import oop.gui.Plocha;
import oop.hrac.Hrac;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

public class Logika extends UniverzalnyAdapter {
    @Getter
    private final Plocha plocha;
    @Getter
    private final JComboBox<String> box;
    @Getter
    private final JFrame ram;
    @Getter
    private final JLabel vypisRozmeru;
    private final int[] rozmery;
    private Pole aktualnePole;
    private final Hrac hrac;
    private final Hrac pc;
    private Hrac aktivnyHrac;

    public Logika() {
        this.rozmery = new int[]{6, 8, 10, 12};

        this.ram = new JFrame("Reversi");

        this.plocha = new Plocha();

        String[] rozmery= {"6x6", "8x8", "10x10", "12x12"};
        this.box = new JComboBox<>(rozmery);

        this.vypisRozmeru = new JLabel("Aktualny rozmer : 6x6", SwingConstants.CENTER);

        this.aktualnePole = new Pole(plocha.getRozmer());

        this.hrac = new Hrac(1);
        this.pc = new Hrac(2);

        this.aktivnyHrac = this.hrac;

        this.tahHraca();

        this.box.setFocusable(false); // posledny riadok!!!
    }

    public void tahHraca(){
        this.aktualnePole.vyznac(this.aktivnyHrac.getCisloHraca());
        if(this.aktualnePole.getHratelnostTeraz()) {
            this.plocha.setHrac(this.aktivnyHrac.getCisloHraca());
            this.plocha.setAktualnePole(this.aktualnePole);
            this.plocha.repaint();
        }
        else if(this.aktualnePole.getHratelnostPred()){
            this.aktivnyHrac = this.zmenHraca(this.aktivnyHrac);
            this.tahPC();
        }
        else{
            this.plocha.setKoniec(true);
            this.urcenieVitaza();
            this.plocha.repaint();
        }
    }

    public void tahPC(){
        this.aktualnePole.vyznac(this.aktivnyHrac.getCisloHraca());
        int ziskaneBody = 0;
        if(this.aktualnePole.getHratelnostTeraz()) {
            int index = -1;
            for (int i = 0; i < this.aktualnePole.getMoznostiPole().size(); i++) {
                if (this.aktualnePole.getMoznostiPole().get(i) != null && this.aktualnePole.getMoznostiPole().get(i).getBody() > ziskaneBody) {
                    ziskaneBody = this.aktualnePole.getMoznostiPole().get(i).getBody();
                    index = i;
                }
            }

            this.otocenieKamenov(index);
            this.plocha.setAktualnePole(this.aktualnePole);
            this.plocha.repaint();
        }
        else if(!this.aktualnePole.getHratelnostPred()){
            this.urcenieVitaza();
            this.plocha.setKoniec(true);
            this.plocha.repaint();
        }
        this.aktivnyHrac = this.zmenHraca(this.aktivnyHrac);
        this.tahHraca();
    }

    public void urcenieVitaza(){
        if(this.hrac.getBody() > this.pc.getBody())
        {
            this.plocha.setVitaz("Hrac");
        }
        else if(this.hrac.getBody() < this.pc.getBody()){
            this.plocha.setVitaz("PC");
        }
        else{
            this.plocha.setVitaz("Remiza");
        }
        this.plocha.setBody(this.hrac.getBody() + " : " + this.pc.getBody());
    }

    public Hrac zmenHraca(Hrac hrac){
        if(hrac.getCisloHraca() == this.hrac.getCisloHraca()){
            return this.pc;
        }
        return this.hrac;
    }

    protected void restart(){
        this.aktualnePole = new Pole(this.plocha.getRozmer());
        this.aktualnePole.vyznac(this.aktivnyHrac.getCisloHraca());
        this.plocha.setHrac(this.aktivnyHrac.getCisloHraca());
        this.hrac.setBody(2);
        this.pc.setBody(2);
        this.plocha.setAktualnePole(this.aktualnePole);
        this.plocha.setKoniec(false);
        this.plocha.repaint();
    }

    public void zmenaNalepky(int rozmer){
        this.vypisRozmeru.setText("Aktualny rozmer : " + rozmer + "x" + rozmer);
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == this.box){
            this.plocha.setRozmer(this.rozmery[this.box.getSelectedIndex()]);
            this.zmenaNalepky(this.plocha.getRozmer());
        }
        this.restart();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                this.restart();
                break;
            case KeyEvent.VK_ESCAPE:
                this.ram.dispose();
                System.exit(0);
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        this.plocha.setIndexZvyraznenia(prepocetNaIndex(e.getX(), e.getY()));
        this.plocha.repaint();
    }

    public int prepocetNaIndex(int x, int y){
        int sirka = this.plocha.getSirkaStvorca();
        int vyska = this.plocha.getVyskaStvorca();
        int riadok = 0;
        int stlpec = 0;
        while(x > sirka){
            stlpec++;
            x = x - sirka;
        }
        while(y > vyska){
            riadok++;
            y = y - vyska;
        }
        return riadok * this.plocha.getRozmer() + stlpec;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int index = prepocetNaIndex(e.getX(), e.getY());
        if(this.aktualnePole.getMoznostiPole().get(index)  != null) {
            this.otocenieKamenov(index);
            this.plocha.setAktualnePole(this.aktualnePole);
            this.plocha.repaint();
            this.aktivnyHrac = this.zmenHraca(this.aktivnyHrac);
            this.tahPC();
        }
    }

    public void otocenieKamenov(int index){
        this.aktualnePole.setAktualnePole(index, this.aktivnyHrac.getCisloHraca());
        List<Integer> pociatocneIndexy = this.aktualnePole.getMoznostiPole().get(index).getIndexStareho();
        List<Smer> smery = this.aktualnePole.getMoznostiPole().get(index).getSmer();
        for(int i = 0; i < pociatocneIndexy.size(); i++){
            int pomocna = pociatocneIndexy.get(i);
            while(pomocna != index){
                this.aktualnePole.getAktualnePole().set(pomocna + smery.get(i).getX1() * this.plocha.getRozmer() + smery.get(i).getY1(), this.aktivnyHrac.getCisloHraca());
                pomocna = pomocna + smery.get(i).getX1() * this.plocha.getRozmer() + smery.get(i).getY1();
            }
        }
        this.aktivnyHrac.setBody(this.aktivnyHrac.getBody() + this.aktualnePole.getMoznostiPole().get(index).getBody() + 1);
        this.aktivnyHrac = this.zmenHraca(this.aktivnyHrac);
        this.aktivnyHrac.setBody(this.aktivnyHrac.getBody() - this.aktualnePole.getMoznostiPole().get(index).getBody());
        this.aktivnyHrac = this.zmenHraca(this.aktivnyHrac);
    }
}