package entity;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class entity {

    //This class will be the parent class that will store variables that will be used in players, monsters, and NPC classes.

    public int worldX, worldY; // Changed to help readability
    public int speed;

    //This describes an image with an accessible buffer of image data
        // Will be used to store image files
    public BufferedImage up1, up2, left1, left2, right1, right2, down1, down2;

    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    
    
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public boolean collisionOn = false;
    
}
