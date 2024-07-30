package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    //SCREEN SETTINGS
    final int originalTileSize = 16;    // 16x16 tiles
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48 tiles
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenHeight = tileSize * maxScreenCol; // 768 pixels
    public final int screenWidth = tileSize * maxScreenRow; // 576 pixels

    //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // public final int worldHeight = tileSize * maxWorldCol;
    // public final int worldWidth = tileSize * maxWorldRow;

    // FPS
    int FPS = 60;

    // System
    TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    // Entity and Object
    public Player player = new Player(this, keyHandler);
    public SuperObject obj[] = new SuperObject[10];


    public GamePanel() {
        this.setPreferredSize(new Dimension(screenHeight, screenWidth));
        this.setBackground(Color.black);

        // if set to true, all the drawing from this component will be done in an off-screen printing buffer
        this.setDoubleBuffered(true); // in short enabling this can improve game's rendering performance
        this.addKeyListener(keyHandler);

        // With this, the GamePanel can be "focused" to receive key input
        this.setFocusable(true);
    }

    public void setUpGame() {
        assetSetter.setObject();
        playMusic(0);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); // this will automatically call the run method
    }

    // this method will create a game loop which will become core of the game
    @Override
    public void run() {

        // Returns the current value of the Java Virtual Machine's high-resolution time source in nanoseconds
        // long currentTime = System.nanoTime();

        // Returns time in milliseconds
        // long currentTime2 = System.currentTimeMillis();

        double drawInterval = 1000000000/FPS;   // 0.01666 seconds
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            // 1 UPDATE: update information such as character information
            update();
            // 2 DRAW: draw the screen with the updated information
            repaint(); // this is how we call the paintComponent method

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;
                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                // sleep basically pauses the game loop until the sleep time is over
                // also, sleep accepts time in milliseconds, therefore first we have to convert time in milliseconds
                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update() {
        player.update();
    }

    // this is a standard j unit method
    public void paintComponent(Graphics g) {
        // whenever you use this method on JPanel, you need to use this
        super.paintComponent(g);

        // Graphics2D class extends the Graphics class to provide more sophisticated control over geometry,
        // coordinate transformations, color management, and text layout.
        Graphics2D g2 = (Graphics2D)g;
        // tile
        tileManager.draw(g2);
        // object
        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(g2, this);
            }
        }
        // player
        player.draw(g2);

        // UI
        ui.draw(g2);

        // Dispose of this graphics context and release any system resources that it is using
        // program will still work without it, but it is good practise as it save memory
        g2.dispose();
    }

    public void playMusic (int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSoundEffect (int i) {
        soundEffect.setFile(i);
        soundEffect.play();
    }
}
