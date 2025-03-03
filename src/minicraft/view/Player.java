package minicraft.view;

import javax.imageio.ImageIO;
import javax.smartcardio.Card;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player {

    private World world;
    private int x;
    private int y;
    private CardinalPoints direction;
    private BufferedImage imagePlayer;

    public Player(int x, int y, CardinalPoints direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;

        try {
            this.imagePlayer = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player.png"));
        } catch (IOException e) {
            this.imagePlayer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
        }
    }

    public void moveNorth() {
        if (world.isTileWalkable(x, y - 1)) {
            y = Math.max(0, y - 1);
        }
        direction = CardinalPoints.N;
    }

    public void moveSouth() {
        if (world.isTileWalkable(x, y + 1)) {
            y++;
        }
        direction = CardinalPoints.S;
    }

    public void moveEast() {
        if (world.isTileWalkable(x + 1, y)) {
            x++;
        }
        direction = CardinalPoints.E;
    }

    public void moveWest() {
        if (world.isTileWalkable(x - 1, y)) {
            x = Math.max(0, x - 1);
        }
        direction = CardinalPoints.W;
    }

    public BufferedImage getImagePlayer() {
        return rotateImage(imagePlayer, direction.getAngle());
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public static BufferedImage rotateImage(BufferedImage image, double angle) {
        int width = image.getWidth();
        int height = image.getHeight();

        // Calculate the new dimensions of the rotated image
        double radians = Math.toRadians(angle); // Convert degrees to radians
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int newWidth = (int) Math.floor(width * cos + height * sin);
        int newHeight = (int) Math.floor(height * cos + width * sin);

        // Create a new image with the new dimensions and a transparent background
        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotatedImage.createGraphics();

        // Apply rotation transformation
        AffineTransform transform = new AffineTransform();
        transform.translate((newWidth - width) / 2, (newHeight - height) / 2);
        transform.rotate(radians, width / 2.0, height / 2.0);

        // Draw the rotated image
        g2d.setTransform(transform);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }

    public void setWorld(World world) {
        this.world = world;
    }
}
