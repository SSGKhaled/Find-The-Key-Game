package entity;


import main.KeyHandler;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Player extends entity {
    GamePanel gp;
    KeyHandler keyH;
    
    //Values are set to follow the character around the map
    public final int screenX;
    public final int screenY;
    
    public int hasKey = 0;

    // Constructor for the Player Class
    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        
        //Making a collision detector that's smaller than the sprite.
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;
        
        setDefaultValues();
        getPlayerImage();
    }

    //What we are doing here is the same for what we are doing in the game Panel class for their relative variables.
    public void setDefaultValues(){

        worldX = gp.tileSize * 23; // Sets the spawn point of the character
        worldY = gp.tileSize * 21; // Sets the spawn point of the character
        speed = 4;
        direction = "down";

    }




    public void getPlayerImage()  {

        try{
            //Currently assigning different images to player movement
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
            

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //Used to update the position of the player in the Game Loop
    public void update(){
    	
    	if(keyH.upPressed == true || keyH.downPressed == true ||
    			keyH.leftPressed == true ||keyH.rightPressed == true) {
    		
            if (keyH.upPressed == true){
                direction = "up";
                
            }
            else if(keyH.downPressed == true){
                direction = "down";
            
            }
            else if(keyH.leftPressed == true){
                direction = "left";
            
            }
            else if(keyH.rightPressed == true){
                direction = "right";
               
            }
            // CHECK TILE COLLISION 
            collisionOn = false;
            gp.cChecker.checkTile(this);
            
            // CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this,  true);
            pickUpObject(objIndex);
            
            // IF COLLISION IS FALSE, PLAYER CAN MOVE
            
            if(collisionOn == false) {
            	switch(direction) {
            	case "up":
            		worldY = worldY - speed;
            		break;
            		
            	case "down":
            		worldY = worldY + speed;
            		break;
            		
            	case "left":
            		worldX = worldX - speed;
            		break;
            		
            	case "right":
            		worldX = worldX + speed;
            		break;
            	}
            }
    	}

        
        
  
        //We now added this knowing that the update method gets called 60 times per second due to the Game Loop
        // So every frame, it increases the counter by one
        // Once it hits twenty, it changes the picture of the character changes.
        //The picture of the character changes every twenty frames a second.
        spriteCounter++;
        if(spriteCounter > 20) {
        	if(spriteNum == 1) {
        		spriteNum = 2;
        	}
        	else if(spriteNum == 2) {
        		spriteNum = 1;
        	}
        	spriteCounter = 0;
        }
    }
    
    
    
    public void pickUpObject(int i) {
    	if(i != 999) {
    		
    		String objectName = gp.obj[i].name;
    		
    		switch(objectName) {
    		case "Key":
    			gp.playSE(1);
    			hasKey++;
    			gp.obj[i] = null;
    			gp.ui.showMessage("You got a key!");
    			break;
    		case "Door":
    			if(hasKey > 0) {
    				gp.playSE(4);
    				gp.obj[i] = null;
    				hasKey--;
    				gp.ui.showMessage("You opened the door!");
    				
    			}
    			else {
    				gp.ui.showMessage("You need a Key!");
    			}
    			System.out.println("Key:" +hasKey);
    			break;
    		case "Boots":
    			gp.playSE(3);
    			speed+= 4;
    			gp.obj[i] = null;
    			gp.ui.showMessage("Speed Up!");
    			break;
    		case "Chest":
    			gp.ui.gameFinished = true;
    			gp.stopMusic();
    			gp.playSE(2);
    			break;
    			
    			
    			
    		}
    	}
    }
    //Used to update character image in the game loop
    public void draw(Graphics2D g2){

        // g2.setColor(Color.white);

        // g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch(direction){
            case "up":
            	if(spriteNum == 1) {
            		image = up1;
            	}
            	if (spriteNum == 2) {
            		image = up2;
            	}
                break;
            case "down":
            	if(spriteNum == 1) {
                    image = down1;
                    
            	}
            	if(spriteNum == 2) {
            		image = down2;
            	}
                break;
            case "left":
            	if(spriteNum == 1) {
            		image = left1;
            	}
                if(spriteNum == 2) {
                	image = left2;
                }
                break;
            case "right":
            	if(spriteNum == 1) {
            		 image = right1;
            	}
                if(spriteNum == 2) {
                	image = right2;
                }
                break;
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);


    }







}
