package it.unibs.arnaldo.esame.game;

import it.kibo.fp.lib.AnsiColors;
import it.unibs.arnaldo.esame.game.cards.Card;
import it.unibs.arnaldo.esame.game.cards.Deck;
import it.unibs.arnaldo.esame.game.cards.Weapon;
import it.unibs.arnaldo.esame.game.map.GameMap;
import it.unibs.arnaldo.esame.game.taunt.Cypher;
import it.unibs.arnaldo.esame.game.taunt.Taunt;
import it.unibs.arnaldo.esame.main.UserInteraction;

import java.util.*;

public class Game {

    private static final int MIN_OUTLAWS = 2;
    private static final int CARDS_FOR_TURN = 2;
    private static final int CARDS_FOR_KILL = 3;
    private static final int SHOW_SHERIFF = 0;
    private static final int SHOW_CARDS = 1;
    private static final int SHOW_DISTANCE = 2;
    private static final int SHOW_HP = 3;
    private static final int PLAY_CARD = 4;

    private List<Player> players;
    private Player sheriff;
    private Player forsaken;
    private List<Player> outlaws;
    private List<Player> vices;
    private Deck deck;
    private Deque<Card> discarded;
    private GameMap map;
    private Role winner;

    public Game(List<Player> players) {
        this.players = players;
        deck = new Deck();
        discarded = new ArrayDeque<>();
        map = new GameMap(players);
        winner = null;
        this.outlaws = new ArrayList<>();
        this.vices = new ArrayList<>();
    }

    public void start() {
        roleAssignment();
        cardsAssignment();
        play();
        end();
    }

    private void play() {
        int index = players.indexOf(sheriff);
        do {
            Player current = players.get(index);
            current.setHasBanged(false);
            showTaunts(current);
            draw(current, CARDS_FOR_TURN);
            move(current);
            taunt(current);
            turn(current);
            discard(current);
            //to his left --> the next one in the array
            index = (index + 1) % players.size();
        } while (canPlay());
        winner = decideWinner();
    }

    private void end() {
        List<Player> winners = new ArrayList<>();
        for (Player player : players) {
            if (player.getRole().equals(winner)) winners.add(player);
        }
        UserInteraction.showWinners(winners);
    }

    private Role decideWinner() {
        if (outlaws.isEmpty() && !forsaken.isAlive()) return Role.SHERIFF;
        if (!sheriff.isAlive() && outlaws.isEmpty() && vices.isEmpty()) return Role.FORSAKEN;
        return Role.OUTLAW;
    }

    private void roleAssignment() {
        List<Role> roles = new ArrayList<>();
        roles.add(Role.SHERIFF);
        roles.add(Role.FORSAKEN);
        for (int i = 0; i < MIN_OUTLAWS; i++) roles.add(Role.OUTLAW);
        if (players.size() >= 5) roles.add(Role.VICE);
        if (players.size() >= 6) roles.add(Role.OUTLAW);
        if (players.size() >= 7) roles.add(Role.VICE);
        Collections.shuffle(roles);
        for (int i = 0; i < players.size(); i++) {
            players.get(i).start(roles.get(i));
            switch (roles.get(i)) {
                case Role.SHERIFF:
                    sheriff = players.get(i);
                    break;
                case Role.FORSAKEN:
                    forsaken = players.get(i);
                    break;
                case Role.OUTLAW:
                    outlaws.add(players.get(i));
                    break;
                case Role.VICE:
                    vices.add(players.get(i));
                    break;
            }
        }
    }

    private void cardsAssignment() {
        for (Player player : players) {
            for (int i = 0; i < player.getHp(); i++) {
                player.drawCard(deck.draw());
            }
        }
    }

    private void showTaunts(Player player) {
        if (!player.getTaunts().isEmpty()) player.showTaunts();
    }

    private void draw(Player player, int amount) {
        for (int i = 0; i < amount; i++) {
            if (!deck.isEmpty()) {
                player.drawCard(deck.draw());
            } else {
                createDeckFromDiscarded();
                player.drawCard(deck.draw());
            }
        }
    }

    private void move(Player player) {
        UserInteraction.movementInstructions();
        int steps = player.getHp();
        boolean choiche;
        do {
            UserInteraction.printMap(map, player, steps);
            choiche = UserInteraction.moveAgain();
            if (choiche) {
                if (!map.move(player)) {
                    UserInteraction.movementError();
                }
            }
            steps--;
        } while (steps > 0 && choiche);
    }

    private void taunt(Player player) {
        if (UserInteraction.wannaTaunt(player)) {
            Player target = UserInteraction.choosePlayer(players, player);
            String clearText = UserInteraction.inputClearText();
            String keyText = UserInteraction.inputKeyText();
            Taunt taunt = Cypher.createTaunt(player, clearText, keyText);
            target.addTaunt(taunt);
        }
    }

    private void turn(Player player) {
        boolean choiche;
        do {
            int action = UserInteraction.chooseActions(player);
            switch (action) {
                case SHOW_SHERIFF:
                    UserInteraction.showSheriff(sheriff);
                    break;
                case SHOW_CARDS:
                    UserInteraction.showCards(player);
                    break;
                case SHOW_DISTANCE:
                    UserInteraction.showDistanceBetween(map, player, UserInteraction.choosePlayer(players, player));
                    break;
                case SHOW_HP:
                    UserInteraction.showHp(player);
                    break;
                case PLAY_CARD:
                    playCard(player);
                    break;
            }
            choiche = UserInteraction.actAgain();
        } while (choiche);
    }

    private void playCard(Player player) {
        Card card = UserInteraction.chooseCard(player.getCards());
        switch (card.getType()) {
            case Card.GIOCA_E_SCARTA:
                if (card.getName().equals(Deck.BANG)) {
                    if (!player.hasBanged()) {
                        Player target = UserInteraction.choosePlayer(players, player);
                        int range = player.getWeapon() == null ? 1 : player.getWeapon().getRange();
                        if (map.getDistanceBetween(player, target) <= range) {
                            if (hasMancato(target)) {
                                UserInteraction.printColoredMessage("Il giocatore ha schivato con la carta Mancato!", AnsiColors.YELLOW);
                                target.discardCard(new Card(Card.GIOCA_E_SCARTA, Deck.MANCATO));
                            } else {
                                UserInteraction.printColoredMessage("Hai colpito il bersaglio", AnsiColors.YELLOW);
                                target.hit();
                                if (!target.isAlive()) playerDead(target, player);
                            }
                        } else {
                            UserInteraction.printColoredMessage("Bersaglio troppo distante", AnsiColors.RED);
                        }
                        player.setHasBanged(true);
                    }
                } else {
                    UserInteraction.printColoredMessage("Non puoi giocare questa carta, non ora", AnsiColors.RED);
                }
                break;
            case Card.EQUIPAGGIABILI:
                if (player.getWeapon() != null) {
                    discarded.addFirst(player.getWeapon());
                }
                player.setWeapon((Weapon)card);
                break;
        }
    }

    private void discard(Player player) {
        while (player.getCards().size() > player.getHp()) {
            player.discardCard(UserInteraction.chooseCard(player.getCards()));
        }
    }

    private boolean hasMancato(Player player) {
        for (Card card : player.getCards()) {
            if (card.getName().equals(Deck.MANCATO)) return true;
        }
        return false;
    }

    private void playerDead(Player dead, Player from) {
        System.out.printf("%s è morto, il suo ruolo era: %s", dead, dead.getRole());
        switch (dead.getRole()) {
            case Role.OUTLAW:
                outlaws.remove(dead);
                draw(from, CARDS_FOR_KILL);
                break;
            case Role.VICE:
                vices.remove(dead);
                if (from.getRole().equals(Role.SHERIFF)) {
                    sheriff.clearCards();
                }
                break;
        }
        players.remove(dead);
    }

    private void createDeckFromDiscarded() {
        deck = new Deck(discarded);
        discarded.clear();
    }

    private boolean canPlay() {
        if (!sheriff.isAlive() || (outlaws.size() == 0 && !forsaken.isAlive())) return false;
        return true;
    }
}
