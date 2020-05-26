package MainPackage;

import java.io.Serializable;

public class BulletFields implements Cloneable , Serializable{
	private static final long serialVersionUID = 1L;
	Handler handler;
	
	    public BulletFields(Handler handler) {
	    	this.handler=handler;
	}
		public Object clone() throws CloneNotSupportedException {
	        return super.clone();
	    }
}
