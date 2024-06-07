package it.unibs.arnaldo.esame.game.cards;

public class Card {

    public static final int GIOCA_E_SCARTA = 0;
    public static final int EQUIPAGGIABILI = 1;

    private final int type;
    private final String name;

    public Card(int type, String name) {
        this.name = name;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public boolean equals(Card c) {
        return this.name.equals(c.getName());
    }
}
