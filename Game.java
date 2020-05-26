import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public  class Game  extends Canvas implements Runnable{  // klase
	
	private static final long serialVersionUID = 1L;
	
	private boolean isRunning = false;
	private Thread thread;
	private Handler handler;
					
	public Game() {		// 	konstruktorius beargumentis
		new Window(1000, 563, "Wizard Lizard", this);   
		start();	
		handler = new Handler();	
		this.addKeyListener(new KeyInput(handler));
		
		handler.addObject(new Wizard(100,100, ID.Player, handler));
	//	handler.addObject(new Box(100,100, ID.Block));    
	//	handler.addObject(new Box(200,100, ID.Block)); 

	}
											 
																					
	
	private void start() {   // prasideda zaidimas
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private void stop() {  // baigiasi zaidimas
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
		public void run() { // game loopas
			  this.requestFocus();
			  long lastTime = System.nanoTime();
			  double amountOfTicks = 60.0;
			  double ns = 1000000000 / amountOfTicks;
			  double delta = 0;
			  long timer = System.currentTimeMillis();
			  int frames = 0;
			  while (isRunning) {
			   long now = System.nanoTime();
			   delta += (now - lastTime) /ns;
			   lastTime = now;
			   while(delta >= 1) {
			    tick();
			    delta--;
			   }
			   render();
			   frames++;
			   
			   if (System.currentTimeMillis() - timer > 1000) {
			    timer += 1000;
			    frames = 0;
			   }
			  }
			  stop();
	 }
		
	 public void tick() { // atnaujinti viska zaidime 
		 handler.tick();
	 } 
	 
	 public void render() { // vaizduoti kas vyksta zaidime
		 BufferStrategy bs = this.getBufferStrategy();    
		 if(bs == null) {   // is anksto uzkrauna frame'us
			 this.createBufferStrategy(3);  // pamatant frame jau krauna kitus 2 ( jeigu daugiau, suprasteja veikimas
			 return;
		 }
		 
		 Graphics g = bs.getDrawGraphics();
		 //////////////////////////////////////
		 // Cia bus dedami vaizdai i zaidima \\
		 
		 g.setColor(Color.black);         
		 g.fillRect(0, 0, 1000, 563);
		 
		 handler.render(g);
		 
		 //////////////////////////////////////
		 g.dispose();
		 bs.show();
	 }
	 
	 public static void main(String Args[]) {
		 	new Game();
		 	Rectangle staciakampis = new Rectangle(100,100,200,100);
			Rectangle kvadratas = new Rectangle(100,100,100,100);
			staciakampis.println();
			kvadratas.println();
			kvadratas.grow(500,500);
			kvadratas.println();
			Rectangle pradinis = new Rectangle(100,100);
			pradinis.println();
			
	 }


	
}
