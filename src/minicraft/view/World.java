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
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    player.moveNorth();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    player.moveSouth();
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.moveWest();
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.moveEast();
                }
                world[player.getY()][player.getX()].setContainsPlayer(true);
            }
        });
    }

    private void generateWorld() {
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                Tile t = new Tile(this, getRandomBiomeType(j, i));
                world[j][i] = t;
                add(t);
            }
        }
    }

    private BiomeType getRandomBiomeType(int row, int col) {
        int waterBorder = 2;
        if (row < waterBorder || col < waterBorder || row >= rows-waterBorder || col >= cols-waterBorder) {
            return BiomeType.WATER;
        }
        return BiomeType.GRASS;
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
        if (x > 0 && y > 0 && x < cols && y < rows) {
            return world[y][x].isWalkable();
        }
        return false;
    }
}
