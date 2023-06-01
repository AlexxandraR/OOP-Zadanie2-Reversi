package oop.pole;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Pole {
    @Getter
    private final List<Integer> aktualnePole;
    @Getter
    private List<Kroky> moznostiPole;
    private final int rozmer;
    private boolean hratelnostPred;
    private boolean hratelnostTeraz;

    public Pole(int rozmer) {
        this.rozmer = rozmer;
        this.aktualnePole = new ArrayList<>();
        this.moznostiPole = new ArrayList<>();
        this.hratelnostPred = true;
        this.hratelnostTeraz = true;
        for (int i = 0; i < rozmer*rozmer; i++){ //poloha kamenov v poli
            if (i == (rozmer-2)/2*rozmer+rozmer/2-1 || i == ((rozmer-2)/2+1)*rozmer+rozmer/2) {
                this.aktualnePole.add(1);
            }
            else if (i == (rozmer-2)/2*rozmer+rozmer/2 || i == ((rozmer-2)/2+1)*rozmer+rozmer/2-1) {
                this.aktualnePole.add(2);
            }
            else{
                this.aktualnePole.add(0);
            }
            moznostiPole.add(null);
        }
    }

    public void setAktualnePole(int index, int hodnota) {
        this.aktualnePole.set(index, hodnota);
    }

    public void vyznac(int hrac){ //moznosti polozenia kamena v poli
        this.moznostiPole = new ArrayList<>();
        for (int i = 0; i < rozmer*rozmer; i++){
            this.moznostiPole.add(null);
        }
        this.hratelnostPred = this.hratelnostTeraz;
        this.hratelnostTeraz = true;
        List<Integer> pomocnePole = new ArrayList<>(aktualnePole);
        Kroky krok;
        int kamen;
        int pomocnyKamen;
        int body = 0;
        while(pomocnePole.contains(hrac)){
            kamen = pomocnePole.indexOf(hrac);

            pomocnePole.set(kamen, 0);

            for (Smer s : Smer.values()) {
                pomocnyKamen = kamen;
                try {
                    while (this.aktualnePole.get(pomocnyKamen + s.getX1() * this.rozmer + s.getY1()) == this.protihrac(hrac)) {

                        body++;
                        pomocnyKamen = pomocnyKamen + s.getX1() * rozmer + s.getY1();
                        if (this.aktualnePole.get(pomocnyKamen + s.getX1() * this.rozmer + s.getY1()) == hrac) {
                            body = 0;
                            break;
                        }
                        //1-rozmerne pole -> treba osetrit pravu a lavu hranu
                        else if (this.aktualnePole.get(pomocnyKamen + s.getX1() * this.rozmer + s.getY1()) == 0 && pomocnyKamen%this.rozmer == 0 && !(s == Smer.HORE || s == Smer.DOLE) ||
                                this.aktualnePole.get(pomocnyKamen + s.getX1() * this.rozmer + s.getY1()) == 0 && pomocnyKamen%this.rozmer == this.rozmer-1 && !(s == Smer.HORE || s == Smer.DOLE) ||
                                this.aktualnePole.get(pomocnyKamen + s.getX1() * this.rozmer + s.getY1()) == protihrac(hrac) && pomocnyKamen%this.rozmer == 0 && !(s == Smer.HORE || s == Smer.DOLE) ||
                                this.aktualnePole.get(pomocnyKamen + s.getX1() * this.rozmer + s.getY1()) == protihrac(hrac) && pomocnyKamen%this.rozmer == this.rozmer-1 && !(s == Smer.HORE || s == Smer.DOLE)) {
                            body = 0;
                            break;
                        }
                        else if (this.aktualnePole.get(pomocnyKamen + s.getX1() * this.rozmer + s.getY1()) == 0) {
                            if (this.moznostiPole.get(pomocnyKamen + s.getX1() * this.rozmer + s.getY1()) == null) {
                                krok = new Kroky(kamen, body, s);
                                this.moznostiPole.set(pomocnyKamen + s.getX1() * this.rozmer + s.getY1(), krok);
                            }
                            else {
                                this.moznostiPole.get(pomocnyKamen + s.getX1() * this.rozmer + s.getY1()).add(kamen);
                                this.moznostiPole.get(pomocnyKamen + s.getX1() * this.rozmer + s.getY1()).add(s);
                                this.moznostiPole.get(pomocnyKamen + s.getX1() * this.rozmer + s.getY1()).setBody(this.moznostiPole.get(pomocnyKamen + s.getX1() * this.rozmer + s.getY1()).getBody() + body);
                            }
                            body = 0;
                            break;
                        }
                    }
                } catch(IndexOutOfBoundsException e){
                    body = 0;
                }
            }
        }
        if(this.isNull())
        {
            this.hratelnostTeraz = false;
        }
    }

    private boolean isNull(){
        for(Kroky i : this.moznostiPole){
            if(i != null){
                return false;
            }
        }
        return true;
    }

    private int protihrac(int hrac){
        if(hrac == 1){
            return 2;
        }
        else{
            return 1;
        }
    }

    public boolean getHratelnostTeraz() {
        return this.hratelnostTeraz;
    }

    public boolean getHratelnostPred() {
        return this.hratelnostPred;
    }

}
