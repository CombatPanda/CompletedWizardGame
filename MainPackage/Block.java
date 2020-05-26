package MainPackage;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Block extends GameObject implements Serializable{

	private static final long serialVersionUID = 1L;
	private BufferedImage block_image;

	public Block(int x, int y, ID id, SpriteSheet ss) {
		super(x, y, id, ss);
		block_image = ss.grabImage(5, 2, 32, 32);
	}

	
	public void tick() {
		
	}

	
	public void render(Graphics g) {
		g.drawImage(block_image,x,y,null);
	}

	
	public Rectangle getBounds() {
		return new Rectangle(x,y,32,32);
	}


	@Override
	public void accelerate(float acceleration) {	
	}


	@Override
	public void move() {	
	}

}
