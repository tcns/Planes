package com.gamegame.planes;

import framework.Camera2D;
import framework.TextureRegion;
import framework.Math.Vector2;

public class GameEntity {
	protected TextureRegion img = null;
	protected int population;
	protected float realX = 0, realY = 0;
	protected float x = 0, y = 0;
	protected int civil = 0;
	protected static int srcWidth, srcHeight;
	
	public void setX(float val, int screenWidth) {
		x = val;
		srcWidth = screenWidth;
		realX = screenWidth / PlanesRenderer.FRUSTUM_WIDTH * x;
	}

	public void setY(float val, int screenHeight) {
		y = val;
		srcHeight = screenHeight;
		realY = screenHeight - screenHeight / PlanesRenderer.FRUSTUM_HEIGHT * y;
	}
	
	public void setX(float val) {
		x = val;
	}

	public void setY(float val) {
		y = val;
	}
	public float getRealX() {
		return realX;
	}

	public float getRealY() {
		return realY;
	}
	public float getRealXcam(Camera2D cam) {
		Vector2 realCoords = new Vector2(realX, realY);
		cam.touchToWorld(realCoords);
		return realCoords.x;
	}

	public float getRealYcam(Camera2D cam) {
		Vector2 realCoords = new Vector2(realX, realY);
		cam.touchToWorld(realCoords);
		return realCoords.y;
	}
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public int getCiv() {
		return civil;
	}
	public void setPopulation(int val) {
		population = val;
	}
	public int getPopulation() {
		return population;
	}
}
