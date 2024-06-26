package it.unibs.arnaldo.esame.game.map;

public class Position {

    private static final String TO_STRING = "(%d, %d)";

    private int i;
    private int j;

    public Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    @Override
    public String toString() {
        return String.format(TO_STRING, i, j);
    }
}
