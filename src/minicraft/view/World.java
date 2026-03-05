package minicraft.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class World extends JPanel {

    private Random rnd = new Random();
    private Tile[][] world;
    private int rows;
    private int cols;
    private Player player;

    public World(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.world = new Tile[rows][cols];

        setLayout(new GridLayout(rows, cols));

        setMinimumSize(new Dimension(cols * Tile.PIXEL_SIZE, rows * Tile.PIXEL_SIZE));
        setMaximumSize(getMinimumSize());
        setPreferredSize(getMinimumSize());

        generateWorld();
        addListeners();
        setFocusable(true);
    }

    private void addListeners() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                world[player.getY()][player.getX()].setContainsPlayer(false);
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar() == 'w') {
                    player.moveNorth();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar() == 's') {
                    player.moveSouth();
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'a') {
                    player.moveWest();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd') {
                    player.moveEast();
                }
                world[player.getY()][player.getX()].setContainsPlayer(true);
            }
        });
    }

    private void generateWorld() {
        // grass everywhere and stone with 0.15 probability
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                BiomeType biome = BiomeType.GRASS;
                if (rnd.nextDouble() < 0.15) {
                    biome = BiomeType.STONE;
                }
                Tile t = new Tile(this, biome);
                world[j][i] = t;
                add(t);
            }
        }
        // grass into water with 0.05 probability
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                if (world[j][i].getBiomeType() == BiomeType.GRASS && rnd.nextDouble() < 0.05) {
                    world[j][i].setBiomeType(BiomeType.WATER);
                }
            }
        }
        // sand next to water with 0.75 probability
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                if (world[j][i].getBiomeType() == BiomeType.GRASS && waterNearby(i, j) && rnd.nextDouble() < 0.75) {
                    world[j][i].setBiomeType(BiomeType.SAND);
                }
            }
        }
    }

    private boolean waterNearby(int i, int j) {
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx != 0 || dy != 0) {
                    int nx = i + dx;
                    int ny = j + dy;
                    if (nx >= 0 && nx < cols && ny >= 0 && ny < rows) {
                        if (world[ny][nx].getBiomeType() == BiomeType.WATER) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.player.setWorld(this);
        world[player.getY()][player.getX()].setContainsPlayer(true);
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean isTileWalkable(int x, int y) {
        if (x >= 0 && y >= 0 && x < cols && y < rows) {
            return world[y][x].isWalkable();
        }
        return false;
    }
}
