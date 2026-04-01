import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Bullet class represents a projectile in the game.
 * It stores the position, direction, and owner of the bullet,
 * and handles updating its movement visually.
 */
public class Bullet {
    public double x, y; // Current position of the bullet
    public double dx, dy; // Direction and speed of the bullet
    public ImageView imageView; // ImageView to display the bullet sprite
    public boolean isPlayerBullet; // Flag to indicate if the bullet was fired by the player

    /**
     * Constructs a new Bullet with the specified position, velocity, image, and ownership.
     *
     * @param x               Initial x-coordinate
     * @param y               Initial y-coordinate
     * @param dx              Horizontal speed component
     * @param dy              Vertical speed component
     * @param imgPath         Path to the bullet image
     * @param isPlayerBullet  True if fired by player, false if by enemy
     */
    public Bullet(double x, double y, double dx, double dy, String imgPath, boolean isPlayerBullet) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.isPlayerBullet = isPlayerBullet;

        Image img = new Image(imgPath);
        imageView = new ImageView(img);
        imageView.setFitWidth(10);
        imageView.setFitHeight(10);
        imageView.setX(x);
        imageView.setY(y);
    }

    /**
     * Updates the bullet's position based on its velocity.
     * Also updates the visual position of the ImageView on screen.
     */
    public void move() {
        x += dx;
        y += dy;
        imageView.setX(x);
        imageView.setY(y);
    }
}
