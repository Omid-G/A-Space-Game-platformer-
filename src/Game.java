
import java.awt.Canvas;
import java.awt.Dimension;


import javax.swing.JFrame;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.LinkedList;
import java.awt.event.KeyEvent;


/* TITLE: Space Game || Grade 12 Final summative 
 * AUTHOR: Omid Ghayouri
 * DATE: June 07, 2016
 * -----------------------------------------------------------------------------------------------------------------------------------
 * PURPOSE: An educational Program that can be used as a study aid for students ranging from grades 6 to 8.
			The program teaches students about the planets in the solar system and allows them to apply
			their knowledge through an interactive test.
 * SUMMARY: When the program starts the user is shown a menu. The user can choose between play, controls,
            database, test and quit using the arrow keys on the keyboard. Clicking play allows to user to
            play a game where the objective is to collect data objects while avoiding meteors. When the
            user collects a piece of data, information on a specific planet in the solar system is displayed.
            The user is encouraged to read it while learning about the specific planet. Once all 8 data
            objects are collected the user can go back to the main menu where database state will be 
            unlocked. Within the database the user can re-read all of the info they have gathered from the
            test. Once the user thinks they know everything they can go back to the menu and start the test.
            The test consists of 10 multiple choice questions based on the info they have previously read.
            At the end of the test the user is shown their score and the specific questions and answers given.
            Here the user can choose to exit the program or continue playing the game.
			
 * INPUT: Menu 		- up and down arrow keys used to navigate through the menu
 			   		- enter key used to enter a state
 		  Play 		- Left, Right, Up, Down used to control the character
 		  	   		- P to pause the game
 		  	   		- Enter to go back
 		  	   		- X and C to fire
 	      Controls  - Enter to go back
 	      DataBase  - Up and Down to Scroll
 	       			- Enter to go back
 	      Test 		- Mouse input to select radio buttons
 	      			- Mouse input to go to next question
 	      			- Enter key to go back
 
 * OUTPUT: Menu 	- Display Menu image based up and down arrow keys
 		   Play 	- Render character location on screen depending on arrow key input
 		  	   		- Render bullet texture depending on characters location and X or C input
 		  	   		- Display pause screen if P is pressed
 	       Controls - Display controls image
 	       DataBase - Render information image based on up or down arrow key
 	       Test 	- Add radio buttons and next button
 	       			- Display questions, answers and score at the end of test
 	       			 
 * NOTES:  - Some textures taken from "Bro Force"
 		   - Music taken from youtube
 		   - Images made by me on Photoshop

 */


public class Game extends Canvas implements Runnable
{

	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 800;  // Game window Width
	public static final int HEIGHT = 600; // Game window Height

	public final String TITLE = "" +
			"A Space Game: By Omid Ghayouri";  // Game window label

	private boolean running = false; // Running animation set to false
	private boolean back = false; // Allow user to return to menu set to false
	private boolean startQuiz = true; // StartQuiz set to true


	private Thread thread; 

	private BufferedImage image = new BufferedImage (WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB); // Render images to size of window
	
	// Set image variables 
	
	private BufferedImage spriteSheet = null; 
	private BufferedImage background = null; 
	private BufferedImage backgroundSpace = null;
	private BufferedImage controlScreen = null;
	private BufferedImage lockScreen = null;
	private BufferedImage dataBase = null;
	private BufferedImage dataBaseEmpty = null;
	private BufferedImage testStarting = null;
	public BufferedImage[] dataInfo = new BufferedImage[8];
	private BufferedImage[] gameMenu = new BufferedImage[5];
	private BufferedImage[] gameHealth = new BufferedImage[6];
	private BufferedImage[] gameOver = new BufferedImage[2];
	private BufferedImage[] gamePauseScreen = new BufferedImage[2];
	
//	private SoundClip clip = new SoundClip("res/mainMenuClip.mp3");

	// Set initial menu images
	
	int menuImage = 0;
	int gameOverImage = 0;
	int gamePause = 0;
	int scroll = 0;

	private boolean is_shooting = false; // Set player shooting to false to stop rapid fire
	private boolean unlockTest = false; // Set unlock test to false so that user cannot enter the test right away

	private int meteorite_count = 3; // Set initial meteorite count to 3
	private int meteorite_kill = 0; // Set initial amount of meteorites killed to 0

	// Declare player, controller, textures and camera
	
	private Test t;
	private Player p;
	private Controller c;
	private Textures tex;
	Camera cam;

	// Declare linked lists for entities
	
	public LinkedList<EntityA> ma;
	public LinkedList<EntityB> mb;
	public LinkedList<EntityC> mc;

	// Initialize health to 5
	
	public static int HEALTH = 5; 

	// declare sates for program
	
	public enum STATE{
		MENU,
		GAME,
		CONTROLS,
		TEST,
		DATA,
		GAMEOVER,
		INFO,
		PAUSE,
		LOCKED

	};
	
	// Initialize state to menu
	
	public static STATE state = STATE.MENU;

	public void init ()
	{
		requestFocus (); // Focus keyboard inputs to window (stops user having to click on the window before interacting with it)
		BufferedImageLoader loader = new BufferedImageLoader (); // Initilize Buffered image loader
		try
		{

			// Load images into correct variables/arrays
			
			spriteSheet = loader.loadImage ("res/sprite_sheet.png");
			background = loader.loadImage("res/game_background.png");
			backgroundSpace = loader.loadImage("res/game_backgroundSky.png");
			controlScreen = loader.loadImage("res/GameControlsScreen.png");
			lockScreen = loader.loadImage("res/LockScreen.png");
			testStarting = loader.loadImage("res/testStarting.png");

			dataBase = loader.loadImage("res/DataBase.png");
			dataBaseEmpty = loader.loadImage("res/DataBaseEmpty.png");

			gameMenu[0] = loader.loadImage("res/GameMenuBackgroundPlay.png");
			gameMenu[1] = loader.loadImage("res/GameMenuBackgroundControls.png");
			gameMenu[2] = loader.loadImage("res/GameMenuBackgroundViewDatabase.png");
			gameMenu[3] = loader.loadImage("res/GameMenuBackgroundTest.png");
			gameMenu[4] = loader.loadImage("res/GameMenuBackgroundQuit.png");

			gameHealth[0] = loader.loadImage("res/Health0.png");
			gameHealth[1] = loader.loadImage("res/Health1.png");
			gameHealth[2] = loader.loadImage("res/Health2.png");
			gameHealth[3] = loader.loadImage("res/Health3.png");
			gameHealth[4] = loader.loadImage("res/Health4.png");
			gameHealth[5] = loader.loadImage("res/Health5.png");

			gameOver[0] = loader.loadImage("res/gameoverreturntomenu.png");
			gameOver[1] = loader.loadImage("res/gameoverquit.png");

			gamePauseScreen[0] = loader.loadImage("res/gamePausedreturntomenu.png");
			gamePauseScreen[1] = loader.loadImage("res/gamePausedquit.png");

			dataInfo[0] = loader.loadImage("res/dataInfo0.png");
			dataInfo[1] = loader.loadImage("res/dataInfo1.png");
			dataInfo[2] = loader.loadImage("res/dataInfo2.png");
			dataInfo[3] = loader.loadImage("res/dataInfo3.png");
			dataInfo[4] = loader.loadImage("res/dataInfo4.png");
			dataInfo[5] = loader.loadImage("res/dataInfo5.png");
			dataInfo[6] = loader.loadImage("res/dataInfo6.png");
			dataInfo[7] = loader.loadImage("res/dataInfo7.png");


		}
		catch (IOException e)
		{
			e.printStackTrace ();
		}

		addKeyListener (new KeyInput (this)); // Add key listener for keyboard input

		// Initialize textures, Test, Controller, player, entities and meteorite
		
		tex = new Textures(this); 

		t = new Test ();
		c = new Controller(tex, this);
		p = new Player (392, 270, tex, this, c);


		ma = c.getEntityA();
		mb = c.getEntityB();
		mc = c.getEntityC();


		c.createMeteorite(meteorite_count);

	}

	
	// Start thread function

	private synchronized void start ()
	{
		if (running)
			return;

		running = true;
		thread = new Thread (this);
		thread.start ();
	}

	// Stop thread function
	
	private synchronized void stop ()
	{
		if (!running)
			return;

		running = false;
		try
		{
			thread.join ();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace ();
		}
		System.exit (1);
	}

	// Run methods (set FPS)
	
	public void run ()
	{

		init ();
		long lastTime = System.currentTimeMillis ();
		final double amountOfTicks = 60.0;
		double ns = 1000 / amountOfTicks; // 1 billion instead of 1000 used here (nano time)
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis ();


		while (running)
		{
			//this would be the game loop
			long now = System.currentTimeMillis ();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1)
			{
				tick ();
				updates++;
				delta--;

			}
			render ();
			frames++;

			if (System.currentTimeMillis () - timer > 1000)
			{
				timer += 1000;
				System.out.println (updates + " Ticks, Fps " + frames);
				updates = 0;
				frames = 0;
			}
		}
		stop ();
	}

	
	// Tick method (Update loop)
	
	private void tick ()
	{

		if(state == STATE.GAME){ // If in game state, run tick methods (update loop)
			p.tick ();
			c.tick();


			if(meteorite_kill >= meteorite_count){
				meteorite_count += 0;
				meteorite_kill = 0;
				c.createMeteorite(meteorite_count);
			}
		}
	}


// Render methods (Everything that is displayed on screen is in here)
	
	private void render ()
	{

		// BufferedStrategy (Loads next 3 frames before displaying it)
		BufferStrategy bs = this.getBufferStrategy ();

		if (bs == null)
		{ 
			createBufferStrategy (3); // Buffer 3 frames in advance
			bs = this.getBufferStrategy();

			return;
		}

		// Declare graphics
		
		Graphics g = bs.getDrawGraphics ();


		g.drawImage (image, 0, 0, getWidth (), getHeight (), this); // draw background


		//-------------------------------------------------------------------------------------------TEST STATE--------------------------------------------------------------------------------------
		
		if(state == STATE.TEST){
			g.drawImage(testStarting, 0, 0, null);
			back = true;
			if(startQuiz){
				startQuiz = false;
				new Quiz();
			}
		}
		
		//-------------------------------------------------------------------------------------------LOCKED STATE--------------------------------------------------------------------------------------


		if (state == STATE.LOCKED){
			g.drawImage(lockScreen, 0, 0, null);
			back = true;
		}
		
		//-------------------------------------------------------------------------------------------DATA STATE--------------------------------------------------------------------------------------

		if(state == STATE.DATA){
			back = true;
			if (Player.dataCounter == 0)
				g.drawImage(dataBaseEmpty, 0, 0, null);
			else{
				g.drawImage(dataBase, 0, scroll, null);
			}
		}
		
		//-------------------------------------------------------------------------------------------PAUSED STATE--------------------------------------------------------------------------------------

		if (state == STATE.PAUSE){
			g.drawImage(backgroundSpace, -((Player.cameraOffset)/2), 0, null);
			g.drawImage(background, -(Player.cameraOffset), 10, null);
			p.render (g);
			c.render(g);
			g.drawImage(gameHealth[HEALTH], 5, 5, null);
			g.drawImage(gamePauseScreen[gamePause], 0, 0, null);
		}

		//-------------------------------------------------------------------------------------------CONTROLS STATE--------------------------------------------------------------------------------------

		if(state == STATE.CONTROLS){
			g.drawImage(controlScreen, 0, 0, null);
			back = true;
		}

		//-------------------------------------------------------------------------------------------GAME STATE--------------------------------------------------------------------------------------
		
		if(state == STATE.GAME){

			g.drawImage(backgroundSpace, -((Player.cameraOffset)/2), 0, null);
			g.drawImage(background, -(Player.cameraOffset), 10, null);

			p.render (g);
			c.render(g);
			g.drawImage(gameHealth[HEALTH], 5, 5, null);

			if (HEALTH == 0){
				state = STATE.GAMEOVER;
			}		
		}
		
		//-------------------------------------------------------------------------------------------INFO STATE--------------------------------------------------------------------------------------

		if(state == STATE.INFO){

			if (Player.dataCounter == 1){
				g.drawImage(backgroundSpace, -((Player.cameraOffset)/2), 0, null);
				g.drawImage(background, -(Player.cameraOffset), 10, null);
				p.render (g);
				c.render(g);
				g.drawImage(gameHealth[HEALTH], 5, 5, null);
				g.drawImage(dataInfo[(Player.dataCounter) - 1], 0, 0, null);
			}
			if (Player.dataCounter == 2){
				g.drawImage(backgroundSpace, -((Player.cameraOffset)/2), 0, null);
				g.drawImage(background, -(Player.cameraOffset), 10, null);
				p.render (g);
				c.render(g);
				g.drawImage(gameHealth[HEALTH], 5, 5, null);
				g.drawImage(dataInfo[(Player.dataCounter) - 1], 0, 0, null);
			}
			if (Player.dataCounter == 3){
				g.drawImage(backgroundSpace, -((Player.cameraOffset)/2), 0, null);
				g.drawImage(background, -(Player.cameraOffset), 10, null);
				p.render (g);
				c.render(g);
				g.drawImage(gameHealth[HEALTH], 5, 5, null);
				g.drawImage(dataInfo[(Player.dataCounter) - 1], 0, 0, null);
			}
			if (Player.dataCounter == 4){
				g.drawImage(backgroundSpace, -((Player.cameraOffset)/2), 0, null);
				g.drawImage(background, -(Player.cameraOffset), 10, null);
				p.render (g);
				c.render(g);
				g.drawImage(gameHealth[HEALTH], 5, 5, null);
				g.drawImage(dataInfo[(Player.dataCounter) - 1], 0, 0, null);
			}
			if (Player.dataCounter == 5){
				g.drawImage(backgroundSpace, -((Player.cameraOffset)/2), 0, null);
				g.drawImage(background, -(Player.cameraOffset), 10, null);
				p.render (g);
				c.render(g);
				g.drawImage(gameHealth[HEALTH], 5, 5, null);
				g.drawImage(dataInfo[(Player.dataCounter) - 1], 0, 0, null);
			}
			if (Player.dataCounter == 6){
				g.drawImage(backgroundSpace, -((Player.cameraOffset)/2), 0, null);
				g.drawImage(background, -(Player.cameraOffset), 10, null);
				p.render (g);
				c.render(g);
				g.drawImage(gameHealth[HEALTH], 5, 5, null);
				g.drawImage(dataInfo[(Player.dataCounter) - 1], 0, 0, null);
			}
			if (Player.dataCounter == 7){
				g.drawImage(backgroundSpace, -((Player.cameraOffset)/2), 0, null);
				g.drawImage(background, -(Player.cameraOffset), 10, null);
				p.render (g);
				c.render(g);
				g.drawImage(gameHealth[HEALTH], 5, 5, null);
				g.drawImage(dataInfo[(Player.dataCounter) - 1], 0, 0, null);
			}
			if (Player.dataCounter == 8){
				g.drawImage(backgroundSpace, -((Player.cameraOffset)/2), 0, null);
				g.drawImage(background, -(Player.cameraOffset), 10, null);
				p.render (g);
				c.render(g);
				g.drawImage(gameHealth[HEALTH], 5, 5, null);
				g.drawImage(dataInfo[(Player.dataCounter) - 1], 0, 0, null);
				unlockTest = true;
			}


		}

		//-------------------------------------------------------------------------------------------GAMEOVER STATE--------------------------------------------------------------------------------------
		
		if(state == STATE.GAMEOVER){
			g.drawImage(backgroundSpace, -((Player.cameraOffset)/2), 0, null);
			g.drawImage(background, -(Player.cameraOffset), 10, null);
			p.render (g);
			c.render(g);
			g.drawImage(gameHealth[HEALTH], 5, 5, null);
			g.drawImage(gameOver[gameOverImage], 0, 0, null);

		}
		
		//-------------------------------------------------------------------------------------------MENU STATE--------------------------------------------------------------------------------------

		else if(state == STATE.MENU){

			//clip.play();
			g.drawImage(gameMenu[menuImage], 0, 0, null);
		}

		g.dispose ();
		bs.show ();
	}


	// method to check for keyboard input

	public void keyPressed (KeyEvent e)
	{
		int key = e.getKeyCode ();

		//-------------------------------------------------------------------------------------------MENU--------------------------------------------------------------------------------------
		if (state == STATE.MENU){

			if (key == KeyEvent.VK_DOWN)
			{
				if (menuImage < 4)
					menuImage++;
				else
					menuImage = 0;
			}

			if (key == KeyEvent.VK_UP)
			{
				if (menuImage > 0)
					menuImage--;
				else
					menuImage = 4;
			}

			if (key == KeyEvent.VK_ENTER && menuImage == 0){
				if (HEALTH == 0)
					HEALTH = 5;
				state = STATE.GAME;
			}

			if (key == KeyEvent.VK_ENTER && menuImage == 4){
				System.exit(1);
			}

			if (key == KeyEvent.VK_ENTER && menuImage == 2){				
				state = STATE.DATA;

			}

			if (key == KeyEvent.VK_ENTER && menuImage == 1){
				state = STATE.CONTROLS;
			}

			if (key == KeyEvent.VK_ENTER && menuImage == 3  && unlockTest){
				state = STATE.TEST;
			}

			else if (key == KeyEvent.VK_ENTER && menuImage == 3){
				state = STATE.LOCKED;
			}

		}
		
		//-------------------------------------------------------------------------------------------CONTROLS/LOCKED/TEST--------------------------------------------------------------------------------------

		if (state == STATE.CONTROLS || state == STATE.LOCKED || state == STATE.TEST){
			if (key == KeyEvent.VK_ENTER && back == true)
			{
				back = false;
				state = STATE.MENU;
			}
		}

		//-------------------------------------------------------------------------------------------DATA--------------------------------------------------------------------------------------
		if (state == STATE.DATA){
			if(key == KeyEvent.VK_UP)
				scroll += 8;
			if (key == KeyEvent.VK_DOWN)
				scroll -= 8;
			if (scroll > 0)
				scroll = 0;
			if ((Player.dataCounter == 1 && scroll < 0))
				scroll = 0;
			if ((Player.dataCounter == 2 && scroll <= -581))
				scroll = -581;
			if ((Player.dataCounter == 3 && scroll <= -1163))
				scroll = -1163;
			if ((Player.dataCounter == 4 && scroll <= -1745))
				scroll = -1745;
			if ((Player.dataCounter == 5 && scroll <= -2327))
				scroll = -2327;
			if ((Player.dataCounter == 6 && scroll <= -2909))
				scroll = -2909;
			if ((Player.dataCounter == 7 && scroll <= -3491))
				scroll = -3491;
			if ((Player.dataCounter == 8 && scroll <= -4073))
				scroll = -4073;
			if (key == KeyEvent.VK_ENTER && back){
				back = false;
				state = STATE.MENU;
			}
		}

		//-------------------------------------------------------------------------------------------PAUSE MENU--------------------------------------------------------------------------------------

		if (state == STATE.PAUSE){

			if (key == KeyEvent.VK_DOWN)
			{
				if (gamePause == 0)
					gamePause = 1;
				else
					gamePause = 0;
			}

			if (key == KeyEvent.VK_UP)
			{
				if (gamePause == 1)
					gamePause = 0;
				else
					gamePause = 1;
			}

			if (key == KeyEvent.VK_ENTER && gamePause == 0){
				state = STATE.GAME;
			}

			if (key == KeyEvent.VK_ENTER && gamePause == 1){
				HEALTH = 5;
				state = STATE.MENU;
			}

		}

		//-------------------------------------------------------------------------------------------GAME--------------------------------------------------------------------------------------

		if(state == STATE.GAME){

			if (key == KeyEvent.VK_P)
			{		
				state = STATE.PAUSE;
			}


			if (key == KeyEvent.VK_RIGHT)
			{
				p.setVelX (5);
				p.right = true;
				p.left = false;
				p.notMoving = false;
				p.playerIsJumping = false;

			}

			else if (key == KeyEvent.VK_LEFT)
			{
				p.setVelX (-5);		
				p.left = true;
				p.right = false;
				p.notMoving = false;
				p.playerIsJumping = false;

			}
			else if (key == KeyEvent.VK_DOWN)
			{
				p.setVelY (10);
			}
			else if (key == KeyEvent.VK_UP)
			{

				if (!p.jumping && (p.getY() == 282 || p.getY() == 330 || p.getY() == 292 || p.getY() == 255 || p.getY() == 366 || p.getY() == 405 || p.getY() == 442 || p.getY() == 254 || p.getY() == 142|| p.getY() == 435|| p.getY() == 546 || p.getY() == 510 || p.getY() == 470 || p.getY() == 400 || p.getY() == 433 || p.getY() == 358 || p.getY() == 322 || p.getY() == 285 || p.getY() == 435 || p.getY() == 475))
				{
					p.jumping = true;
					p.gravity = -10.0;
				}

			}
			else if (key == KeyEvent.VK_C && !is_shooting)
			{
				is_shooting = true; // If player is shooting set to true
				c.addEntity(new Bullet(p.getX(), p.getY(), tex, this)); // Create new bullet at players position
			}
			else if (key == KeyEvent.VK_X && !is_shooting)
			{
				is_shooting = true; // If player is shooting set to true
				c.addEntity(new BulletLeft(p.getX(), p.getY(), tex, this)); // Create new bullet at players position

			}

		}

		//-------------------------------------------------------------------------------------------INFO--------------------------------------------------------------------------------------

		if (state == STATE.INFO){
			if (key == KeyEvent.VK_ENTER)
			{
				state = STATE.GAME;
			}

			if (key == KeyEvent.VK_M)
			{
				state = STATE.MENU;
			}
		}

		//-------------------------------------------------------------------------------------------GAME OVER--------------------------------------------------------------------------------------
		if (state == STATE.GAMEOVER){

			if (key == KeyEvent.VK_DOWN)
			{
				if (gameOverImage == 0)
					gameOverImage = 1;
				else
					gameOverImage = 0;
			}

			if (key == KeyEvent.VK_UP)
			{
				if (gameOverImage == 1)
					gameOverImage = 0;
				else
					gameOverImage = 1;
			}

			if (key == KeyEvent.VK_ENTER && gameOverImage == 0){
				HEALTH = 5;
				state = STATE.MENU;
			}

			if (key == KeyEvent.VK_ENTER && gameOverImage == 1){
				System.exit(1);
			}
		}
	}



// Method for when a key is released
	public void keyReleased (KeyEvent e)
	{
		int key = e.getKeyCode ();


		if (key == KeyEvent.VK_RIGHT && !p.left ) // If arrow key is released set player velocity to zero
		{
			p.setVelX (0);
			p.notMoving = true;
			p.right = false;
			p.left = false;
			p.playerIsJumping = false;

		}
		else if (key == KeyEvent.VK_LEFT && !p.right) // If arrow key is released set player velocity to zero
		{
			p.setVelX (0);
			p.notMoving = true;
			p.right = false;
			p.left = false;
			p.playerIsJumping = false;
		}
		else if (key == KeyEvent.VK_DOWN) // If arrow key is released set player velocity to zero
		{
			p.setVelY (0);
		}
		else if (key == KeyEvent.VK_UP) // If arrow key is released sey player velocity to zero
		{
			p.setVelY (0);
			p.playerIsJumping = true;
			p.notMoving = false;
			p.right = false;
			p.left = false;

		}

		else if (key == KeyEvent.VK_C)
		{
			is_shooting = false; // If fire key is released set shooting to false
		}
		else if (key == KeyEvent.VK_X)
		{
			is_shooting = false; // If fire key is released set shooting to false
		}
	}


	public static void main (String args[])
	{
		Game game = new Game (); // create new game

		game.setSize (new Dimension (WIDTH, HEIGHT)); // Set size of window

		JFrame frame = new JFrame (game.TITLE);
		frame.getContentPane ().add (game);
		frame.pack ();
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE); // Close operation
		frame.setResizable (false); // Make window static
		frame.setLocationRelativeTo (null);
		frame.setVisible (true);

		game.start (); // Start thread

	}

	public BufferedImage getSpriteSheet ()
	{
		return spriteSheet;
	}


	public int getMeteorite_count() {
		return meteorite_count;
	}


	public void setMeteorite_count(int meteorite_count) {
		this.meteorite_count = meteorite_count;
	}


	public int getMeteorite_kill() {
		return meteorite_kill;
	}


	public void setMeteorite_kill(int meteorite_kill) {
		this.meteorite_kill = meteorite_kill;
	}


}


