package com.bleak.graphics.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.bleak.graphics.framework.GameObject;
import com.bleak.graphics.framework.ObjectId;
import com.bleak.graphics.framework.Texture;
import com.bleak.graphics.test.Animation;
import com.bleak.graphics.test.Game;

public class Block extends GameObject
{
	Texture tex = Game.getInstance();
	private Animation waterAnimation = new Animation(30,tex.water[0],tex.water[1],tex.water[2]);
	//private int type;
	
	public Block(float x, float y, int type, ObjectId id) {
		super(x, y, id);
		this.type = type;
	}

	public void tick(LinkedList<GameObject> object) {
		if(type == 3)
			waterAnimation.runAnimation();
	}

	public void render(Graphics g) {
		//0 - concrete
		//1 - bricks
		//2 - grass
		//3 - water
		
		if(this.type != 3){
			g.drawImage(tex.block[this.type], (int) x, (int) y, 32, 32, null);
		} else {
			waterAnimation.drawAnimation(g, (int) x, (int) y, 32, 32);
		}
		
		
		/*if(this.type == 0) //dirt block
			g.drawImage(tex.block[0], (int) x, (int) y, 32, 32, null);
		if(this.type == 1) //grass block
			g.drawImage(tex.block[1], (int) x, (int) y, 32, 32, null);*/
		
		/*g.setColor(Color.green);
		g.drawRect((int) x, (int) y, 32, 32); */
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}
}
