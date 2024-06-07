package it.unibs.arnaldo.esame.game;

import it.kibo.fp.lib.AnsiColors;
import it.unibs.arnaldo.esame.game.cards.Card;
import it.unibs.arnaldo.esame.game.cards.Weapon;
import it.unibs.arnaldo.esame.game.taunt.Taunt;
import it.unibs.arnaldo.esame.main.UserInteraction;

import java.util.ArrayList;
import java.util.List;

public class Player implements Comparable<Player> {

    private static final int SHERIFF_HP = 5;
    private static final int STANDARD_HP = 4;
    private static final int TOURNAMENT_BALANCE = 500;
    private static final int STANDARD_BALANCE = 0;

    private boolean hasBanged;
    private int hp;
    private final String name;
    private int balance;
    private int gamesPlayed;
    private double score;
    private List<Card> cards;
    private Weapon weapon;
    private Role role;
    private List<Taunt> taunts;

    public Player(String name, int mode) {
        this.name = name;
        this.balance = mode == Saloon.TOURNAMENT ? TOURNAMENT_BALANCE : STANDARD_BALANCE;
        this.gamesPlayed = 0;
        this.score = 0;
    }

    public int getHp() {
        return hp;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public double getScore() {
        return score;
    }

    public List<Card> getCards() {
        return cards;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Role getRole() {
        return role;
    }

    public List<Taunt> getTaunts() {
        return taunts;
    }

    public void start(Role role) {
        hp = role.equals(Role.SHERIFF) ? SHERIFF_HP : STANDARD_HP;
        hasBanged = false;
        cards = new ArrayList<>();
        weapon = null;
        this.role = role;
        taunts = new ArrayList<>();
    }

    public void incrementBalance(int amount) {
        this.balance += amount;
    }

    public void clearCards() {
        this.cards.clear();
        this.weapon = null;
    }

    public void hit() {
        hp -= 1;
    }

    public void decrementBalance(int amount) {
        this.balance -= amount;
    }

    public boolean hasBanged() {
        return hasBanged;
    }

    public void setHasBanged(boolean hasBanged) {
        this.hasBanged = hasBanged;
    }

    public void drawCard(Card card) {
        cards.add(card);
    }

    public void discardCard(Card card) {
        cards.remove(card);
    }

    public void addTaunt(Taunt taunt) {
        taunts.add(taunt);
    }

    public void showTaunts() {
        for (Taunt taunt : taunts) {
            UserInteraction.printColoredMessage(taunt.toString(), AnsiColors.CYAN);
        }
        taunts.clear();
    }

    public boolean canPlay(int mode) {
        if (mode == Saloon.TOURNAMENT && balance < 50) return false;
        return true;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Player p) {
        if (this.score < p.getScore()) return 1;
        else if (this.score == p.getScore()) {
            // minus for descending order
            return -(this.name.compareTo(p.getName()));
        } else return -1;
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public boolean equals(Player p) {
        return this.name.equals(p.getName());
    }
}
