import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Tank class represents both the player and enemy tanks in the game.
 * It handles position, movement, direction, image representation,
 * and simple AI state variables.
 */
public class Tank {
    public String imagePath; // Path to tank image used for animation switching
    double x, y; // Tank position on the map
    double width, height; // Dimensions of the tank
    int direction; // Direction the tank is facing (angle in degrees)
    ImageView imageView; // JavaFX ImageView for displaying the tank

    // Enemy AI-related fields
    public int aiDirection = 0; // 0: right, 1: left, 2: down, 3: up
    public double targetX; // Future use (not active in current version)
    public double targetY; // Future use (not active in current version)
    public boolean isMoving = false; // Used for animation logic
    public int animationFrame = 0; // Used to toggle between animation frames

    /**
     * Constructs a Tank object with given position, size, and image.
     *
     * @param x        Initial x-coordinate
     * @param y        Initial y-coordinate
     * @param width    Width of the tank
     * @param height   Height of the tank
     * @param imgPath  Path to the tank's image
     */
    public Tank(double x, double y, double width, double height, String imgPath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.direction = 0;
        this.targetX = x;
        this.targetY = y;
        this.imagePath = imgPath;

        Image img = new Image(imgPath);
        imageView = new ImageView(img);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setX(x);
        imageView.setY(y);
    }

    /**
     * Moves the tank by given delta values and updates image position.
     *
     * @param dx  Movement along x-axis
     * @param dy  Movement along y-axis
     */
    public void move(double dx, double dy) {
        this.x += dx;
        this.y += dy;
        imageView.setX(this.x);
        imageView.setY(this.y);
    }

    /**
     * Sets the tank's facing direction by rotation angle.
     *
     * @param angle Rotation angle in degrees (0, 90, 180, -90)
     */
    public void setDirection(int angle) {
        this.direction = angle;
        this.imageView.setRotate(angle);
    }

    // Getters
    public double getX() { return x; }
    public double getY() { return y; }
    public ImageView getImageView() { return imageView; }

    /**
     * Updates the position of the tank's image to match internal coordinates.
     */
    public void updateImageView() {
        this.imageView.setX(this.x);
        this.imageView.setY(this.y);
    }
}
