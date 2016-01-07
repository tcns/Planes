package com.gamegame.planes;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


import framework.Screen;
import implemented.GLGame;

public class PlanesGame extends GLGame {

	
	private boolean firstTimeCreate = true;

	@Override
	public Screen getStartScreen() {
		Assets.load(this);
		return new menuScreen(this);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		if (firstTimeCreate ) {
			Assets.load(this);
			firstTimeCreate = false;
		} else {
			Assets.reload();
		}
		
	}
	
	
}
