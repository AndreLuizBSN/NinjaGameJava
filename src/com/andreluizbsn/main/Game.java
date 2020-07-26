package com.andreluizbsn.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

import com.andreluizbsn.entities.Entity;
import com.andreluizbsn.entities.LadderPos;
import com.andreluizbsn.entities.Player;
import com.andreluizbsn.graficos.Spritesheet;
import com.andreluizbsn.graficos.UI;
import com.andreluizbsn.world.World;

public class Game extends Canvas implements Runnable,KeyListener,MouseListener,MouseMotionListener{

	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final int SCALE = 1;
	
	public static int basex = 64, basey = 64;
	
	private BufferedImage image;
	

	public static World world;
	public static List<Entity> entities;
	public static List<LadderPos> laddersPos;
	public static Spritesheet spritesheet;
	public static Player player;
	
	public static boolean isSound = true;
	
	public static String state = "NORMAL";
	
	public static Menu menu;
	
	public UI ui;
	
	public Game(){
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		
		//Inicializando objetos.
		/*spritesheet = new Spritesheet("/spritesheet.png");
		
		entities = new ArrayList<Entity>();
		player = new Player(WIDTH/2 - 30,HEIGHT/2,Game.basex,Game.basey,1.4,Player.PLAYER_SPRITE_R);
		world = new World("/level1.png");*/
		ui = new UI();
		//background = new Background();
		
		Game.newGame("level1.png", 100, false, 0);
		
		//entities.add(player);
		
	}
	
	public static void setCurLevel ( int cUR_LEVEL ) {
	}
	
	public static void newGame( String level, double life, boolean hasGun, int ammo ) {
		
		Game.laddersPos = new ArrayList<LadderPos>();
		
		if ( Game.entities != null )
			Game.entities.clear();
		Game.entities = new ArrayList<>();
		
		spritesheet = new Spritesheet("/spritesheet.png");
		
		world = new World("/" + level);
		
		player.life = life;
		
		Game.entities.add(Game.player);
		
		Game.menu = new Menu();
	}
	
	public void initFrame(){
		frame = new JFrame("Ninja");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start(){
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop(){
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]){
		Game game = new Game();
		game.start();
	}
	
	public void tick(){
		
		if ( isSound ) {
			Sound.backgroundSound.loop();
		}
		
		if ( Game.state.equals("NORMAL") ) {
	
			for(int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
			
		} else {
			
			if ( isSound ) {
				Sound.backgroundSound.stop();
			}
			
			menu.tick();
			
		}
		
	}
	


	
	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		
				
		g.setColor(new Color(122,102,255));
		g.fillRect(0, 0,WIDTH,HEIGHT);
		//background.render(g);
		
		/*Renderização do jogo*/
		//Graphics2D g2 = (Graphics2D) g;
		world.render(g);
		Collections.sort(entities,Entity.nodeSorter);
		for(int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		
		/***/
		g.dispose();
		g = bs.getDrawGraphics();

		g.drawImage(image, 0, 0,WIDTH*SCALE,HEIGHT*SCALE,null);
		ui.render(g);
		
		if ( Game.state.equals("GAME_OVER") ){
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,100));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g.setFont(new Font("Arial", Font.BOLD, 28));
			g.setColor(Color.white);
			g.drawString("Game Over", 160, 120);
			g.setFont(new Font("Arial", Font.BOLD, 15));
			g.setColor(Color.white);
			g.drawString("Press N to restart game", 150, 140);
			if ( isSound ) {
				Sound.backgroundSound.stop();
			}
		} else if ( Game.state.equals("MENU") ) {
			menu.render(g);
		}
		
		bs.show();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning){
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer >= 1000){
				System.out.println("FPS: "+ frames);
				frames = 0;
				timer+=1000;
			}
			
		}
		
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if ( e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D && Game.state.equals("NORMAL") ) {
			player.right = true;
		}
		
		if ( e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W && Game.state.equals("NORMAL")  ) {
			player.jump = true;
		}
		
		if ( e.getKeyCode() == KeyEvent.VK_ESCAPE && Game.state.equals("NORMAL") ) {
			Menu.options[0] = "Continue";
			Game.state = "MENU";
		}
				
		if ( ( e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W ) && Game.state.equals("MENU") ) {
			menu.up = true;
		} else if ( ( e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S ) && Game.state.equals("MENU") ) {
			menu.down = true;
		}
		
		if ( e.getKeyCode() == KeyEvent.VK_ENTER && Game.state.equals("MENU") ) {
			menu.enter = true;
		}
		
		if ( e.getKeyCode() == KeyEvent.VK_N && Game.state.equals("GAME_OVER") ) {
			Game.state = "NORMAL";
			newGame("level1.png", 100, false, 0);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if ( e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D && Game.state.equals("NORMAL") ) {
			player.right = false;
		}		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	
	}

	
}
