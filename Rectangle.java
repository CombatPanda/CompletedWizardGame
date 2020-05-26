public class Rectangle {
    private int x;
    private int y;
    private int width;
    private int height;
    private static int count = 0;
    private final int LIMIT_HEIGHT = 1000;
    private final int LIMIT_WIDTH = 1000;


    public Rectangle(int initialX, int initialY, int initialWidth, int initialHeight) {
        this.setLocation(initialX, initialY);
        this.setWidth(initialWidth);
        this.setHeight(initialHeight);
        rectCounter();

    }
    
    public Rectangle(){ // konstruktorius
        x = 0;
        y = 0;
        width = 0;
        height = 0;
    }

    public Rectangle(int initialWidth, int initialHeight){ // pradines koordinates 0;0
        this(0, 0, initialWidth, initialHeight);    // this() konstrukcija
    }
    
    //gaunu x koordinates
    public int getX() {

        return this.x;
    }
    //gaunu y koordinates
    public int getY() {
    	
        return this.y;
    }
    //gaunu ploti
    public int getWidth() {
    	
        return this.width;
    }
    //gaunu auksti
    public int getHeight(){
    
        return this.height;
    }
    //gaunu plota
    public int area(){
        return this.width * this.height;
    }
    //gaunu perimetra
    public int perimeter(){
        return (2*this.width + 2*this.height);
    }

    //nustatau x ir y koordinates(butinai teigiamos)
    public void setLocation(int newX, int newY) {
        if (newX < 0 || newY < 0) {
            throw new IllegalArgumentException();
        }

        this.x = newX;
        this.y = newY;
    }

    //nustatau ploti (teigiamas)       
    public void setWidth(int newWidth) {
        if (newWidth <= 0) {
            throw new IllegalArgumentException();
        }

        this.width = newWidth;
    }
    //nustatau auksti (teigiamas)
    public void setHeight(int newHeight) {
        if (newHeight <= 0) {
            throw new IllegalArgumentException("negalima reiksme");
        }

        this.height = newHeight;
    }
    //keturkampio didinimas
    public void grow(int dWidth, int dHeight) {  //metodas
        this.setWidth(this.width + dWidth);
        this.setHeight(this.height + dHeight);
    }
    public void grow(int dWidth, int dHeight,int limitWidth,int limitHeight){ // perkrautas metodas
        if (this.width+dWidth<=limitWidth && limitWidth <= LIMIT_WIDTH) {
            this.setWidth(this.width + dWidth);
        }
        else {
            throw new IllegalArgumentException("per didele plocio reiksme");
        }
        if(this.height +dHeight <=limitHeight && limitHeight <= LIMIT_HEIGHT) {
            this.setHeight(this.height + dHeight);
        }

        else {
            throw new IllegalArgumentException("per didele aukscio reiksme");
        }
    }

    public String toString(){
        return this.width + " x " + this.height;
    }
    
    public static void rectCounter (){
        count++;
    }
    //spausdinimas
    public void println() {
        System.out.println("x koordinate " + getX());
        System.out.println("y koordinate " + getY());
        System.out.println("plotis " + getWidth());
        System.out.println("aukstis " + getHeight());
        System.out.println("plotas " + area());
        System.out.println("perimetras " +perimeter());
        System.out.println("matmenys " + toString());
        System.out.println("sukurta "+ count+" kvadratu");
        System.out.println(" ");
    }
}