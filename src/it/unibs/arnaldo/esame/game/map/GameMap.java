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
        boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                dist[i][j] = Integer.MAX_VALUE;
            }
        }
        int distance = 0;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (i == pos1.getI() && j == pos1.getJ()) {
                    distance = findPath(dist, visited, i, j, pos2);
                    break;
                }
            }
        }
        return distance / 2;
    }

    //TODO: NON FUNZIONA - NON TESTARE LA DISTANZA
    private int findPath(int[][] dist, boolean[][] visited, int i, int j, Position target) {
        if (i < 0 || j < 0 || i >= GRID_SIZE || j >= GRID_SIZE || visited[i][j]) return 0;
        if (i == target.getI() && j == target.getJ()) return 0;
        visited[i][j] = true;
        int min = Integer.MAX_VALUE;
        min = Math.min(min, findPath(dist, visited, i - 1, j, target));
        min = Math.min(min, findPath(dist, visited, i - 1, j, target));
        min = Math.min(min, findPath(dist, visited, i, j - 1, target));
        min = Math.min(min, findPath(dist, visited, i, j + 1, target));
        min++;
        dist[i][j] = min;
        return min;
    }

}
