package com.bleak.graphics.test;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.bleak.graphics.framework.KeyInput;
import com.bleak.graphics.framework.ObjectId;
import com.bleak.graphics.framework.Texture;
import com.bleak.graphics.objects.Block;
import com.bleak.graphics.objects.Enemy;
//import com.bleak.graphics.objects.Coin;
import com.bleak.graphics.objects.Player;


public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = 1L;

	private boolean running = false;
	private Thread thread;
	
	private int rateFPS = 0;
	private int rateTicks = 0;
	
	public static int WIDTH, HEIGHT;
	public int SCORE = 0;
	
	private BufferedImage level = null;
	
	
	Handler handler;
	Camera cam;
	static Texture tex;
	
	private int fontSize = 32;
	private Font mainFont = new Font("TimesRoman", Font.PLAIN, fontSize);
	
	
	Random rand = new Random();
	
	private void init()
	{
		WIDTH = getWidth();
		HEIGHT = getHeight();
		
		tex = new Texture();
		
		handler = new Handler();
		
		SFX.initSFX();
		
		cam = new Camera(0,0);
		
		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("/level.png");// level loading		
		LoadImageLevel(level);
		
		System.out.println("Blocks: " + handler.countObject(ObjectId.Block));
		System.out.println("Coins: " + handler.countObject(ObjectId.Coin));
		System.out.println("Players: " + handler.countObject(ObjectId.Player));
		
		//handler.addObject(new Player(100, 100, handler, ObjectId.Player));
		//handler.CreateLevel();
		
		this.addKeyListener(new KeyInput(handler));;
	}
	
	public synchronized void start(){
		if(running)
			return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void run(){
		System.out.println("Thread has begun");
		
		init();
		this.requestFocus();
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 128.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;
					
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				rateFPS = frames;
				rateTicks = updates;
				System.out.println("FPS: " + rateFPS + " TICKS: " + rateTicks);
				frames = 0;
				updates = 0;
			}
		}
	}
	
	private void tick()
	{
		handler.tick();
		for(int i = 0; i < handler.object.size(); i++){
			if(handler.object.get(i).getId() == ObjectId.Player){
				cam.tick(handler.object.get(i));
			}
		}
	}
	
	private void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
	    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		;/////////////////////////////////
		;//      D R A W   H E R E      //
		;/////////////////////////////////
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.drawImage(tex.sky[0], 0, 0, WIDTH, HEIGHT, null);
		
		g2d.translate(cam.getX(), cam.getY());
		
		handler.render(g);
		
		g2d.translate(-cam.getX(), -cam.getY());
		
		g.setFont(mainFont);
		
		/*g.setColor(Color.DARK_GRAY);
		g.drawString("FPS: " + rateFPS + " TICKS: " + rateTicks, 36, 68);
		g.setColor(Color.white);
		g.drawString("FPS: " + rateFPS + " TICKS: " + rateTicks, 32, 64);*/
		
		/*g.setFont(mainFont);
		g.setColor(Color.blue);
		g.drawString("Explosions: "+handler.countObject(ObjectId.Explosion), 50, 50);*/
		
		g.dispose();
		bs.show();
	}
	
	private void LoadImageLevel(BufferedImage image){
		int w = image.getWidth();
		int h = image.getHeight();
		
		System.out.println("width, height: " + w + " " + h);
		
		for(int xx = 0; xx < h; xx++){
			for(int yy = 0; yy < w; yy++){
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if(red == 255 && green == 255 && blue == 255)
					handler.addObject(new Block(xx*32, yy*32, 0, ObjectId.Block));// concrete
				
				if(red == 255 && green == 0 && blue == 0)
					handler.addObject(new Block(xx*32, yy*32, 1, ObjectId.Block));// bricks

				if(red == 0 && green == 255 && blue == 0)
					handler.addObject(new Block(xx*32, yy*32, 2, ObjectId.Block));// grass
				
				if(red == 0 && green == 255 && blue == 255)
					handler.addObject(new Block(xx*32, yy*32, 3, ObjectId.Block));// water
				
				if(red == 0 && green == 0 && blue == 255)
					handler.addObject(new Player(xx*32, yy*32, handler, ObjectId.Player));// player
				
				if(red == 255 && green == 0 && blue == 255)
					handler.addObject(new Enemy(xx*32, yy*32, handler, ObjectId.Enemy));// enemy
				//if(red == 255 && green == 255 && blue == 0)
				//	handler.addObject(new Coin(xx*32, yy*32, ObjectId.Coin));
			}
		}
	}
	
	public static Texture getInstance(){
		return tex;
	}
	
	
	public static void main(String args[])
	{
		new Window(800,600,"Battle City", new Game());
	}
	
}
