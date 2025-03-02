package minicraft.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Tile extends JPanel {

    public static final int PIXEL_SIZE = 64;

    private World world;
    private BiomeType type;
    private BufferedImage image;
    private boolean containsPlayer;

    public Tile(World world, BiomeType biomeType) {
        super(true);

        this.world = world;
        this.type = biomeType;
        try {
            this.image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(this.type.getPictureFile()));
        } catch (IOException e) {
            this.image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        }
        setMinimumSize(new Dimension(PIXEL_SIZE, PIXEL_SIZE));
        setMaximumSize(getMinimumSize());
        setPreferredSize(getMinimumSize());
    }

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawImage(image, 0, 0, null);

        if (containsPlayer) {
            g2d.drawImage(world.getPlayer().getImagePlayer(), 0, 0, null);
        }
    }

    public boolean isWalkable() {
        return type.isWalkable();
    }

    public void setContainsPlayer(boolean containsPlayer) {
        this.containsPlayer = containsPlayer;
        repaint();
    }
}
