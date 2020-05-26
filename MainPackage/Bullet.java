package MainPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

public class Bullet extends GameObject implements Cloneable, Serializable{
	
	private static final long serialVersionUID = 1L;

	Handler handler;

	float acceleration=(float) 0.1;
	

	
	public Bullet(int x, int y, ID id, Handler handler, int mx, int my, SpriteSheet ss) {
		super(x, y, id, ss);
		this.handler = handler;
	
		
		velX = (mx - x) / 10;
		velY = (my - y) / 10;
	}

	public void tick() {
		move();
		accelerate(acceleration);
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			
			if(tempObject.getId() == ID.Block) {
				if(getBounds().intersects(tempObject.getBounds())) {
					MouseInput.bulletcount--;
					handler.removeObject(this);
				}
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillOval(x, y, 8, 8);
	}

	public Rectangle getBounds() {
		return new Rectangle(x,y,8,8);
	}
	public void move() {
		if(velX>=0)
			x += velX + this.acceleration;
		if(velX<=0)
			x += velX + this.acceleration*-1;
			y += velY;
	}
	public void accelerate(float acceleration2) {
		this.acceleration += 0.1 ;
		}
	
    public Object clone() throws CloneNotSupportedException { // clone metodas 
   
    		Bullet cloned = (Bullet) super.clone();
    		//klonuoti handleri
          	cloned.handler = (Handler) handler.clone();
          	
            return cloned;
           
        }             						    	  								
}
    

