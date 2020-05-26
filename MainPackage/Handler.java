package MainPackage;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.LinkedList;

public class Handler implements Cloneable, Serializable{


	private static final long serialVersionUID = 1L;

	LinkedList<GameObject> object = new LinkedList<GameObject>(); //objektu masyvas
	
	private boolean up = false, down = false, right = false, left = false;

	public void tick() {
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.tick();
		}
			}
	
	public void render(Graphics g) {
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.render(g);
		}
	}
	
	public void addObject(GameObject tempObject) {
		object.add(tempObject);
		}
	public void addObject(Movable tempObject) {
		object.add((GameObject) tempObject);
	}
	
	public void removeObject(GameObject tempObject) {
		object.remove(tempObject);
		}
	
	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}
	public  Object clone() throws CloneNotSupportedException {
		
		LinkedList<GameObject> cloned = (LinkedList<GameObject>) super.clone();
		//klonuoti object
		cloned = (LinkedList<GameObject>) object.clone();
      	
		return cloned;
	}
	
}
