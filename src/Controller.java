import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;


public class Controller {

	private LinkedList<EntityA> ma = new LinkedList<EntityA>();
	private LinkedList<EntityB> mb = new LinkedList<EntityB>();
	private LinkedList<EntityC> mc = new LinkedList<EntityC>();

	EntityA enta;
	EntityB entb;
	EntityC entc;
	private Textures tex;
	Random r = new Random();
	private Game game;
	
	public Controller(Textures tex, Game game){
		this.tex = tex;
		this.game = game;
		
	}
	
	public void createMeteorite(int meteorite_count){
		for(int i = 0; i < meteorite_count; i++){
			addEntity(new Meteorite(r.nextInt(Game.WIDTH), -10, tex, this, game));
		}
	}
		
	
	public void tick(){
		
	
		// A CLASS
		
		for(int i = 0; i < ma.size(); i++){
			enta = ma.get(i);
			enta.tick();
		}
		
		// B CLASS
		for(int i = 0; i < mb.size(); i++){
			entb = mb.get(i);
			
			entb.tick();
		}
		
		//C CLASS
		for(int i = 0; i < mc.size(); i++){
			entc = mc.get(i);
			
			entc.tick();
		}
		
	}
			

	public void render(Graphics g){
	
	// A CLASS
		for(int i = 0; i < ma.size(); i++){
			enta = ma.get(i);
			
			enta.render(g);
		}
		
		//B CLASS
		for(int i = 0; i < mb.size(); i++){
			entb = mb.get(i);
			
			entb.render(g);
		}
		
		//C CLASS
		for(int i = 0; i < mc.size(); i++){
			entc = mc.get(i);
			
			entc.render(g);
		}
	}
		
		public void addEntity(EntityA block){
			ma.add(block);
		}
		
		public void removeEntity(EntityA block){
			ma.remove(block);
		}
		
		public void addEntity(EntityB block){
			mb.add(block);
		}
		
		public void removeEntity(EntityB block){
			mb.remove(block);
		}
		
		public void addEntity(EntityC block){
			mc.add(block);
		}
		
		public void removeEntity(EntityC block){
			mc.remove(block);
		}
		
		public LinkedList<EntityA> getEntityA(){
			return ma;
		}
		
		public LinkedList<EntityB> getEntityB(){
			return mb;
		}
		
		public LinkedList<EntityC> getEntityC(){
			return mc;
		}
		
	}
	


