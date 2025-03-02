package minicraft;

import minicraft.view.CardinalPoints;
import minicraft.view.Player;
import minicraft.view.World;

import javax.smartcardio.Card;
import javax.swing.*;

public class Minicraft {
    public static void main(String[] args) {
        Player p = new Player(4, 4, CardinalPoints.E);
        World world = new World(10, 20);
        world.setPlayer(p);

        JFrame f = new JFrame("Minicraft Demo - v.0.1");
        f.add(world);
        f.setSize(900, 700);
        f.setVisible(true);
        f.pack();
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
