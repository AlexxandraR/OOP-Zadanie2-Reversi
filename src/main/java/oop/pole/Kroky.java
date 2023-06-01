package oop.pole;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Kroky {
    @Getter
    private final List<Integer> indexStareho;
    @Getter
    @Setter
    private int body;
    @Getter
    private final List<Smer> smer;

    public Kroky(int indexStareho, int body, Smer smer) {
        this.indexStareho = new ArrayList<>() ;
        this.indexStareho.add(indexStareho);
        this.body = body;
        this.smer = new ArrayList<>();
        this.smer.add(smer);
    }

    public void add(int index){
        this.indexStareho.add(index);
    }

    public void add(Smer smer){
        this.smer.add(smer);
    }

}