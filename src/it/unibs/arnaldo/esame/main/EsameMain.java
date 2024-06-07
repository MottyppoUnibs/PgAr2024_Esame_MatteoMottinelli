package it.unibs.arnaldo.esame.main;

import it.unibs.arnaldo.esame.game.Player;
import it.unibs.arnaldo.esame.game.Saloon;
import it.unibs.arnaldo.esame.io.Ranking;

import java.util.ArrayList;
import java.util.List;

//TODO: DOC
public class EsameMain {

    public static void main(String[] args) {
        UserInteraction.welcome();
        // choose mode
        int mode = UserInteraction.chooseMode();
        // choose and init players
        int numberOfPlayers = UserInteraction.chooseNumberOfPlayers();
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(new Player(UserInteraction.chooseName(i + 1), mode));
        }
        // let's start a gaming session
        Saloon saloon = new Saloon(players, mode);
        saloon.play();
        // writing the rankings
        Ranking ranking = new Ranking(saloon.getLobby());
        ranking.writeRanking();
        UserInteraction.farewell();
    }
}