package com.bleak.graphics.test;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.Serial;
import java.util.Random;

import com.bleak.graphics.framework.KeyInput;
import com.bleak.graphics.framework.ObjectId;
import com.bleak.graphics.framework.Texture;
import com.bleak.graphics.objects.*;
//import com.bleak.graphics.objects.Coin;


public class Game extends Canvas implements Runnable {
    public static int MAP_ROWS, MAP_COLS, TILE_SIZE;
    @Serial
    private static final long serialVersionUID = 1L;
    private boolean running = false;
    public static int WIDTH, HEIGHT;
    public int SCORE = 0;
    private int rateFPS;
    private int rateTicks;
    Handler handler;
    Camera cam;
    static Texture tex;
    private final int fontSize = 32;
    private final Font mainFont = new Font("TimesRoman", Font.PLAIN, fontSize);
    Random rand = new Random();

    private void init() {
        WIDTH = getWidth();
        HEIGHT = getHeight();
        tex = new Texture();
        handler = new Handler();
        SFX.initSFX();
        cam = new Camera(0, 0);
        BufferedImageLoader loader = new BufferedImageLoader();

        BufferedImage level = loader.loadImage("/gfx/level.png"); // level loading

        TILE_SIZE = 64;
        MAP_ROWS = level.getHeight();
        MAP_COLS = level.getWidth();

        LoadImageLevel(level);

        this.addKeyListener(new KeyInput(handler));
    }

    public synchronized void start() {
        if (running) {
            return;
        }

        running = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        init();
        this.requestFocus();

        long lastTime = System.nanoTime();
        double amountOfTicks = 128.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while (delta >= 1) {
                tick();
                updates++;
                delta--;
            }

            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                rateFPS = frames;
                rateTicks = updates;
                System.out.println("FPS: " + rateFPS + " TICKS: " + rateTicks);
                frames = 0;
                updates = 0;
            }
        }
    }

    private void tick() {
        handler.tick();

        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getId() == ObjectId.Player) {
                cam.tick(handler.object.get(i));
            }
        }
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(tex.sky[0], 0, 0, WIDTH, HEIGHT, null);

        g2d.translate(cam.getX(), cam.getY());
        handler.render(g);
        g2d.translate(-cam.getX(), -cam.getY());

        g.setFont(mainFont);
        g.setColor(Color.DARK_GRAY);
        g.drawString("FPS: " + rateFPS + " TICKS: " + rateTicks, 36, 68);
        g.setColor(Color.white);
        g.drawString("FPS: " + rateFPS + " TICKS: " + rateTicks, 32, 64);
        g.setFont(mainFont);
        g.setColor(Color.blue);
        g.drawString("Explosions: "+handler.countObject(ObjectId.Explosion), 50, 50);
        g.dispose();

        bs.show();
    }

    private void LoadImageLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        System.out.println("width, height: " + w + " " + h);

        for (int xx = 0; xx < h; xx++) {
            for (int yy = 0; yy < w; yy++) {
                int pixel = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;
                int blocksHeight = Block.height;
                int blocksWidth = Block.width;
                int playerHeight = Player.height;
                int playerWidth = Player.width;
                int enemyHeight = Enemy.height;
                int enemyWidth = Enemy.width;
                int coinHeight = Coin.height;
                int coinWidth = Coin.width;
                int eagleHeight = Eagle.height;
                int eagleWidth = Eagle.width;

                if (red == 255 && green == 255 && blue == 255) {
                    handler.addObject(
                        new Block(
                            xx * blocksHeight,
                            yy * blocksWidth,
                            BlockType.Concrete.getId(),
                            ObjectId.Block
                        )
                    );
                }

                if (red == 255 && green == 0 && blue == 0) {
                    handler.addObject(
                        new Block(
                            xx * blocksHeight,
                            yy * blocksWidth,
                            BlockType.Brick.getId(),
                            ObjectId.Block
                        )
                    );
                }

                if (red == 0 && green == 255 && blue == 0) {
                    handler.addObject(
                        new Block(
                            xx * blocksHeight,
                            yy * blocksWidth,
                            BlockType.Grass.getId(),
                            ObjectId.Block
                        )
                    );
                }

                if (red == 0 && green == 255 && blue == 255) {
                    handler.addObject(
                        new Block(
                            xx * blocksHeight,
                            yy * blocksWidth,
                            BlockType.Water.getId(),
                            ObjectId.Block
                        )
                    );
                }

                if (red == 0 && green == 0 && blue == 255) {
                    handler.addObject(
                        new Player(
                            xx * playerHeight,
                            yy * playerWidth,
                            handler,
                            ObjectId.Player
                        )
                    );
                }

                if (red == 255 && green == 0 && blue == 255) {
                    handler.addObject(
                        new Enemy(
                            xx * enemyHeight,
                            yy * enemyWidth,
                            handler,
                            ObjectId.Enemy
                        )
                    );
                }

                if (red == 255 && green == 255 && blue == 0) {
                    handler.addObject(
                        new Coin(
                            xx*32,
                            yy*32,
                            ObjectId.Coin
                        )
                    );
                }
            }
        }
    }

    public static Texture getInstance() {
        return tex;
    }


    public static void main(String[] args) {
        new Window(800, 600, "Battle City", new Game());
    }
}
