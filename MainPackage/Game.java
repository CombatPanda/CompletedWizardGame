package MainPackage;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.JOptionPane;

import SecondPackage.Window;

public  class Game  extends Canvas implements Serializable,  Runnable  { 
	
	private static final long serialVersionUID = 1L;
	
	private static Wizard wizardGameData;
	private static Enemy enemyGameData;
	
	private boolean isRunning = false;
	private Thread thread; // thread
	private Handler handler;
	private Camera camera;
	private SpriteSheet ss;
	
	static int score=0;
	private static String highScore = "";
	static int enemiesRemaining=-1;		
	static int wizardHealth=100;
	public int ammo = 50;
	
	private BufferedImage level = null;
	private BufferedImage sprite_sheet = null;
	private BufferedImage floor = null;
	

	
	static File gameSaveFile = new File("gamesave.bin");
	
	public Game() {	
		new Window(1000, 563, "Wizard Lizard", this);   
		start();	
		handler = new Handler();	
		camera = new Camera(0,0);
		this.addKeyListener(new KeyInput(handler));
	
		BufferedImageLoader loader = new BufferedImageLoader();
		sprite_sheet = loader.loadImage("/sprite_sheet.png");
		level = loader.loadImage("/wizard_level.png");
		

		
		ss = new SpriteSheet(sprite_sheet);
		floor = ss.grabImage(4, 2, 32, 32);
		
		this.addMouseListener(new MouseInput(handler,camera, this, ss));
		
		loadLevel(level);
		
		//GameSave(handler, gameSaveFile);
		//GetGameSave(handler, gameSaveFile);
		//loadSavedLevel(handler, gameSaveFile);
		
		
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
		 
		 for(int i=0; i < handler.object.size(); i++) {  // loopina pro visus objektus, suranda kuris yra zaidejas ir duoda parametrus camera kuriu mum reikia 
			 if(handler.object.get(i).getId() == ID.Player) {
				 camera.tick(handler.object.get(i));
			 }
		 }
		
			if(wizardHealth<=0) { // mirsta burtininkas
				CheckScore();
				System.exit(0);
				}
		 
		 handler.tick();
	 } 
	 
	 public void render() { // vaizduoti kas vyksta zaidime
		 BufferStrategy bs = this.getBufferStrategy();    
		 if(bs == null) {   // is anksto uzkrauna frame'us
			 this.createBufferStrategy(3);  // pamatant frame jau krauna kitus 2 ( jeigu daugiau, suprasteja veikimas
			 return;
		 }
		 
		 Graphics g = bs.getDrawGraphics();
		 Graphics2D g2d = (Graphics2D) g;
		 //////////////////////////////////////
		 // Cia bus dedami vaizdai i zaidima \\
		 
		 g2d.translate(-camera.getX(), -camera.getY());
		 
		 for(int xx = 0; xx < 30*72; xx+=32) {
			 for(int yy = 0; yy < 30*72;  yy+=32) {
				 g.drawImage(floor, xx, yy, this); // grindu tekstura
			 }
		 }
		 
		 handler.render(g);
		 
		 g2d.translate(camera.getX(), camera.getY());
		 
		 g.setColor(Color.gray); 				//gyvybiu vaizdavimas
		 g.fillRect(5, 5, 200, 32);
		 g.setColor(Color.green);
		 g.fillRect(5, 5, wizardHealth*2, 32);
		 g.drawRect(5, 5, 200, 32);
		 
		 g.setColor(Color.white);           // kulku vaizdavimas
		 g.drawString("Ammo: " + ammo, 5, 50);
		 
		 if(highScore.equals("")) {
			 // inicializuoti highscore
			 highScore = this.GetHighScore();
		 }
		 g.setColor(Color.white);
		 g.drawString("Score: " + score, 10, 500);  // taskai
		 g.drawString("Highscore: " + highScore, 10, 510);
		 g.drawString("Enemies Remaining: " + enemiesRemaining, 400, 25);
		 //////////////////////////////////////
		 g.dispose();
		 bs.show();
	 }
	 
	 //levelio uzkrovimas
	 private void loadLevel(BufferedImage image) {
		 int w = image.getWidth();
		 int h = image.getHeight();
		 
		 for(int xx = 0; xx < w; xx++) {
			 for(int yy = 0; yy < h; yy++) {
				 int pixel = image.getRGB(xx, yy);
				 int red = (pixel >> 16) & 0xff;
				 int green = (pixel >> 8) & 0xff;
				 int blue = (pixel) & 0xff;
				 
				 if(red == 255) {
					 handler.addObject(new Block(xx*32, yy*32, ID.Block, ss));
				 }
				 if(blue == 255 && green == 0) {
					 handler.addObject(new Wizard(xx*32, yy*32, ID.Player, handler, this, ss));
					}
				 if(green == 255 && blue == 0) {
					 handler.addObject(new Enemy(xx*32, yy*32, ID.Enemy, handler, ss));
					 enemiesRemaining++;
				 }
				 if(green == 255 && blue == 255) {
					 handler.addObject(new Crate(xx*32, yy*32, ID.Crate, ss));
				 }
			 
			 }
		 }
	 }
	 
	 public static void main(String Args[]) {
			new Game();
				
			
	 }

public String GetHighScore() {
	//formatas: vardas:100
	DataInputStream readFile = null;
	ObjectInputStream reader = null;
	try {
		 readFile = new DataInputStream(new FileInputStream("highscore.bin")) ;	
		 reader = new ObjectInputStream(readFile);
		 return reader.readUTF();
	} 
	catch (Exception e) {
		return "Nobody:0";
	} 
	finally {
		try {
			if (reader != null)
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
public static void CheckScore() {
	if(score > Integer.parseInt((highScore.split(":")[1]))) {
		// pasiekta daugiausia tasku
		String name = JOptionPane.showInputDialog("Pasiekete nauja rekorda! Koks jusu vardas?");
		highScore = name + ":" + score;
		
		File scoreFile = new File("highscore.bin");
		if(!scoreFile.exists()) {
			try {
				scoreFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		DataOutputStream writeFile = null;
		ObjectOutputStream writer = null;
		try {
			writeFile = new DataOutputStream(new FileOutputStream(scoreFile));
			writer = new ObjectOutputStream(writeFile);
			writer.writeUTF(highScore);
		}
		catch (Exception e) {
			
		}
		finally {
			try {
				if(writer != null) {
					writer.close();
				}
			}
			catch(Exception e) {
				
			}
		}
		
	}
}
public static void GameSave(Handler handler, File gameSaveFile) { // WORKING

	    System.out.println("Iskviestas GameSave metodas: ZAIDIMAS ISSAUGOTAS");
		if(!gameSaveFile.exists()) {
			try {
				gameSaveFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		DataOutputStream writeFile = null;
		ObjectOutputStream writer = null;
		try {
			writeFile = new DataOutputStream(new FileOutputStream(gameSaveFile));          
			writer = new ObjectOutputStream(writeFile);
		
			for(int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);
				
				if(tempObject.getId() == ID.Player) {
					// iraso i faila burtininka
					writer.writeObject(tempObject);         
				}
				if(tempObject.getId() == ID.Enemy) {
					// iraso i faila priesa
					writer.writeObject(tempObject);        
				}
			}
		}
		catch (Exception e) {
			//error
			System.out.println("ERROR" + e);
		}
		finally {
			try {
				if(writer != null) {
					writer.close();
				}
			}
			catch(Exception e) {
				// erroras
				System.out.println("ERROR" + e);
			}
		}
	}
public static void GetGameSave(Handler handler, File gameSaveFile) { // WORKING
			
			System.out.println("Iskviestas GetGameSave metodas: GAUTI DUOMENYS IS FAILO");
			DataInputStream readGameSaveFile = null;
			ObjectInputStream gameSaveReader = null;
			
			try {
				readGameSaveFile = new DataInputStream(new FileInputStream(gameSaveFile)) ;	 
				 gameSaveReader = new ObjectInputStream(readGameSaveFile);
				 		
				 	for(int i = 0; i < handler.object.size(); i++) {
						GameObject tempObject = handler.object.get(i);
						
						if(tempObject.getId() == ID.Player) {
						// gauna is failo duomenis apie burtininka
							wizardGameData = (Wizard) gameSaveReader.readObject();
						}
						if(tempObject.getId() == ID.Enemy) {
						//gauna is failo duomenis apie priesus
							enemyGameData = (Enemy) gameSaveReader.readObject();
							}
						}
					

			} 
			catch (Exception e) { 
				//error
			} 
			finally {
				try {
					if (gameSaveReader != null)
						gameSaveReader.close();              // uzdaro faila
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}
static void loadSavedLevel(Handler handler,File  gameSaveFile) {
			System.out.println("Uzkrautas issaugotas lygis");
		for(int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);
				
				if(tempObject.getId() == ID.Player) {
					handler.removeObject(tempObject);
					handler.addObject(wizardGameData);
					}
				if(tempObject.getId() == ID.Enemy) {
					handler.removeObject(tempObject);
					handler.addObject(enemyGameData);
					}
					}
				}
}