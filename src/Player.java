import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import java.util.ArrayList;

public class Player extends GameObject implements EntityA
{

	// Initialize velocity of player to zero
	private double velX = 0;
	private double velY = 0;

	public static int dataCounter = 0; // Initialize data collected to zero

	public double gravity = 0.0; // Initialize gravity to zero

	// Temporary position of player
	public int tempx = 0;
	public int tempy = 0;

	// Initialize jumping and falling variables
	public boolean jumping = false;
	public boolean falling = true;
	public static boolean allowJumping = false;

	// Initialize player moving variables (used for textures)
	public boolean right = false;
	public boolean left = false;
	public boolean notMoving = true;
	public boolean playerIsJumping = false;

    // Declare hit boxes for player and map 
	public Rectangle hitBox; 
	public Rectangle[] map = new Rectangle[30]; 

	// Initialize cameraOffset to 0
	public static int cameraOffset = 0;

	// Declare textures
	private Textures tex;

	Game game;
	Controller controller;
	Animation animRight;
	Animation animLeft;

	public Player (double x, double y, Textures tex, Game game, Controller controller)
	{

		super(x,y);
		this.tex = tex;
		this.game = game;
		this.controller = controller;

		hitBox = new Rectangle ((int) x,(int) y, 30, 30);

		// Hard code hit boxes for map

		map[0] = new Rectangle (400, 312, 35, 50);
		map[1] = new Rectangle (435, 360, 155, 35);
		map[2] = new Rectangle (480, 322, 110, 37);
		map[3] = new Rectangle (518, 285, 72, 37);
		map[4] = new Rectangle (590, 396, 77, 37);
		map[5] = new Rectangle (627, 435, 77, 42);
		map[6] = new Rectangle (700, 472, 160, 42);
		map[7] = new Rectangle (855, 435, 160, 42);
		map[8] = new Rectangle (930, 290, 140, 160);
		map[9] = new Rectangle (930, 290, 215, 109);
		map[10] = new Rectangle (930, 290, 250, 69);
		map[11] = new Rectangle (1220, 290, 115, 69);
		map[12] = new Rectangle (1335, 290, 38, 32);
		map[13] = new Rectangle (1270, 576, 38, 38);
		map[14] = new Rectangle (1308, 540, 76, 50);
		map[15] = new Rectangle (1384, 500, 120, 50);
		map[16] = new Rectangle (1460, 463, 80, 40);
		map[17] = new Rectangle (1500, 430, 50, 40);
		map[18] = new Rectangle (1538, 388, 400, 40);
		map[19] = new Rectangle (1915, 352, 100, 40);
		map[20] = new Rectangle (1990, 315, 395, 225);
		map[21] = new Rectangle (780, 284, 110, 40);
		map[22] = new Rectangle (930, 284, 250, 40);
		map[23] = new Rectangle (1220, 284, 153, 40);
		map[24] = new Rectangle (820, 320, 70, 40);
		map[25] = new Rectangle (858, 172, 185, 35);
		map[26] = new Rectangle (2460, 505, 80, 35);
		map[27] = new Rectangle (2320, 465, 140, 35);
		map[28] = new Rectangle (2423, 315, 77, 40);
		map[29] = new Rectangle (1165, 465, 75, 35);


		// Declare character movement animations
		animRight = new Animation(5, tex.playerRight[0], tex.playerRight[1], tex.playerRight[2]);
		animLeft = new Animation(5, tex.playerLeft[0], tex.playerLeft[1], tex.playerLeft[2]);

	}

	//-------------------------------------------------------------------------------------------PLAYER-MAP COLLISION--------------------------------------------------------------------------------------
	
	private int getCollisionSide(Rectangle player, Rectangle tile){

		if (player.getCenterX() > tile.getMinX() && player.getCenterX() < tile.getMaxX() && player.getCenterY() < tile.getMinY()){
			//Top
			return 1;
		}
		else if(player.getCenterY() > tile.getMinY() && player.getCenterY() < tile.getMaxY() && player.getCenterX() < tile.getMinX()){
			//Left
			return 2;
		}
		else if(player.getCenterY() > tile.getMinY() && player.getCenterY() < tile.getMaxY() && player.getCenterX() > tile.getMaxX()){
			//Right
			return 3;
		}
		else if(player.getCenterX() > tile.getMinX() && player.getCenterX() < tile.getMaxX() && player.getCenterY() > tile.getMaxY()){
			//Bottom
			return 4;
		}
		else if(player.getCenterX() < tile.getMinX() && player.getCenterY() < tile.getMinY()){
			//TL Corner
			return 5;
		}
		else if(player.getCenterX() > tile.getMaxX() && player.getCenterY() < tile.getMinY()){
			//TR Corner
			return 6;
		}
		else if(player.getCenterX() < tile.getMinX() && player.getCenterY() > tile.getMaxY()){
			//BL Corner
			return 7;
		}
		else if(player.getCenterX() > tile.getMaxX() && player.getCenterY() > tile.getMaxY()){
			//BR Corner
			return 8;
		}
		else{
			return 9;
		}
	}

	
	// Tick method (update methods)
	public void tick ()
	{

		if (jumping) // If player is jumping set gravity
		{
			gravity -= 0.4;
			setVelY ((int) gravity);
			if (gravity <= 0.0)
			{
				jumping = false;
				falling = true;
			}
		}

		if (falling) // If falling set gravity
		{
			gravity += 0.4;
			setVelY ((int) gravity);
		}


		// Set temp x and temp y to players position next frame
		tempx = (int) (x + velX);
		tempy = (int) (y + velY);

		
		Rectangle temp = new Rectangle ((int) tempx,(int) tempy, 30, 30); // Temporary hit box for players position next frame

		ArrayList<Rectangle> collisionRects = new ArrayList<Rectangle>(); // Make array list rectangles that player collides with

		
		// Loop through each rectangle and check for collision
		for(int i = 0; i <= 29; i++){

			if(temp.intersects(map[i])){
				collisionRects.add(map[i]);
			}
		}

		if (!collisionRects.isEmpty()){
			//Run collision resolution on each rectangle
			for (int i = 0; i < collisionRects.size(); i++)
			{
				//Do collision resolution
				//Check side of collision with collisionRect.get(i)
				//Based on side set positions, velocities, or gravity etc.
				int side = getCollisionSide(temp, collisionRects.get(i));

				if (side == 1){
					//Top
					velY = 0;
					gravity = 0;
				}
				else if (side == 2){
					//Left
					velX = 0;
				}
				else if (side == 3){
					//Right
					velX = 0;
				}
				else if (side == 4){
					velY = 0;
				}
				else if (side == 5){
					//TL
					/*
					if (velX > 0){
						x = collisionRects.get(i).getMinX() - hitBox.getWidth();
						velX = 0;
					}
					else{
						y = collisionRects.get(i).getMinY() - hitBox.getHeight();
						velY = 0;
					}
					 */
					velX = 0;
					velY = 0;
					gravity = 0;

				}
				else if (side == 6){
					//TR
					velX = 0;
					velY = 0;
					gravity = 0;

				}
				else if (side == 7){
					//BL
					velX = 0;
					velY = 0;
					gravity = 0;

				}
				else if (side == 8){
					//BR
					velX = 0;
					velY = 0;
					gravity = 0;
				}
				else{
					System.out.println("SOMETHINGS WRONG!!!!!"); // If unknown collision
				}	
			}
		}

		x += velX; // Update players x position depending on velocity
		y += velY; // Update players y position depending on velocity
		cameraOffset += velX; // Set camera off set to players x movement

		// Player movement restrictions (Far left and right side of screen)
		if (x <= 392) 
			x = 392;

		if (x >= 3208)
			x = 3208;

		// Camera movement restrictions (Far left and right side of screen)
		if (cameraOffset <= 0)
			cameraOffset = 0;

		if (cameraOffset >= 2200)
			cameraOffset = 2200;

		// Players y restriction
		if (y <= 0)
			y = 0;

		// Set animation depending on which way player is moving
		if (right){
			animRight.runAnimation();
		}
		if (left){
			animLeft.runAnimation();
		}

		// Check for collision between player and meteors/data objects
		for(int i = 0; i < game.mb.size(); i++){
			EntityB tempEnt = game.mb.get(i);

			if(Physics.Collision(this, tempEnt)){
				controller.removeEntity(tempEnt);
				Game.HEALTH -= 1;
				game.setMeteorite_kill(game.getMeteorite_kill() + 1);
			}

		}

		// If player falls off screen set health to zero (game over)
		if (y > 650)
			Game.HEALTH = 0;

		// Check for collision between bullet and meteor (create data object)
		for(int i = 0; i < game.mc.size(); i++){
			EntityC tempEnt2 = game.mc.get(i);

			if(Physics.Collision(this, tempEnt2)){
				controller.removeEntity(tempEnt2);
				Game.state = Game.STATE.INFO;
				dataCounter += 1;
			}
		}

		hitBox = getBounds(); // set hit box to bounds of player
	}

	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, 32, 32);
	}


	public void render (Graphics g)
	{
		g.setColor(Color.RED);
		
		//-------------------------------------------------------------------------------------------DRAW HIT BOXES--------------------------------------------------------------------------------------

		// g.drawRect(392, hitBox.y, hitBox.width, hitBox.height);

			//for(int i = 0; i <= 29; i++){
			//	g.drawRect((map[i].x)-cameraOffset, map[i].y, map[i].width, map[i].height);
			//}


		// Set animations depending on which way player is moving
		if (right){
			animRight.drawAnimation(g, 392, y, 0);		
		}

		if (left){
			animLeft.drawAnimation(g, 392, y, 0);
		}

		if (notMoving){
			g.drawImage (tex.player, (int) 392, (int) y, null);
		}

		if (playerIsJumping){
			g.drawImage (tex.playerJumping, (int) 392, (int) y, null);
		}

	}

	
	// Getters and Setters for players position and velocity 
	public double getX ()
	{
		return x;
	}


	public double getY ()
	{
		return y;
	}


	public double getVelY ()
	{
		return velY;
	}


	public void setX (double x)
	{
		this.x = x;
	}


	public void setY (double y)
	{
		this.y = y;
	}


	public void setVelX (double velX)
	{
		this.velX = velX;
	}


	public void setVelY (double velY)
	{
		this.velY = velY;
	}
}


