import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Explosion class represents a visual explosion effect in the game.
 * It uses an ImageView to display a static explosion image
 * at a given location with specified dimensions.
 */
public class Explosion {
    public ImageView imageView; // ImageView for explosion sprite

    /**
     * Constructor for a small default-sized explosion (16x16).
     *
     * @param x        X position of the explosion
     * @param y        Y position of the explosion
     * @param imgPath  Path to the explosion image
     */
    public Explosion(double x, double y, String imgPath) {
        this(x, y, imgPath, 16, 16);
    }

    /**
     * Constructor to create an explosion with custom size.
     *
     * @param x        X position of the explosion
     * @param y        Y position of the explosion
     * @param imgPath  Path to the explosion image
     * @param width    Width of the explosion image
     * @param height   Height of the explosion image
     */
    public Explosion(double x, double y, String imgPath, double width, double height) {
        Image img = new Image(imgPath);
        imageView = new ImageView(img);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setX(x);
        imageView.setY(y);
    }
}
