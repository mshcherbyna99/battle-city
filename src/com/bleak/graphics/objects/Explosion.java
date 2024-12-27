package com.bleak.graphics.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.bleak.graphics.framework.ObjectId;
import com.bleak.graphics.framework.GameObject;
import com.bleak.graphics.framework.Texture;
import com.bleak.graphics.test.Animation;
import com.bleak.graphics.test.Game;
import com.bleak.graphics.test.Handler;



public class Explosion extends GameObject{
	private Handler handler;
	
	Texture tex = Game.getInstance();
	private Animation smallExplosion = new Animation(10, tex.explosion[0], tex.explosion[1], tex.explosion[2]);
	private Animation bigExplosion = new Animation(20, tex.kaboom[0], tex.kaboom[1]);
	
	public Explosion(float x, float y, Handler handler, int type){
		super(x,y, ObjectId.Explosion);
		this.handler = handler;
		this.type = type;
	}

	public void tick(LinkedList<GameObject> object) {
		switch(this.type){
		case 1:
			smallExplosion.runAnimation();
			break;
		case 2:
			bigExplosion.runAnimation();
			break;
		}
	}

	public void render(Graphics g) {
		switch(this.type){
		case 1:
			if(!(smallExplosion.isPlayedOnce())){
				smallExplosion.drawAnimation(g, (int) x, (int) y, 32, 32);
			} else {
				smallExplosion = null;
				handler.removeObject(this);
			}			
			break;
		case 2:
			if(!(bigExplosion.isPlayedOnce())){
				bigExplosion.drawAnimation(g, (int) x, (int) y, 64, 64);
			} else {
				bigExplosion = null;
				handler.removeObject(this);
			}	
			break;
		}

	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}
	
}
