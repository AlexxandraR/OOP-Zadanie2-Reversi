package oop.pole;

import lombok.Getter;

public enum Smer {
    HORE(-1, 0),
    DOLE(1, 0),
    VLAVO(0, -1),
    VPRAVO(0, 1),
    HOREVLAVO(-1, -1),
    HOREVPRAVO(-1, 1),
    DOLEVLAVO(1, -1),
    DOLEVPRAVO(1, 1);

    @Getter
    private final int x1;
    @Getter
    private final int y1;

    Smer(int x1, int y1) {
        this.x1 = x1;
        this.y1 = y1;
    }
}
