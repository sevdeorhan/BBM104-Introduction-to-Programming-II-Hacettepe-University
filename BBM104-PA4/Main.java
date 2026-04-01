import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.animation.AnimationTimer;
import javafx.util.Duration;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Random;
import java.util.ArrayList;

// Main game class (handles window, rendering, game logic)
public class Main extends Application {
    private int score = 0; // Player's score
    private int lives = 3; // Player's lives
    private Label scoreLabel; // UI label to display score
    private Label livesLabel; // UI label to display remaining lives
    private boolean isGameOver = false; // Game over flag
    private Label gameOverLabel; // Label shown when the game ends
    private boolean isPaused = false; // Pause state
    private Label pausedLabel; // Label shown when game is paused

    // Key press states for movement
    private boolean moveUp, moveDown, moveLeft, moveRight;

    // Speed settings
    private final double PLAYER_SPEED = 4.0;
    private final double ENEMY_SPEED = 4.0;
    private final double ENEMY_BULLET_SPEED = 5.0;

    // Fire rate limit: max 10 bullets per second
    private final long FIRE_WINDOW_NS = 1_000_000_000L;
    private final int MAX_SHOTS_PER_SECOND = 10;
    private ArrayList<Long> playerFireTimestamps = new ArrayList<>();

    public void start(Stage primaryStage) {
        // Base pane and camera group (for scrolling effect)
        Pane root = new Pane();
        Group cameraGroup = new Group(); // All gameplay elements are grouped here to allow scrolling
        root.getChildren().add(cameraGroup);

        // Black background for the map
        Rectangle background = new Rectangle(1800, 1200, Color.BLACK);
        cameraGroup.getChildren().add(background);

        // Main window and scene setup
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tank Game");
        primaryStage.show();

        // Score label configuration
        scoreLabel = new Label("Score: " + score);
        scoreLabel.setFont(Font.font(18));
        scoreLabel.setTextFill(Color.WHITE);
        scoreLabel.setLayoutX(30);
        scoreLabel.setLayoutY(30);
        root.getChildren().add(scoreLabel);

        // Lives label configuration
        livesLabel = new Label("Lives: " + lives);
        livesLabel.setFont(Font.font(18));
        livesLabel.setTextFill(Color.WHITE);
        livesLabel.setLayoutX(30);
        livesLabel.setLayoutY(60);
        root.getChildren().add(livesLabel);

        // Game over label (initially hidden)
        gameOverLabel = new Label();
        gameOverLabel.setFont(Font.font("Arial", 54));
        gameOverLabel.setTextFill(Color.DARKRED);
        gameOverLabel.setLayoutX(270);
        gameOverLabel.setLayoutY(200);
        gameOverLabel.setVisible(false);
        root.getChildren().add(gameOverLabel);

        // Pause label (initially hidden)
        pausedLabel = new Label("PAUSED");
        pausedLabel.setFont(Font.font("Arial", 48));
        pausedLabel.setTextFill(Color.ORANGE);
        pausedLabel.setLayoutX(330);
        pausedLabel.setLayoutY(250);
        pausedLabel.setVisible(false);
        root.getChildren().add(pausedLabel);

        // Restart/exit instructions
        Label restartHint = new Label("Press R to Restart\nPress ESC to Quit");
        restartHint.setFont(Font.font("Arial", 20));
        restartHint.setTextFill(Color.DARKBLUE);
        restartHint.setLayoutX(320);
        restartHint.setLayoutY(360);
        restartHint.setVisible(false);
        root.getChildren().add(restartHint);

        // Create an empty 40x60 map
        int[][] map = new int[40][60];

        int[][] oldMap = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
                {1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
                {1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1},
                {1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1},
                {1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},

        };
        // Center the oldMap block into the larger map
        for (int i = 0; i < oldMap.length; i++) {
            for (int j = 0; j < oldMap[0].length; j++) {
                map[i + 10][j + 15] = oldMap[i][j];
            }
        }

        ArrayList<Wall> walls = new ArrayList<>();

        // Offset to center the map on the 1800x1200 canvas
        int offsetX = (1800 - 900) / 2;
        int offsetY = (1200 - 600) / 2;

        // Generate wall objects from map data
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                int cell = map[i][j];
                if (cell == 0) continue;
                double x = j * 30;
                double y = i * 30;
                Wall wall = new Wall(x, y, 30, 30, "file:assets/wall.png");
                walls.add(wall);
                cameraGroup.getChildren().add(wall.imageView);
            }
        }

        ArrayList<Tank> enemyTanks = new ArrayList<>();
        double startX = 60 + offsetX;
        double startY = 60 + offsetY;
        Tank playerTank = new Tank(startX, startY, 30, 30, "file:assets/yellowTank1.png");
        cameraGroup.getChildren().add(playerTank.getImageView());
        cameraGroup.setLayoutX(450 - playerTank.x - playerTank.width / 2);
        cameraGroup.setLayoutY(300 - playerTank.y - playerTank.height / 2);


        Random rnd = new Random();
        ArrayList<Bullet> bullets = new ArrayList<>();
        String bulletImgPath = "file:assets/bullet.png";

        Tank enemy1 = new Tank(720+offsetX, 60+offsetY, 30, 30, "file:assets/whiteTank1.png");
        enemy1.aiDirection = rnd.nextInt(4);
        enemyTanks.add(enemy1);
        cameraGroup.getChildren().add(enemy1.getImageView());

        Tank enemy2 = new Tank(90+offsetX, 480+offsetY, 30, 30, "file:assets/whiteTank2.png");
        enemy2.aiDirection = rnd.nextInt(4);
        enemyTanks.add(enemy2);
        cameraGroup.getChildren().add(enemy2.getImageView());

        Tank enemy3 = new Tank(420+offsetX, 300+offsetY, 30, 30, "file:assets/whiteTank1.png");
        enemy3.aiDirection = rnd.nextInt(4);
        enemyTanks.add(enemy3);
        cameraGroup.getChildren().add(enemy3.getImageView());

        Tank enemy4 = new Tank(660+offsetX, 420+offsetY, 30, 30, "file:assets/whiteTank2.png");
        enemy4.aiDirection = rnd.nextInt(4);
        enemyTanks.add(enemy4);
        cameraGroup.getChildren().add(enemy4.getImageView());

        // Player input
        scene.setOnKeyPressed(e -> {
            KeyCode code = e.getCode();

            if (code == KeyCode.UP) moveUp = true;
            if (code == KeyCode.DOWN) moveDown = true;
            if (code == KeyCode.LEFT) moveLeft = true;
            if (code == KeyCode.RIGHT) moveRight = true;


            if (code == KeyCode.X && !isPaused && !isGameOver) {
                long now = System.nanoTime();


                playerFireTimestamps.removeIf(t -> now - t > FIRE_WINDOW_NS);

                if (playerFireTimestamps.size() < MAX_SHOTS_PER_SECOND) {
                    playerFireTimestamps.add(now);

                    double dx = 0, dy = 0;
                    int ang = playerTank.direction;
                    if (ang == 0) dx = PLAYER_SPEED + 3;
                    if (ang == 180) dx = -PLAYER_SPEED - 3;
                    if (ang == -90) dy = -PLAYER_SPEED - 3;
                    if (ang == 90) dy = PLAYER_SPEED + 3;

                    Bullet b = new Bullet(
                            playerTank.x + playerTank.width / 2 - 5,
                            playerTank.y + playerTank.height / 2 - 5,
                            dx, dy,
                            bulletImgPath, true
                    );
                    b.imageView.setRotate(ang);
                    bullets.add(b);
                    cameraGroup.getChildren().add(b.imageView);
                }
            }


            if (code == KeyCode.R && isGameOver) {
                javafx.application.Platform.runLater(() -> {
                    try {
                        new Main().start(primaryStage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            }

            if (code == KeyCode.ESCAPE && isGameOver) {
                System.exit(0);
            }

            if (code == KeyCode.P && !isGameOver) {
                isPaused = !isPaused;
                pausedLabel.setVisible(isPaused);
                pausedLabel.toFront();
            }
        });

        scene.setOnKeyReleased(e -> {
            KeyCode code = e.getCode();
            if (code == KeyCode.UP) moveUp = false;
            if (code == KeyCode.DOWN) moveDown = false;
            if (code == KeyCode.LEFT) moveLeft = false;
            if (code == KeyCode.RIGHT) moveRight = false;
        });

        ArrayList<Long> nextFireTime = new ArrayList<>();
        for (int i = 0; i < enemyTanks.size(); i++) {
            nextFireTime.add(0L);
        }

        // Main loop
        new AnimationTimer() {
            long lastThink = 0; // Keeps track of when AI last changed direction

            public void handle(long now) {
                // Do not process game loop if game is over or paused
                if (isGameOver) return;
                if (isPaused) return;

                // === Player Tank Movement ===
                double vx = 0, vy = 0;
                if (moveUp)    vy = -PLAYER_SPEED;
                if (moveDown)  vy =  PLAYER_SPEED;
                if (moveLeft)  vx = -PLAYER_SPEED;
                if (moveRight) vx =  PLAYER_SPEED;

                if (vx != 0 || vy != 0) {
                    // Determine player direction for rotation
                    int newDir;
                    if      (vx > 0) newDir = 0;
                    else if (vx < 0) newDir = 180;
                    else if (vy < 0) newDir = -90;
                    else newDir = 90;

                    playerTank.setDirection(newDir);

                    // Proposed new player position
                    double nx = playerTank.x + vx;
                    double ny = playerTank.y + vy;

                    // Check for collisions and boundaries
                    if (nx >= 0 && nx + playerTank.width <= 1800 &&
                            ny >= 0 && ny + playerTank.height <= 1200 &&
                            !walls.stream().anyMatch(w -> rectsIntersect(nx, ny, playerTank.width, playerTank.height, w.x, w.y, w.width, w.height)) &&
                            !enemyTanks.stream().anyMatch(t -> rectsIntersect(nx, ny, playerTank.width, playerTank.height, t.x, t.y, t.width, t.height))) {

                        // Update position and animation
                        playerTank.x = nx;
                        playerTank.y = ny;
                        playerTank.animationFrame = 1 - playerTank.animationFrame; // Toggle between frame 0 and 1

                        if (playerTank.animationFrame == 0)
                            playerTank.getImageView().setImage(new Image("file:assets/yellowTank1.png"));
                        else
                            playerTank.getImageView().setImage(new Image("file:assets/yellowTank2.png"));

                        playerTank.updateImageView();

                        // Move camera to follow player
                        cameraGroup.setLayoutX(450 - playerTank.x - playerTank.width / 2);
                        cameraGroup.setLayoutY(300 - playerTank.y - playerTank.height / 2);
                    }
                }

                // === Enemy AI Direction Update (every 0.5s) ===
                if (now - lastThink > 500_000_000L) {
                    for (Tank en : enemyTanks) {
                        en.aiDirection = rnd.nextInt(4); // Randomly select new direction
                    }
                    lastThink = now;
                }

                // === Enemy Tank Movement ===
                for (Tank en : enemyTanks) {
                    double evx = 0, evy = 0;
                    switch (en.aiDirection) {
                        case 0: evx = ENEMY_SPEED;  break;
                        case 1: evx = -ENEMY_SPEED; break;
                        case 2: evy = ENEMY_SPEED;  break;
                        case 3: evy = -ENEMY_SPEED; break;
                    }

                    // Determine new direction and rotate tank
                    int newDir;
                    if      (evx > 0)  newDir =   0;
                    else if (evx < 0)  newDir = 180;
                    else if (evy < 0)  newDir = -90;
                    else               newDir =  90;
                    en.setDirection(newDir);

                    double enx = en.x + evx;
                    double eny = en.y + evy;

                    // Check movement boundaries and collisions
                    if (enx >= 0 && enx + en.width <= 1800 &&
                            eny >= 0 && eny + en.height <= 1200 &&
                            !walls.stream().anyMatch(w -> rectsIntersect(enx, eny, en.width, en.height, w.x, w.y, w.width, w.height)) &&
                            !enemyTanks.stream().anyMatch(other -> other != en && rectsIntersect(enx, eny, en.width, en.height, other.x, other.y, other.width, other.height)) &&
                            !rectsIntersect(enx, eny, en.width, en.height, playerTank.x, playerTank.y, playerTank.width, playerTank.height)) {

                        // Update enemy tank position and animation
                        en.x = enx;
                        en.y = eny;
                        en.animationFrame = 1 - en.animationFrame;

                        // Alternate enemy sprite
                        if (en.imagePath.contains("whiteTank1")) {
                            en.getImageView().setImage(new Image(en.animationFrame == 0 ? "file:assets/whiteTank1.png" : "file:assets/whiteTank2.png"));
                        } else {
                            en.getImageView().setImage(new Image(en.animationFrame == 0 ? "file:assets/whiteTank2.png" : "file:assets/whiteTank1.png"));
                        }

                        en.updateImageView();
                    }
                }

                // === Enemy Firing ===
                for (int i = 0; i < enemyTanks.size(); i++) {
                    Tank en = enemyTanks.get(i);
                    if (now > nextFireTime.get(i)) {
                        double dx = 0, dy = 0;
                        switch (en.aiDirection) {
                            case 0: dx =  ENEMY_BULLET_SPEED; break;
                            case 1: dx = -ENEMY_BULLET_SPEED; break;
                            case 2: dy =  ENEMY_BULLET_SPEED; break;
                            case 3: dy = -ENEMY_BULLET_SPEED; break;
                        }
                        Bullet b = new Bullet(en.x + en.width / 2 - 5, en.y + en.height / 2 - 5, dx, dy, bulletImgPath, false);
                        b.imageView.setRotate(en.aiDirection == 0 ? 0 : en.aiDirection == 1 ? 180 : en.aiDirection == 2 ? 90 : -90);
                        bullets.add(b);
                        cameraGroup.getChildren().add(b.imageView);

                        long delay = (long)((0.5 + rnd.nextDouble()) * 1_000_000_000L);
                        nextFireTime.set(i, now + delay);
                    }
                }

                // === Bullet Movement and Collision ===
                ArrayList<Bullet> toRemove = new ArrayList<>();
                for (Bullet b : bullets) {
                    b.move();

                    boolean expired = (b.x < 0 || b.x > 1800 || b.y < 0 || b.y > 1200);
                    boolean hitWall = false;

                    for (Wall w : walls) {
                        if (rectsIntersect(b.x, b.y, 10, 10, w.x, w.y, w.width, w.height)) {
                            hitWall = true;
                            break;
                        }
                    }

                    boolean hitTank = false;
                    Tank hitTarget = null;

                    // Player bullets hitting enemies
                    if (b.isPlayerBullet) {
                        for (Tank t : enemyTanks) {
                            if (rectsIntersect(b.x, b.y, 10, 10, t.x, t.y, t.width, t.height)) {
                                hitTank = true;
                                hitTarget = t;
                                break;
                            }
                        }
                        if (hitTank) {
                            // Increase score and show explosion
                            score += 10;
                            scoreLabel.setText("Score: " + score);
                            Explosion e = new Explosion(hitTarget.x, hitTarget.y, "file:assets/explosion.png", 30, 30);
                            cameraGroup.getChildren().add(e.imageView);
                            PauseTransition p2 = new PauseTransition(Duration.seconds(0.3));
                            p2.setOnFinished(ev -> cameraGroup.getChildren().remove(e.imageView));
                            p2.play();

                            // Replace defeated enemy with a new one
                            cameraGroup.getChildren().remove(hitTarget.getImageView());
                            int targetIndex = enemyTanks.indexOf(hitTarget);
                            Random rnd = new Random();
                            int ex, ey;
                            do {
                                ex = 60 + offsetX + rnd.nextInt(800);
                                ey = 60 + offsetY + rnd.nextInt(400);
                            } while (collidesWithWall(ex, ey, walls));

                            String[] enemyImgs = {"file:assets/whiteTank1.png", "file:assets/whiteTank2.png"};
                            String chosenImg = enemyImgs[rnd.nextInt(2)];
                            Tank newEnemy = new Tank(ex, ey, 30, 30, chosenImg);
                            newEnemy.aiDirection = rnd.nextInt(4);

                            enemyTanks.set(targetIndex, newEnemy);
                            nextFireTime.set(targetIndex, 0L);
                            cameraGroup.getChildren().add(newEnemy.getImageView());

                            toRemove.add(b);
                            cameraGroup.getChildren().remove(b.imageView);
                            continue;
                        }
                    }

                    // Enemy bullets hitting player
                    if (!b.isPlayerBullet && rectsIntersect(b.x, b.y, 10, 10, playerTank.x, playerTank.y, playerTank.width, playerTank.height)) {
                        lives--;
                        livesLabel.setText("Lives: " + lives);

                        if (lives <= 0) {
                            isGameOver = true;
                            gameOverLabel.setText("GAME OVER\n  Score: " + score);
                            gameOverLabel.setVisible(true);
                            gameOverLabel.toFront();
                            restartHint.setVisible(true);
                            restartHint.toFront();
                        } else {
                            // Respawn player after delay
                            PauseTransition respawn = new PauseTransition(Duration.seconds(1));
                            respawn.setOnFinished(evt -> {
                                playerTank.x = 60 + offsetX;
                                playerTank.y = 60 + offsetY;
                                playerTank.updateImageView();
                                playerTank.getImageView().setVisible(true);
                                cameraGroup.setLayoutX(450 - playerTank.x - playerTank.width / 2);
                                cameraGroup.setLayoutY(300 - playerTank.y - playerTank.height / 2);
                            });
                            respawn.play();
                        }

                        // Show explosion when player dies
                        Explosion e = new Explosion(playerTank.x, playerTank.y, "file:assets/explosion.png", 30, 30);
                        cameraGroup.getChildren().add(e.imageView);
                        PauseTransition p2 = new PauseTransition(Duration.seconds(0.5));
                        p2.setOnFinished(ev -> cameraGroup.getChildren().remove(e.imageView));
                        p2.play();

                        playerTank.getImageView().setVisible(false);
                        toRemove.add(b);
                        cameraGroup.getChildren().remove(b.imageView);
                        continue;
                    }

                    // Remove expired or wall-colliding bullets
                    if (expired || hitWall) {
                        toRemove.add(b);
                        cameraGroup.getChildren().remove(b.imageView);
                        if (hitWall) {
                            double expX = b.x + 5 - 8;
                            double expY = b.y + 5 - 8;
                            Explosion exp = new Explosion(expX, expY, "file:assets/smallExplosion.png");
                            cameraGroup.getChildren().add(exp.imageView);
                            PauseTransition p = new PauseTransition(Duration.seconds(0.2));
                            p.setOnFinished(evt -> cameraGroup.getChildren().remove(exp.imageView));
                            p.play();
                        }
                    }
                }
                // Remove all marked bullets from the list
                bullets.removeAll(toRemove);
            }
        }.start();

    }


    public boolean collidesWithWall(double x, double y, ArrayList<Wall> walls) {
        for (Wall w : walls) {
            if (rectsIntersect(x, y, 30, 30, w.x, w.y, w.width, w.height)) {
                return true;
            }
        }
        return false;
    }

    public static boolean rectsIntersect(double x1, double y1, double w1, double h1,
                                         double x2, double y2, double w2, double h2) {
        double buffer = 2.0;
        return x1 < x2 + w2 - buffer &&
                x1 + w1 > x2 + buffer &&
                y1 < y2 + h2 - buffer &&
                y1 + h1 > y2 + buffer;
    }


    public static void main(String[] args) {
        launch(args);
    }
}