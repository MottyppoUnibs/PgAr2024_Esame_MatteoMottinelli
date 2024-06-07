import it.kibo.fp.lib.AnsiColors;
import it.unibs.arnaldo.esame.game.Player;
import it.unibs.arnaldo.esame.game.Saloon;
import it.unibs.arnaldo.esame.game.map.GameMap;
import it.unibs.arnaldo.esame.game.taunt.Cypher;
import it.unibs.arnaldo.esame.io.Ranking;
import it.unibs.arnaldo.esame.main.UserInteraction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class EsameTest {
    @Test
    public void cypherTest() {
        Player p = new Player("Mottyppo", Saloon.STANDARD);
        String testo = "i love this competition very much";
        String key = "bugged resolution into xplosive impossible algorithm";
        System.out.println(Cypher.createTaunt(p, testo, key));
        assertTrue(true);
    }

    @Test
    public void rankingStandardTest() {
        UserInteraction.printColoredMessage("TEST STANDARD MOD: ", AnsiColors.GREEN);
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            players.add(new Player("Pippo", Saloon.STANDARD));
        }
        Ranking ranking = new Ranking(players);
        ranking.writeRanking();
        assertTrue(true);
    }

    @Test
    public void rankingTournamentTest() {
        UserInteraction.printColoredMessage("TEST TOURNAMENT MOD: ", AnsiColors.GREEN);
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            players.add(new Player("Pippo", Saloon.TOURNAMENT));
        }
        Ranking ranking = new Ranking(players);
        ranking.writeRanking();
        assertTrue(true);
    }

    @Test
    public void mapPrintingTest() {
        UserInteraction.printColoredMessage("TEST MAP PRINTING: ", AnsiColors.GREEN);
        List<Player> players = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            players.add(new Player("Motty", Saloon.TOURNAMENT));
        }
        GameMap map = new GameMap(players);
        UserInteraction.printMap(map, players.get(0), players.get(0).getHp());
        assertTrue(true);
    }
}
