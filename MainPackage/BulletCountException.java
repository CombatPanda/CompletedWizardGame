package MainPackage;

import java.io.Serializable;

public class BulletCountException extends BulletException implements Serializable {

	private static final long serialVersionUID = 1L;

	public BulletCountException(String message, int bulletcount) {
        super(message, bulletcount);
    }
}
