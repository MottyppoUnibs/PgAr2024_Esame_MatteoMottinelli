package it.unibs.arnaldo.esame.io;

import it.kibo.fp.lib.AnsiColors;
import it.unibs.arnaldo.esame.game.Player;
import it.unibs.arnaldo.esame.main.UserInteraction;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Ranking {

    private static final String FILE = "resources/ranking.xml";
    private static final String INIT_ERROR = "Error in initializing the writer:\n";
    private static final String WRITING_ERROR = "Error while writing!\n";
    private static final String PLAYERS = "players";
    private static final String PLAYER = "player";
    private static final String NAME = "name";
    private static final String BALANCE = "balance";
    private static final String GAMES_PLAYED = "games_played";
    private static final String SCORE = "score";

    private List<Player> players;

    public Ranking(List<Player> players) {
        this.players = players;
        Collections.sort(players);
    }

    public void writeRanking() {
        XMLOutputFactory xmlof = null;
        XMLStreamWriter xmlw = null;
        try (FileOutputStream writer = new FileOutputStream(FILE)) {
            xmlof = XMLOutputFactory.newInstance();
            xmlw = xmlof.createXMLStreamWriter(writer, "UTF-8");
            try {
                xmlw.writeStartDocument("UTF-8", "1.0");
                xmlw.writeCharacters("\n");
                xmlw.writeStartElement(PLAYERS);
                xmlw.writeCharacters("\n");

                for (Player player : players) writePlayer(xmlw, player);

                xmlw.writeEndElement();
                xmlw.writeEndDocument();
                xmlw.flush();
                xmlw.close();
            } catch (XMLStreamException e) {
                UserInteraction.printColoredMessage(WRITING_ERROR, AnsiColors.RED);
            }
        } catch (XMLStreamException | IOException e) {
            UserInteraction.printColoredMessage(INIT_ERROR + e.getMessage(), AnsiColors.RED);
        }
    }

    private void writePlayer(XMLStreamWriter xmlw, Player player) throws XMLStreamException {
        xmlw.writeCharacters("\t");
        xmlw.writeEmptyElement(PLAYER);
        xmlw.writeAttribute(NAME, player.getName());
        xmlw.writeAttribute(BALANCE, String.valueOf(player.getBalance()));
        xmlw.writeAttribute(GAMES_PLAYED, String.valueOf(player.getGamesPlayed()));
        xmlw.writeAttribute(SCORE, String.format("%.2f", player.getScore()));
        xmlw.writeCharacters("\n");
    }
}
