package com.bleak.graphics.test;

import com.bleak.graphics.framework.GameObject;

public class Camera {
	private float x,y;
	
	public Camera(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void tick(GameObject player){
		x = Game.WIDTH/2 - player.getX();
		y = Game.HEIGHT/2 - player.getY();
	}
	
	public void setX(float x){
		this.x = x;
	}
	
	public void setY(float y){
		this.y = y;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	
}
