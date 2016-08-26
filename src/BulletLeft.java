import java.awt.Graphics;
import java.awt.Rectangle;


public class BulletLeft extends GameObject implements EntityA{


	private Textures tex;
	private Game game;

	Animation anim; 


	public BulletLeft(double x, double y, Textures tex, Game game){
		super (x,y);
		this.tex = tex;
		this.game = game;

		anim = new Animation(2, tex.bullet[0], tex.bullet[1]);

	}

	public void tick(){

		x -= 10;


		anim.runAnimation();
	}	

	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, 7, 7);
	}

	public void render(Graphics g){
		anim.drawAnimation(g, (x - Player.cameraOffset), y, 5);
	}

	public double getX(){
		return x;
	}

	public double getY(){
		return y;
	}


}





