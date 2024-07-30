package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gamePanel;
    KeyHandler keyHandler;

    public final int screenX;
    public final int screenY;

    public int hasKey = 0;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;

        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        solidArea = new Rectangle(8,16,32,32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
        speed = 4;
        direction = "down"; // any direction is fine
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("player/boy_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyHandler.upPressed || keyHandler.downPressed || keyHandler.leftPressed || keyHandler.rightPressed) {
            if (keyHandler.upPressed) {
                direction = "up";
            }
            else if (keyHandler.downPressed) {
                direction = "down";
            }
            else if (keyHandler.leftPressed) {
                direction = "left";
            }
            else if (keyHandler.rightPressed) {
                direction = "right";
            }

            // check tile collision
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);

            // check object collision
            int objIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // if collision is false, player can move
            if (!collisionOn) {
                switch (direction) {
                    case "up" : worldY -= speed; break;
                    case "down" : worldY += speed; break;
                    case "left" : worldX -= speed; break;
                    case "right" : worldX += speed; break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum =2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gamePanel.obj[i].name;
            switch (objectName) {
                case "Key" :
                    gamePanel.playSoundEffect(1);
                    hasKey++;
                    gamePanel.obj[i] = null;
                    gamePanel.ui.showMessage("You got a key!");
                    break;
                case "Door" :
                    if (hasKey > 0) {
                        gamePanel.playSoundEffect(3);
                        gamePanel.obj[i] = null;
                        hasKey--;
                        gamePanel.ui.showMessage("You opened the door!");
                    }
                    else {
                        gamePanel.ui.showMessage("You need a key!");
                    }
                    break;
                case "Boots" :
                    gamePanel.playSoundEffect(2);
                    speed += 1;
                    gamePanel.obj[i] = null;
                    gamePanel.ui.showMessage("Speed increased!");
                    break;
                case "Chest" :
                    gamePanel.ui.gameFinished = true;
                    gamePanel.stopMusic();
                    gamePanel.playSoundEffect(4);
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {
        //g2.setColor(Color.white);

        // Draw a rectangle and fills it with the specified color
        //g2.fillRect(x, y, gamePanel.tileSize, gamePanel.tileSize);

        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    image = up1;
                }
               else if (spriteNum == 2) {
                   image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    image = down1;
                }
                else if (spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    image = left1;
                }
                if (spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    image = right1;
                }
                else if (spriteNum == 2) {
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }
 }
