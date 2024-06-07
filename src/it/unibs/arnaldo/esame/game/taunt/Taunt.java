package it.unibs.arnaldo.esame.game.taunt;

import it.unibs.arnaldo.esame.game.Player;

//TODO: DOC - equals
public class Taunt {

    private static final String TO_STRING = "%s ti sta provocando: \"%s\"";

    private Player sender;
    private String text;

    public Taunt(Player sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    public Player getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return String.format(TO_STRING, sender, text);
    }
}
