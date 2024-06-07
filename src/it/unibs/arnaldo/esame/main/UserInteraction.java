package it.unibs.arnaldo.esame.main;

import it.kibo.fp.lib.AnsiColors;
import it.kibo.fp.lib.InputData;
import it.kibo.fp.lib.Menu;
import it.unibs.arnaldo.esame.game.Player;
import it.unibs.arnaldo.esame.game.Saloon;
import it.unibs.arnaldo.esame.game.cards.Card;
import it.unibs.arnaldo.esame.game.map.GameMap;

import java.util.*;

public class UserInteraction {

    private static final int TITLE_SLEEP_TIME = 3000;
    private static final int INSTRUCTIONS_SLEEP_TIME = 5000;
    private static final int MIN_PLAYERS = 4;
    private static final int MAX_PLAYERS = 7;
    private static final String GAME_MODE_MESSAGE = "Scegli la modalità di gioco [0 --> standard / 1 --> torneo]: ";
    private static final String NUMBER_OF_PLAYERS_MESSAGE = "Scegli il numero di giocatori [4, 7]: ";
    private static final String PLAYER_NAME_MESSAGE = "giocatore %d, come ti chiami? ";
    private static final String PLAY_AGAIN_MESSAGE = "VUoi giocare ancora? ";
    private static final String REMAINING_STEPS_MESSAGE = "Puoi fare ancora %d passi ";
    private static final String MOVE_AGAIN_MESSAGE = "Vuoi muoverti ancora ?";
    private static final String ACT_AGAIN_MESSAGE = "Vuoi fare un'altra azione? ";
    private static final String FAREWELL_MESSAGE = "Addio, cowboy! ";
    private static final String TITLE = "ESAME PROGRAMMA ARNALDO";
    private static final String WELCOME_MESSAGE = "Benvenuto";
    private static final String INSTRUCTIONS = "Queste dovrebbero essere le regole ma non ho avuto tempo per scriverle :(";
    private static final String NOT_ENOUGH_PEOPLE_MESSAGE = "Non ci sono abbastanza persone per giocare";
    private static final String WANNA_TAUNT_MESSAGE = "Vuoi provocare un altro giocatore? ";
    private static final String MOVEMENT_INSTRUCTIONS = "usa WASD per muoverti (non è implementato, col cavolo): ";
    private static final String MOVEMENT_ERROR = "Non puoi muoverti in questa direzione!!";
    private static final String INPUT_CLEAR_TEXT = "Inserisci il testo da cifrare: ";
    private static final String INPUT_KEY_TEXT = "Inserisci la chiave di cifratura: ";
    private static final String CHOOSE_PLAYER_TITLE = "GIOCATORI";
    private static final String CHOOSE_CARD_TITLE = "CARTE";
    private static final String SHERIFF_MESSAGE = "Lo sceriffo è: %s";
    private static final String ROLE_MESSAGE = "Il tuo ruolo è: %s";
    private static final String HP_MESSAGE = "ti rimangono %d hp";
    private static final String DISTANCE_MESSAGE = "La distanza tra %s e %s è: %d";
    private static final String CARD_MESSAGE = "%s";
    private static final String COLT_MESSAGE = "Hai equipaggiato solo la Colt .45";
    private static final String WEAPON_MESSAGE = "La tua arma equipaggia è: %s";
    private static final String ACTIONS_TITLE = "AZIONI";
    private static final String WINNER_MESSAGE = "Ha vinto: %s, ruolo: %s";
    private static final String[] ACTIONS_ENTRIES = {
        "Visualizza chi è lo sceriffo",
        "Visualizza il mio ruolo",
        "Visualizza le mie carte",
        "Visualizza la distanza tra me e ...",
        "Visualizza i miei punti ferita",
        "Gioca una carta"
    };

    public static void welcome() {
        System.out.println(TITLE);
        waitAndClear(TITLE_SLEEP_TIME);
        printColoredMessage(WELCOME_MESSAGE, AnsiColors.YELLOW);
        System.out.println(INSTRUCTIONS);
        try {
            Menu.wait(INSTRUCTIONS_SLEEP_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static int chooseMode() {
        return InputData.readIntegerBetween(coloredMessage(GAME_MODE_MESSAGE, AnsiColors.CYAN), Saloon.STANDARD, Saloon.TOURNAMENT);
    }

    public static int chooseNumberOfPlayers() {
        return InputData.readIntegerBetween(coloredMessage(NUMBER_OF_PLAYERS_MESSAGE, AnsiColors.CYAN), MIN_PLAYERS, MAX_PLAYERS);
    }

    public static String chooseName(int i) {
        return InputData.readNonEmptyString(coloredMessage(String.format(PLAYER_NAME_MESSAGE, i), AnsiColors.CYAN), true);
    }

    public static boolean playAgain() {
        return InputData.readYesOrNo(coloredMessage(PLAY_AGAIN_MESSAGE, AnsiColors.CYAN));
    }

    public static void movementInstructions() {
        printColoredMessage(MOVEMENT_INSTRUCTIONS, AnsiColors.CYAN);
    }

    public static String inputClearText() {
        return InputData.readNonEmptyString(coloredMessage(INPUT_CLEAR_TEXT, AnsiColors.CYAN), false);
    }

    public static String inputKeyText() {
        return InputData.readNonEmptyString(coloredMessage(INPUT_KEY_TEXT, AnsiColors.CYAN), false);
    }

    public static boolean moveAgain() {
        return InputData.readYesOrNo(coloredMessage(MOVE_AGAIN_MESSAGE, AnsiColors.CYAN));
    }

    public static boolean actAgain() {
        return InputData.readYesOrNo(coloredMessage(ACT_AGAIN_MESSAGE, AnsiColors.CYAN));
    }

    public static boolean wannaTaunt(Player current) {
        return InputData.readYesOrNo(coloredMessage(WANNA_TAUNT_MESSAGE, AnsiColors.CYAN));
    }

    public static void movementError() {
        printColoredMessage(MOVEMENT_ERROR, AnsiColors.RED);
    }

    public static Player choosePlayer(List<Player> players, Player from) {
        List<Player> valid = new ArrayList<>();
        String[] entries = new String[players.size() - 1];
        int i = 0;
        for (Player player : players) {
            if (!player.equals(from)) {
                valid.add(player);
                entries[i++] = player.toString();
            }
        }
        Menu menu = new Menu(CHOOSE_PLAYER_TITLE, entries, false, true, true);
        return valid.get(menu.choose() - 1);
    }

    public static Card chooseCard(List<Card> cards) {
        String[] entries = new String[cards.size()];
        int i = 0;
        for (Card card : cards) {
                entries[i++] = card.toString();
        }
        Menu menu = new Menu(CHOOSE_CARD_TITLE, entries, false, true, true);
        return cards.get(menu.choose() - 1);
    }

    public static void notEnoughPlayers() {
        printColoredMessage(NOT_ENOUGH_PEOPLE_MESSAGE, AnsiColors.RED);
    }

    public static void showSheriff(Player sheriff) {
        printColoredMessage(String.format(SHERIFF_MESSAGE, sheriff.toString()), AnsiColors.CYAN);
    }

    public static void showRole(Player player) {
        printColoredMessage(String.format(ROLE_MESSAGE, player.getRole()), AnsiColors.CYAN);
    }

    public static void showCards(Player player) {
        for (Card card : player.getCards()) {
            printColoredMessage(String.format(CARD_MESSAGE, card.getName()), AnsiColors.YELLOW);
        }
        System.out.println("\n");
        String message = player.getWeapon() == null ? COLT_MESSAGE : String.format(WEAPON_MESSAGE, player.getWeapon().getName());
        printColoredMessage(message, AnsiColors.YELLOW);
    }

    public static void showDistanceBetween(GameMap map, Player p1, Player p2) {
        printColoredMessage(String.format(DISTANCE_MESSAGE, p1.toString(), p2.toString(), map.getDistanceBetween(p1, p2)), AnsiColors.CYAN);
    }

    public static void showHp(Player player) {
        printColoredMessage(String.format(HP_MESSAGE, player.getHp()), AnsiColors.CYAN);
    }

    public static void showWinners(List<Player> winners) {
        for (Player winner : winners) {
            printColoredMessage(String.format(WINNER_MESSAGE, winner.getName(), winner.getRole()), AnsiColors.GREEN);
        }
    }

    public static void farewell() {
        printColoredMessage(FAREWELL_MESSAGE, AnsiColors.YELLOW);
    }

    public static String coloredMessage(String message, AnsiColors color){
        return color + message + AnsiColors.RESET;
    }

    public static void printColoredMessage(String message, AnsiColors color) {
        System.out.println(coloredMessage(message, color));
    }

    public static void waitAndClear(int sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.print(AnsiColors.CLEAR);
        System.out.flush();
    }

    public static int chooseActions(Player player) {
        Menu menu = new Menu(ACTIONS_TITLE, ACTIONS_ENTRIES, false, true, true);
        return menu.choose() - 1;
    }

    public static void printMap(GameMap map, Player player, int steps) {
        char[][] grid = map.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (i == map.getPosition(player).getI() &&
                    j == map.getPosition(player).getJ()) {
                    System.out.print(coloredMessage(String.format("%c\t", grid[i][j]), AnsiColors.YELLOW));
                } else System.out.printf("%c\t", grid[i][j]);
            }
            System.out.println('\n');
        }
        System.out.println('\n');
        printColoredMessage(String.format(REMAINING_STEPS_MESSAGE, steps), AnsiColors.YELLOW);
        System.out.println('\n');
    }
}

