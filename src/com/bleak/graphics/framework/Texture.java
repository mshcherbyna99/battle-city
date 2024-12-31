package com.bleak.graphics.framework;

import java.awt.image.BufferedImage;

import com.bleak.graphics.objects.BlockType;
import com.bleak.graphics.objects.Enemy;
import com.bleak.graphics.objects.Player;
import com.bleak.graphics.test.BufferedImageLoader;
import com.bleak.graphics.objects.Block;

public class Texture {
    SpriteSheet ps; // player_sheet

    public BufferedImage[] block = new BufferedImage[5];
    public BufferedImage[] water = new BufferedImage[3];
    public BufferedImage[] player = new BufferedImage[9];
    public BufferedImage[] enemy = new BufferedImage[9];
    public BufferedImage[] bullet = new BufferedImage[4];
    public BufferedImage[] explosion = new BufferedImage[3];
    public BufferedImage[] kaboom = new BufferedImage[3];
    public BufferedImage[] eagle = new BufferedImage[2];
    public BufferedImage[] sky = new BufferedImage[1];
    public BufferedImage[] coin = new BufferedImage[10];

    public Texture() {
        BufferedImageLoader loader = new BufferedImageLoader();

        BufferedImage player_sheet = loader.loadImage("/gfx/battle_city_sprite_sheet.png");

        ps = new SpriteSheet(player_sheet);

        getTextures();
    }

    private void getTextures() {
        block[BlockType.Concrete.getId()] = ps.grabImage(33, 3, Block.spriteWidth, Block.spriteHeight); // concrete block
        block[BlockType.Brick.getId()] = ps.grabImage(33, 1, Block.spriteWidth, Block.spriteHeight); // brick block
        block[BlockType.Grass.getId()] = ps.grabImage(35, 5, Block.spriteWidth, Block.spriteHeight); // grass block
        block[BlockType.Water.getId()] = ps.grabImage(33, 5, Block.spriteWidth, Block.spriteHeight); // water block

        eagle[0] = ps.grabImage(39, 5, 16, 16); // eagle
        eagle[1] = ps.grabImage(41, 5, 16, 16); // eagle destroyed

        water[0] = ps.grabImage(33, 5, Block.spriteWidth, Block.spriteHeight); // water animation
        water[1] = ps.grabImage(33, 7, Block.spriteWidth, Block.spriteHeight);
        water[2] = ps.grabImage(35, 7, Block.spriteWidth, Block.spriteHeight);

        player[0] = ps.grabImage(1, 1, Player.spriteWidth, Block.spriteHeight); // player idle frame
        player[1] = ps.grabImage(1, 1, Player.spriteWidth, Block.spriteHeight); // player movement animation (up)
        player[2] = ps.grabImage(3, 1, Player.spriteWidth, Block.spriteHeight);
        player[3] = ps.grabImage(5, 1, Player.spriteWidth, Block.spriteHeight); // player movement animation (left)
        player[4] = ps.grabImage(7, 1, Player.spriteWidth, Block.spriteHeight);
        player[5] = ps.grabImage(9, 1, Player.spriteWidth, Block.spriteHeight); // player movement animation (down)
        player[6] = ps.grabImage(11, 1, Player.spriteWidth, Block.spriteHeight);
        player[7] = ps.grabImage(13, 1, Player.spriteWidth, Block.spriteHeight); // player movement animation (right)
        player[8] = ps.grabImage(15, 1, Player.spriteWidth, Block.spriteHeight);

        enemy[1] = ps.grabImage(17, 1, Enemy.spriteWidth, Enemy.spriteHeight); // enemy movement animation (up)
        enemy[2] = ps.grabImage(19, 1, Enemy.spriteWidth, Enemy.spriteHeight);
        enemy[3] = ps.grabImage(21, 1, Enemy.spriteWidth, Enemy.spriteHeight); // enemy movement animation (left)
        enemy[4] = ps.grabImage(23, 1, Enemy.spriteWidth, Enemy.spriteHeight);
        enemy[5] = ps.grabImage(25, 1, Enemy.spriteWidth, Enemy.spriteHeight); // enemy movement animation (down)
        enemy[6] = ps.grabImage(27, 1, Enemy.spriteWidth, Enemy.spriteHeight);
        enemy[7] = ps.grabImage(29, 1, Enemy.spriteWidth, Enemy.spriteHeight); // enemy movement animation (right)
        enemy[8] = ps.grabImage(31, 1, Enemy.spriteWidth, Enemy.spriteHeight);

        bullet[0] = ps.grabImage(41, 14, 8, 8);//
        bullet[1] = ps.grabImage(42, 14, 8, 8);// bullet different directions
        bullet[2] = ps.grabImage(43, 14, 8, 8);//        sprites
        bullet[3] = ps.grabImage(44, 14, 8, 8);//

        explosion[0] = ps.grabImage(33, 17, 16, 16);//
        explosion[1] = ps.grabImage(35, 17, 16, 16);// small explosion
        explosion[2] = ps.grabImage(37, 17, 16, 16);//

        kaboom[0] = ps.grabImage(9, 8, 16, 16);
        kaboom[1] = ps.grabImage(10, 8, 16, 16);
    }
}
