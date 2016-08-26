import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Meteorite extends GameObject implements EntityB {

	// Declare variables
	Random r = new Random();
	private Game game;
	private Controller c;

	private Textures tex;
	private int speed = r.nextInt(3) + 1;

	Animation anim;

	public Meteorite(double x, double y, Textures tex, Controller c, Game game){
		super (x,y);
		this.tex = tex;
		this.c = c;
		this.game = game;

		anim = new Animation(3, tex.meteorite[0], tex.meteorite[1], tex.meteorite[2], tex.meteorite[3], tex.meteorite[4], tex.meteorite[5]);
		
	}

	public void tick(){ // Tick (update) methods
		y += speed; // Speed of meteor

		if(y > 505){ // spawn position of meteor
	
			speed = r.nextInt(3) + 1;
			x = (r.nextInt(2000)) + 600;
			y = -10;
		}
		
		if(x < 600){
			
			speed = r.nextInt(3) + 1;
			x = (r.nextInt(2000)) + 600;
			y = -10;
		}
		
		// Collision between Meteor and map (hard coded) 
		
		if (x <= 422 && y >= 255 || x <= 842 && x > 762 && y >= 228 || x <= 1032 && x > 842 && y >= 117 || x <= 460 && x > 422 && y >= 305 || x <= 500 && x > 460  && y >= 268 || x <= 580 &&  x > 500 && y >= 228 || x <= 655 &&  x > 570 && y >= 338 || x <= 700 &&  x > 655 && y >= 380 || x <= 840 &&  x > 700 && y >= 415 || x <= 900  &&  x > 840 && y >= 380 || x <= 1360  &&  x > 900 && y >= 228 || x <= 1445  &&  x > 1360 && y >= 445 || x <= 1480  &&  x > 1445 && y >= 410 || x <= 1515  &&  x > 1480 && y >= 372 || x <= 1895  &&  x > 1515 && y >= 334 || x <= 1980  &&  x > 1895 && y >= 297 || x <= 2403  &&  x > 1980 && y >= 260 || x <= 2450  &&  x > 2403 && y >= 225 || x <= 2495  &&  x > 2450 && y >= 260){
			speed = r.nextInt(3) + 1;
			x = (r.nextInt(2200)) + 600;
			y = -10;
		}


		// Check for collision between each meteor
		for(int i = 0; i < game.ma.size(); i++){
			EntityA tempEnt = game.ma.get(i);

			if(tempEnt.getX() > 3200){
				c.removeEntity(tempEnt);
			}

			if(Physics.Collision(this, tempEnt)){
				c.removeEntity(tempEnt);
				c.removeEntity(this);
				game.setMeteorite_kill(game.getMeteorite_kill() + 1); // add more when shot
				c.addEntity(new Data((r.nextInt(2000)) + 600, 0, tex, game));
			}
			

			if(y > 505 ){
				
				c.removeEntity(tempEnt);
				c.removeEntity(this);

				game.setMeteorite_kill(game.getMeteorite_kill() + 1); // add more when shot

			}

		}

		anim.runAnimation(); // Run animation

	}

	public void render(Graphics g){ // Render meteorite 
		anim.drawAnimation(g, (x - Player.cameraOffset), y, 0);

	}

	public Rectangle getBounds(){ // Get bounds of meteorite
		return new Rectangle((int)x, (int)y, 30, 46);
	}

	//Getters and Setters of meteorite
	
	public double getY(){
		return y;
	}

	public void setY(double y){
		this.y = y;
	}


	public double getX() {

		return x;
	}
}

