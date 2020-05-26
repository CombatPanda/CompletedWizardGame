package MainPackage;

import java.io.Serializable;

public class BulletException extends Exception implements Serializable{
	
	private static final long serialVersionUID = 1L;
		int bulletcount;
	  public BulletException(String message, int bulletcount) {
	        super(message);
	        this.bulletcount=bulletcount;
	    }
}
