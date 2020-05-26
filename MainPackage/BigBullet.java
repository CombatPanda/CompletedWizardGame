package MainPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

public class BigBullet extends Bullet implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Handler handler;
	float acceleration=(float) 0.01;
	
	public BigBullet(int x, int y, ID id, Handler handler, int mx, int my, SpriteSheet ss) {
		super( x,  y,  id,  handler,  mx,  my, ss);
		this.handler = handler;
	
		
		velX = (mx - x) / 100;
		velY = (my - y) / 100;
	}

	public void tick() {
		move();
		accelerate(acceleration);
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Block) {
				if(getBounds().intersects(tempObject.getBounds())) {
					handler.removeObject(this);
				}
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillOval(x, y, 32, 32);
	}

	public Rectangle getBounds() {
		return new Rectangle(x,y,32,32);
	}
	public void move() {
		if(velX>=0)
			x += velX + this.acceleration;
		if(velX<=0)
			x += velX + this.acceleration*-1;
			y += velY;
	}
	public void accelerate(float acceleration2) {
		this.acceleration += 0.01 ;
		}
	
}
