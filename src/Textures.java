import java.awt.image.BufferedImage;


public class Textures {

	public BufferedImage player;
	public BufferedImage playerJumping;
	public BufferedImage[] data = new BufferedImage[6];
	public BufferedImage[] playerRight = new BufferedImage[3];
	public BufferedImage[] playerLeft = new BufferedImage[3];
	public BufferedImage[] bullet = new BufferedImage[2];
	public BufferedImage[] meteorite = new BufferedImage[6];
	public BufferedImage[] meteoriteHit = new BufferedImage[4];

	private SpriteSheet ss;

	public Textures(Game game){
		ss = new SpriteSheet(game.getSpriteSheet());

		getTextures();
	}

	private void getTextures(){

		data[0] = ss.grabImage(4, 4, 32, 32);
		data[1] = ss.grabImage(5, 4, 32, 32);
		data[2] = ss.grabImage(6, 4, 32, 32);
		data[3] = ss.grabImage(7, 4, 32, 32);
		data[4] = ss.grabImage(8, 4, 32, 32);
		data[5] = ss.grabImage(4, 5, 32, 32);
		//Not moving
		player = ss.grabImage(2, 1, 32, 32);

		//Jumping
		playerJumping = ss.grabImage(1, 1, 32, 32);

		// Run right
		playerRight[0] = ss.grabImage(1, 5, 32, 32);
		playerRight[1] = ss.grabImage(2, 5, 32, 32);
		playerRight[2] = ss.grabImage(3, 5, 32, 32);

		//Run left
		playerLeft[0] = ss.grabImage(1, 4, 32, 32);
		playerLeft[1] = ss.grabImage(2, 4, 32, 32);
		playerLeft[2] = ss.grabImage(3, 4, 32, 32);

		bullet[0] = ss.grabImage(7, 1, 32, 32);
		bullet[1] = ss.grabImage(8, 1, 32, 32);


		meteorite[0] = ss.grabImage(1,2,32,64); 
		meteorite[1] = ss.grabImage(2,2,32,64); 
		meteorite[2] = ss.grabImage(3,2,32,64); 
		meteorite[3] = ss.grabImage(4,2,32,64); 
		meteorite[4] = ss.grabImage(5,2,32,64); 
		meteorite[5] = ss.grabImage(6,2,32,64); 

		meteoriteHit[0] = ss.grabImage(1,7,64,64);
		meteoriteHit[1] = ss.grabImage(3,7,64,64); 
		meteoriteHit[2] = ss.grabImage(5,7,64,64); 
		meteoriteHit[3] = ss.grabImage(7,7,64,64); 


	}

}
