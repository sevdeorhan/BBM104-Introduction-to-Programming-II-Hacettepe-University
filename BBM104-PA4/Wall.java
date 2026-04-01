import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Wall class represents a solid object on the game map
 * that blocks tank movement and destroys bullets.
 */
public class Wall {
    double x, y; // Position of the wall
    double width, height; // Dimensions of the wall
    ImageView imageView; // ImageView for visual display of the wall

    /**
     * Constructs a Wall object with given position, size, and texture.
     *
     * @param x         X position of the wall
     * @param y         Y position of the wall
     * @param width     Width of the wall
     * @param height    Height of the wall
     * @param wallFile  File path to the wall texture image
     */
    Wall(double x, double y, double width, double height, String wallFile) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        Image image = new Image(wallFile);
        imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setX(x);
        imageView.setY(y);
    }
}