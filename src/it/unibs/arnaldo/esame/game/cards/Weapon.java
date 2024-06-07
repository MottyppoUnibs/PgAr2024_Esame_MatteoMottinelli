package it.unibs.arnaldo.esame.game.cards;

public class Weapon extends Card {

    private final int range;

    public Weapon(int type, String name, int range) {
        super(type, name);
        this.range = range;
    }

    public int getRange() {
        return range;
    }
}
