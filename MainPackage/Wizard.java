package MainPackage;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Wizard extends GameObject implements Serializable { // klasiu hierarchija

	private static final long serialVersionUID = 1L;
	Handler handler;
	Game game;
	
	private BufferedImage[] wizard_image = new BufferedImage[3];
	
	Animation anim;									
	float acceleration=(float) 0.1;
		
	public Wizard(int x, int y, ID id, Handler handler, Game game, SpriteSheet ss) {
		super(x, y, id, ss); 
		this.handler = handler;	
		this.game = game;
		
		wizard_image[0] = ss.grabImage(1, 1, 32, 48);
		wizard_image[1] = ss.grabImage(2, 1, 32, 48);
		wizard_image[2] = ss.grabImage(3, 1, 32, 48);
		
		anim = new Animation(10, wizard_image);
	}

	public void tick() {
		x += velX;
		y += velY; 
		accelerate(acceleration);
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Enemy) {
				if(getBounds().intersects(tempObject.getBounds())) {
				Game.wizardHealth-=20;	
				Game.enemiesRemaining--;
				handler.removeObject(tempObject);
				}
			}
		}
		
		
		collision();
		move();
		
		anim.runAnimation();
	}
	public void move() {
				//zmogeliuko judejimas
				if(handler.isUp()) velY=-5;
				else if(!handler.isDown()) velY=0;
				
				if(handler.isDown()) velY=5;
				else if(!handler.isUp()) velY=0;
				
				if(handler.isRight()) velX=5;
				else if(!handler.isLeft()) velX=0;
				
				if(handler.isLeft()) velX=-5;
				else if(!handler.isRight()) velX=0;
	}
	
	private void collision() {
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Block) {
				if(getBounds().intersects(tempObject.getBounds())) {
					x += velX * -1;
					y += velY * -1;
				}
			}
			if(tempObject.getId() == ID.Crate) {
				if(getBounds().intersects(tempObject.getBounds())) {
					game.ammo += 20;
					handler.removeObject(tempObject);
				}
		}
	}
	}
	@Override                             
	public void render(Graphics g) {
		if(velX == 0 && velY == 0)
			g.drawImage(wizard_image[0], x, y, null);
		else
			anim.drawAnimation(g, x	, y, 0);
	}
	@Override
	public Rectangle getBounds() {
		return new Rectangle(x,y,32,48); // 32 48
	}
	
	@Override
	public void accelerate(float acceleration) {
		this.acceleration += 0.001 ;
		}
	

}
