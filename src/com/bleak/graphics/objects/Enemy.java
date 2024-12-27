package com.bleak.graphics.objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
//import java.util.Random;



import com.bleak.graphics.framework.GameObject;
import com.bleak.graphics.framework.ObjectDirection;
import com.bleak.graphics.framework.ObjectId;
import com.bleak.graphics.framework.Texture;
import com.bleak.graphics.test.Animation;
import com.bleak.graphics.test.Game;
import com.bleak.graphics.test.Handler;
import com.bleak.graphics.test.SFX;

public class Enemy extends GameObject {
	private int width = 64, height = 64;
	
	private Handler handler;
	
	Texture tex = Game.getInstance();
	
	private Animation enemyMoveUp;
	private Animation enemyMoveDown;
	private Animation enemyMoveLeft;
	private Animation enemyMoveRight;
	
	//private Random random;
	
	public Enemy(float x, float y, Handler handler, ObjectId id) {
		super(x, y, id);
		this.handler = handler;
		this.direction = ObjectDirection.Down;
		this.setVelY(1);
		this.health = 5;
		//random = new Random();
		
		enemyMoveUp = new Animation(5, tex.enemy[1], tex.enemy[2]);
		enemyMoveDown = new Animation(5, tex.enemy[5], tex.enemy[6]);
		enemyMoveLeft = new Animation(5, tex.enemy[3], tex.enemy[4]);
		enemyMoveRight = new Animation(5, tex.enemy[7], tex.enemy[8]);
	}

	public void tick(LinkedList<GameObject> object) {
		x += velX;
		y += velY;
		
		Collision(object);	
		
		enemyMoveUp.runAnimation();
		enemyMoveDown.runAnimation();
		enemyMoveLeft.runAnimation();
		enemyMoveRight.runAnimation();
		
		if(health < 1){
			handler.removeObject(this);
			handler.addObject(new Explosion(this.getX(), this.getY(), handler,2));
			SFX.BIGEXPLO.play();
		}
	}

	private void Collision(LinkedList<GameObject> object)
	{
		for(int i = 0; i < handler.object.size(); i++)
		{
			GameObject tempObject = handler.object.get(i);
			
			if((tempObject.getId() == ObjectId.Block) || (tempObject.getId() == ObjectId.Player)){
				
				//top collision
				if(getBoundsTop().intersects(tempObject.getBounds())){
					y = tempObject.getY()+32;
					velX = -1;
					velY = 0;
					//velY = -1*((random.nextBoolean()) ? 1 : 0) + 1*((random.nextBoolean()) ? 1 : 0);
					//velX = -1*((random.nextBoolean()) ? 1 : 0) + 1*((random.nextBoolean()) ? 1 : 0);
				}
				
				//bottom collision
				if(getBounds().intersects(tempObject.getBounds())){
					y = tempObject.getY() - height;
					velY = 0;
					velX = 1;
				} 
				
				//right collision
				if(getBoundsRight().intersects(tempObject.getBounds())){
					x = tempObject.getX()-width;
					velX = 0;
					velY = -1;
				}
				
				//left collision
				if(getBoundsLeft().intersects(tempObject.getBounds())){
					x = tempObject.getX()+32;
					velX = 0;
					velY = 1;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(velX != 0){
			if(velX > 0){
				enemyMoveRight.drawAnimation(g, (int) x, (int) y, 64, 64);
				this.direction = ObjectDirection.Right;
			} else {
				enemyMoveLeft.drawAnimation(g, (int) x, (int) y, 64, 64);
				this.direction = ObjectDirection.Left;
			}
		} else if(velY!=0){
			if(velY > 0){
				enemyMoveDown.drawAnimation(g, (int) x, (int) y, 64, 64);
				this.direction = ObjectDirection.Down;
			} else {
				enemyMoveUp.drawAnimation(g, (int) x, (int) y, 64, 64);
				this.direction = ObjectDirection.Up;
			}
		} else {
			switch (this.direction) {
			case Up : 
				g.drawImage(tex.enemy[1], (int) x, (int) y, 64, 64, null);
				break;
			case Down :
				g.drawImage(tex.enemy[5], (int) x, (int) y, 64, 64, null);
				break;
			case Left :
				g.drawImage(tex.enemy[3], (int) x, (int) y, 64, 64, null);
				break;
			case Right :
				g.drawImage(tex.enemy[7], (int) x, (int) y, 64, 64, null);
				break;
			default:
				break;
			}
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) ((int) x+(width/2)-((width/2)/2)), (int) ((int) y+(height/2)), (int) width/2, (int) height/2);
	}

	public Rectangle getBoundsTop() {
		return new Rectangle((int) ((int) x+(width/2)-((width/2)/2)), (int) y, (int) width/2, (int) height/2);
	}
	
	public Rectangle getBoundsRight() {
		return new Rectangle((int) ((int) x+width-5), (int) y+5, (int) 5, (int) height-10);
	}
	
	public Rectangle getBoundsLeft() {
		return new Rectangle((int) x, (int) y+5, (int) 5, (int) height-10);
	}
	
	public Rectangle getBoundsBody() {
		return new Rectangle((int) x, (int) y, (int) width, (int) height);
	}
}
