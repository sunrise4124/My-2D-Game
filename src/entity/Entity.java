package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

// This stores variables that will be used in Player, Monster and NPC classes
public class Entity {
    public int worldX, worldY;
    public int speed;

    // It describes an image with an accessible buffer data. (We use this to store our image files).
    public BufferedImage up1, up2, down1, down2, right1, right2, left1, left2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    // we are adding this method so that only specific body part of the character can detect collision
    // if we do not do this then the game will become really unplayable
    // Rectangle class can be used to store data such as x, y, width, height
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn;
}
