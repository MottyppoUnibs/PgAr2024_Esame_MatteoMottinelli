package it.unibs.arnaldo.esame.game.cards;

import java.util.*;

public class Deck {

    public static final String BANG = "BANG!";
    public static final String MANCATO = "Mancato!";
    public static final String COLT_45 = "Colt .45";
    public static final String SCHOFIELD = "Schofield";
    public static final String REMINGTON = "Remington";
    public static final String REV_CARABINE = "Rev. Carabine";
    public static final String WINCHESTER = "Winchester";
    private static final int BANG_AMOUNT = 50;
    private static final int MANCATO_AMOUNT = 24;
    private static final int SCHOFIELD_AMOUNT = 3;
    private static final int SCHOFIELD_RANGE = 2;
    private static final int REMINGTON_RANGE = 3;
    private static final int REV_CARABINE_RANGE = 4;
    private static final int WINCHESTER_RANGE = 5;

    private Deque<Card> cards;

    public Deck() {
        cards = new ArrayDeque<>();
        // BANG!
        for (int i = 0; i < BANG_AMOUNT; i++) cards.addFirst(new Card(Card.GIOCA_E_SCARTA, BANG));
        // Mancato!
        for (int i = 0; i < MANCATO_AMOUNT; i++) cards.addFirst(new Card(Card.GIOCA_E_SCARTA, MANCATO));
        // Schofield
        for (int i = 0; i < SCHOFIELD_AMOUNT; i++) cards.addFirst(new Weapon(Card.EQUIPAGGIABILI, SCHOFIELD, SCHOFIELD_RANGE));
        // Remington
        cards.addFirst(new Weapon(Card.EQUIPAGGIABILI, REMINGTON, REMINGTON_RANGE));
        // Rev. Carabine
        cards.addFirst(new Weapon(Card.EQUIPAGGIABILI, REV_CARABINE, REV_CARABINE_RANGE));
        // Winchester
        cards.addFirst(new Weapon(Card.EQUIPAGGIABILI, WINCHESTER, WINCHESTER_RANGE));
        shuffle();
    }

    public Deck(Deque<Card> discarded) {
        cards = discarded;
        shuffle();
    }

    private void shuffle() {
        List<Card> list = new ArrayList<>();
        while (!cards.isEmpty()) {
            list.add(cards.removeFirst());
        }
        Collections.shuffle(list);
        for (Card c : list) {
            cards.addFirst(c);
        }
    }

    public Card draw() {
        return cards.removeFirst();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
