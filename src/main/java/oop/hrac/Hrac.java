package oop.hrac;

import lombok.Getter;
import lombok.Setter;

public class Hrac {
    @Setter
    @Getter
    private int cisloHraca;
    @Setter
    @Getter
    private int body;

    public Hrac(int cisloHraca) {
        this.cisloHraca = cisloHraca;
        this.body = 2;
    }
}
