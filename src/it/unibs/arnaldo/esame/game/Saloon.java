package it.unibs.arnaldo.esame.game;

import it.unibs.arnaldo.esame.main.UserInteraction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Saloon {

    public static final int STANDARD = 0;
    public static final int TOURNAMENT = 1;
    private static final int GAME_FEE = 50;

    private Game currentGame;
    private List<Player> lobby;
    private final int mode;

    public Saloon(List<Player> lobby, int mode) {
        this.currentGame = null;
        this.lobby = lobby;
        this.mode = mode;
    }

    public List<Player> getLobby() {
        return lobby;
    }

    public void play() {
        boolean choice;
        do {
            List<Player> available = getAvailablePlayers();
            currentGame = new Game(available);
            currentGame.start();
            //TODO: aggiornamento score, bilanci, ecc... (rispetto alla tabella data nel documento)
            choice = UserInteraction.playAgain();
        } while (choice && canPlay());
        if (!canPlay()) UserInteraction.notEnoughPlayers();
    }

    private List<Player> getAvailablePlayers() {
        List<Player> available = new ArrayList<>();
        for (Player p : lobby) {
            if (p.canPlay(mode))  {
                if (mode == TOURNAMENT) p.decrementBalance(GAME_FEE);
                available.add(p);
            }
        }
        // shuffle so they will always be in different positions
        Collections.shuffle(available);
        return available;
    }

    private boolean canPlay() {
        int counter = 0;
        for (Player player : lobby) {
            if (player.canPlay(mode)) counter++;
        }
        return counter >= 4;
    }
}
