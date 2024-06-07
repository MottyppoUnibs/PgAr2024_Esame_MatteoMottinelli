package it.unibs.arnaldo.esame.game.map;

import it.kibo.fp.lib.RandomDraws;
import it.unibs.arnaldo.esame.game.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameMap {

    private static final int GRID_SIZE = 6;
    public static final char FREE = '-';
    public static final char OBSTACLE = '*';

    private Map<Player, Position> positions;
    private char[][] grid;

    public GameMap(List<Player> players) {
        grid = new char[GRID_SIZE][GRID_SIZE];
        initGrid();
        positions = new HashMap<>();
        initPositions(players);
    }

    public Position getPosition(Player player) {
        return positions.get(player);
    }

    public char[][] getGrid() {
        return this.grid;
    }

    private void initGrid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if ((i == 1 && j == 1) ||
                        (i == 2 && j == 5) ||
                        (i == 3 && j == 5) ||
                        (i == 4 && j == 2)) grid[i][j] = OBSTACLE;
                else grid[i][j] = FREE;
            }
        }
    }

    private void initPositions(List<Player> players) {
        for (Player player : players) {
            int i, j;
            do {
                i = RandomDraws.drawInteger(0, 5);
                j = RandomDraws.drawInteger(0, 5);
            } while (!isFree(i, j));
            positions.put(player, new Position(i, j));
            grid[i][j] = player.getName().charAt(0);
        }
    }

    private boolean isFree(int i, int j) {
        return grid[i][j] == FREE;
    }

    public boolean move(Player player) {
        //TODO: movement management
        return true;
    }

    public int getDistanceBetween(Player p1, Player p2) {
        Position pos1 = positions.get(p1);
        Position pos2 = positions.get(p2);
        int[][] dist = new int[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (i == pos1.getI() && j == pos1.getJ()) dist[i][j] = 0;
                else dist[i][j] = -1;
            }
        }
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (dist[i][j] == 0) {
                    explore(dist, i, j, pos2.getI(), pos2.getJ());
                    break;
                }
            }
        }
        return dist[pos2.getI()][pos2.getJ()] / 2;
    }

    //NON SO NEMMENO SE FUNZIONA E LO STO FACENDO A 10 MINUTI DALLA FINE, E'
    //TIPO IL METODO RICORSIVO PIU BRUTTO CHE ABBIA MAI SCRITTO PERO NON FATECI CASO
    private void explore(int[][] dist, int i, int j, int ti, int tj) {
        if (i < 0 || j < 0 || i >= GRID_SIZE || j >= GRID_SIZE ||
            (i == ti && i == tj)) return;
        if (dist[i][j] + 1 < dist[i - 1][j]) dist[i - 1][j] = dist[i][j] + 1;
        else explore (dist, i - 1, j, ti, tj);
        if (dist[i][j] + 1 < dist[i + 1][j]) dist[i + 1][j] = dist[i][j] + 1;
        else explore (dist, i + 1, j, ti, tj);
        if (dist[i][j] + 1 < dist[i][j - 1]) dist[i][j - 1] = dist[i][j] + 1;
        else explore (dist, i, j - 1, ti, tj);
        if (dist[i][j] + 1 < dist[i][j + 1]) dist[i][j + 1] = dist[i][j] + 1;
        else explore (dist, i, j + 1, ti, tj);
    }

}
