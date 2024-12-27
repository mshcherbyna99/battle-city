package com.bleak.graphics.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.bleak.graphics.framework.GameObject;
import com.bleak.graphics.framework.ObjectDirection;
import com.bleak.graphics.framework.ObjectId;
import com.bleak.graphics.framework.Texture;
import com.bleak.graphics.test.Game;
import com.bleak.graphics.test.Handler;
import com.bleak.graphics.test.SFX;

public class Bullet extends GameObject{

	private float width = 32, height = 32;
	
	protected ObjectDirection direction;
	
	private Handler handler;
	
	Texture tex = Game.getInstance();
	
	public Bullet(float x, float y, float velX, float velY, Handler handler, ObjectId id, ObjectDirection direction) {
		super(x, y, id);
		this.handler = handler;
		this.velX = velX;
		this.velY = velY;
		this.id = id;
		this.damage = 1;
		this.direction = direction;
	}

	public void tick(LinkedList<GameObject> object) {
		x += velX;
		y += velY;
		
		Collision(object);		
	}

	private void Collision(LinkedList<GameObject> object)
	{
		boolean remove = false;
		for(int i = 0; i < handler.object.size(); i++)
		{
			GameObject tempObject = handler.object.get(i);
			//if(tempObject.getId() == ObjectId.Block){
			//if(getBounds().intersects(tempObject.getBounds())){
			if(getBounds().intersects(tempObject.getBounds())){
				if(tempObject.getId() == ObjectId.Block){
					SFX.EXPLODE.play();
					if(tempObject.type == 1)
						handler.removeObject(tempObject);
					remove = true;
					handler.addObject(new Explosion(this.getX()-this.width/2, this.getY()-this.height/2, handler,1));
				}
				
				if(tempObject.getId() == ObjectId.Enemy){
					SFX.RICHCHET.play();
					tempObject.health = tempObject.health - this.damage;
					remove = true;
					//handler.removeObject(tempObject);
				}
			}
		}
		if(remove == true){
			handler.removeObject(this);
			remove = false;
		}
	}
	
	public void render(Graphics g) {
		switch (this.direction) {
		case Up : 
			g.drawImage(tex.bullet[0], (int) (x-width/2), (int) (y-height/2), (int) width, (int) height, null);
			break;
		case Down :
			g.drawImage(tex.bullet[2], (int) (x-width/2), (int) (y-height/2), (int) width, (int) height, null);
			break;
		case Left :
			g.drawImage(tex.bullet[1], (int) (x-width/2), (int) (y-height/2), (int) width, (int) height, null);
			break;
		case Right :
			g.drawImage(tex.bullet[3], (int) (x-width/2), (int) (y-height/2), (int) width, (int) height, null);
			break;
		default:
			break;
		}
	}

	public void renderDebug(Graphics g){
		g.setColor(Color.red);
		g.drawRect((int) (x-width/2), (int) (y-height/2), (int) width, (int) height);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) (x-width/2), (int) (y-height/2), (int) width, (int) height);
	}
	
}
