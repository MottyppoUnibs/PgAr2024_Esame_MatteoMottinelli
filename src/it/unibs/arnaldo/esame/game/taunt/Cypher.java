package it.unibs.arnaldo.esame.game.taunt;

import it.unibs.arnaldo.esame.game.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cypher {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public static Taunt createTaunt(Player sender, String clearText, String keyText) {
        List<String> clear = Arrays.asList(clearText.split(" "));
        List<String> key = Arrays.asList(keyText.split(" "));
        List<String> crypted = new ArrayList<>();
        int n = clear.size();
        for (int i = 0; i < n; i++) {
            int j = 0;
            StringBuilder builder = new StringBuilder();
            for (char c : clear.get(i).toCharArray()) {
                int increment = ALPHABET.indexOf(key.get(i).charAt(j));
                int index = 0;
                if (ALPHABET.indexOf(c) + increment >= ALPHABET.length()) {
                    index = increment - (ALPHABET.length() - ALPHABET.indexOf(c));
                } else {
                    index = ALPHABET.indexOf(c) + increment;
                }
                builder.append(ALPHABET.charAt(index));
                j = (j + 1) % key.get(i).length();
            }
            crypted.add(builder.toString());
        }
        StringBuilder encryptedText = new StringBuilder();
        for (String word : crypted) {
            encryptedText.append(word);
            encryptedText.append(' ');
        }
        //remove last space
        encryptedText.deleteCharAt(encryptedText.length() - 1);
        return new Taunt(sender, encryptedText.toString());
    }
}
