import java.awt.Graphics;
import java.awt.Rectangle;


public class Data extends GameObject implements EntityC{

	private Textures tex;
	private Game game;

	Animation anim; 

	public Data(double x, double y, Textures tex, Game game){
		super (x,y);
		this.tex = tex;
		this.game = game;

		anim = new Animation(4, tex.data[0], tex.data[1], tex.data[2], tex.data[3], tex.data[4], tex.data[5]);
	}

	public void tick(){ // tick (update method)

		y += 3; // falling

		
		// Hard code map
		if (y >= 485)
			y = 485;

		else if (x <= 842 && x > 762 && y >= 228)
			y = 228;

		else if (x <= 1032 && x > 842 && y >= 117)
			y = 117;

		else if (x <= 460 && x > 422 && y >= 330)
			y = 330;

		else if (x <= 500 && x > 460  && y >= 293)
			y = 293;
		// Second box
		else if (x <= 580 &&  x > 500 && y >= 253)
			y = 253;
		// First grass
		else if (x <= 655 &&  x > 570 && y >= 363)
			y = 363;
		// Second grass
		else if (x <= 700 &&  x > 655 && y >= 405)
			y = 405;
		// bottom grass
		else if (x <= 840 &&  x > 700 && y >= 440)
			y = 440;

		else if (x <= 900  &&  x > 840 && y >= 405)
			y = 405;
		//Ladder//
		else if (x <= 1360  &&  x > 900 && y >= 253)
			y = 253;
		//Initial drop//
		else if (x <= 1445  &&  x > 1360 && y >= 470)
			y = 470; 
		//Barrel
		else if (x <= 1480  &&  x > 1445 && y >= 435)
			y = 435; 
		else if (x <= 1515  &&  x > 1480 && y >= 397)
			y = 397;
		else if (x <= 1895  &&  x > 1515 && y >= 359)
			y = 359;
		//box
		else if (x <= 1980  &&  x > 1895 && y >= 322)
			y = 322;
		else if (x <= 2403  &&  x > 1980 && y >= 285)
			y = 285;
		// last box
		else if (x <= 2450  &&  x > 2403 && y >= 250)
			y = 250;
		else if (x <= 2495  &&  x > 2450 && y >= 285)
			y = 285;


		anim.runAnimation(); // Run animation
	}	

	public Rectangle getBounds(){ // Hit box for data objects
		return new Rectangle((int)x, (int)y, 7, 7);
	}

	public void render(Graphics g){ // Render data object animation
		anim.drawAnimation(g, (x - Player.cameraOffset), y, 5);
	}

	// Getters for data object position
	public double getX(){
		return x;
	}

	public double getY(){
		return y;
	}

}





