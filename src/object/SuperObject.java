package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle solidArea = new Rectangle(0,0,48,48);
    public int solidAreaDefaultX = 0;
    public int solidAreaDefaultY = 0;

    public void draw(Graphics2D g2, GamePanel gamePanel) {

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        // Basically we are using this if statement so that the loop does not draw the whole map and just
        // draws the area that is visible on screen
        //   if (worldX >= gamePanel.player.worldX - gamePanel.player.screenX && worldX <= gamePanel.player.worldX +
        //           gamePanel.player.screenX && worldY >= gamePanel.player.worldY - gamePanel.player.screenY &&
        //           worldY <= gamePanel.player.worldY + gamePanel.player.screenY) {
        g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        //   }
    }
}
