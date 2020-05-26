package MainPackage;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

public class MouseInput extends MouseAdapter implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Handler handler;
	private Camera camera;
	private Game game;
	private SpriteSheet ss;
	static int bulletcount;
	
	public MouseInput(Handler handler, Camera camera, Game game, SpriteSheet ss) {
		this.handler = handler;
		this.camera = camera;
		this.game = game;
		this.ss = ss;
	}
	
	public void mousePressed(MouseEvent e) {
		int mx = (int) (e.getX() + camera.getX());
		int my = (int) (e.getY() + camera.getY());
		
		for(int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if(tempObject.getId() == ID.Player && game.ammo >= 1) {
				
				try {
					createBullet(tempObject,mx,my);
					game.ammo--;
				} catch (BulletCountException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
	}
	public void createBullet(GameObject tempObject,int mx,int my) throws BulletCountException {
		BulletFactory create = new CreateObject();
	    Bullet normalBullet = create.createBullet( 1, tempObject.getX()+16,  tempObject.getY()+24,  ID.Bullet,  handler,  mx,  my);
	   // Bullet bigBullet = create.createBullet( 2, tempObject.getX()+16,  tempObject.getY()+24,  ID.Bullet,  handler,  mx,  my);
		handler.addObject(normalBullet);
		
	   
		bulletcount++;
		if(bulletcount>=40) {
			throw new BulletCountException("Too many bullets on the screen",bulletcount);
		}
	}
}
