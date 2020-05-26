package MainPackage;

public interface BulletFactory {
	 Bullet createBullet(int type,int x, int y, ID id, Handler handler, int mx, int my);
}
