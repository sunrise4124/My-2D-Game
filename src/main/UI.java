package main;

import object.Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {

    GamePanel gamePanel;
    Font font, boldFont;
    BufferedImage keyImage;
    public Boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    double playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        font = new Font("Arial", Font.PLAIN, 40);
        boldFont = new Font("Arial", Font.BOLD,80);
        Key key = new Key();
        keyImage = key.image;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    // bad idea because will be a part of loop and will be called 60 times a second
    //public void draw (Graphics2D g2) {
    //    g2.setFont(new Font("Arial", Font.PLAIN, 40));
    //}

    public void draw(Graphics2D g2) {
        g2.setFont(font);
        g2.setColor(Color.white);

        if (gameFinished) {
            String text;
            int x;
            int y;
            text = "You found the treasure!";
            x = gamePanel.screenWidth / 2 - (gamePanel.tileSize * 3);
            y = gamePanel.screenHeight / 2 - (gamePanel.tileSize * 3);
            g2.drawString(text, x, y);

            text = "Your time is: " + decimalFormat.format(playTime) + "!";
            x = gamePanel.screenWidth / 2 - (gamePanel.tileSize * 3);
            y = gamePanel.screenHeight / 2 + (gamePanel.tileSize * 3);
            g2.drawString(text, x, y);

            g2.setFont(boldFont);
            g2.setColor(Color.yellow);
            text = "Congratulations!";
            x = gamePanel.screenWidth / 2 - (gamePanel.tileSize * 4);
            y = gamePanel.screenHeight / 2 + gamePanel.tileSize;
            g2.drawString(text, x, y);

            // finally the game stops
            gamePanel.gameThread = null;
        }
        else {
            g2.drawImage(keyImage, gamePanel.tileSize / 2, gamePanel.tileSize / 2,
                    gamePanel.tileSize, gamePanel.tileSize, null);
            // this drawString is an exception. For some reason the y represents the bottom and not the top
            //g2.drawString("key = " + gamePanel.player.hasKey, 25, 50);
            g2.drawString("x " + gamePanel.player.hasKey, 74, 65);

            // time
            playTime += (double) 1 / 60;
            g2.drawString("Time: " + decimalFormat.format(playTime), gamePanel.tileSize * 11, 65);

            // message
            if (messageOn) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message,gamePanel.tileSize / 2, gamePanel.tileSize * 5);
                messageCounter++;
                if (messageCounter > 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }
    }
}
