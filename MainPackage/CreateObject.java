package MainPackage;

public class CreateObject implements BulletFactory {
	 public Bullet createBullet(int type,int x, int y, ID id, Handler handler, int mx, int my) {
	        switch (type) {
	            case 1:
	                return new Bullet(x,  y,  id,  handler,  mx,  my, null ); 
	            case 2:
	                return new BigBullet(x,  y,  id,  handler,  mx,  my, null );
	            default:
	            	return new Bullet(x,  y,  id,  handler,  mx,  my, null);
	        }
	    }

}