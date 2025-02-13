package MainPackage;

import java.io.Serializable;

public class Camera implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private float x,y;
	
	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick(GameObject object) {
		
		x += ((object.getX()-x) - 1000/2) * 0.05f;
		y += ((object.getY()-y) - 563/2) * 0.05f;

		if(x <= 0) x=0;
		if(x >= 1048) x = 1048;
		if(y <= 0) y=0;
		if(y >= 563+48) y = 563+48;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}
