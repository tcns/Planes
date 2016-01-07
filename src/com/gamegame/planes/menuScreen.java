package com.gamegame.planes;

import framework.Camera2D;
import framework.Game;
import framework.Input.TouchEvent;
import framework.SpriteBatcher;
import framework.Math.OverlapTester;
import framework.Math.Rectangle;
import framework.Math.Vector2;
import implemented.GLScreen;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

public class menuScreen extends GLScreen {

	Camera2D menuCam;
	SpriteBatcher batcher;
	Vector2 touchPoint;
	Rectangle newgameBounds;
	Rectangle helpBounds;
	public menuScreen(Game game) {
		super(game);
		menuCam = new Camera2D(glGraphics, glGraphics.getWidth(), glGraphics.getHeight());
		batcher = new SpriteBatcher(glGraphics, 100);
		touchPoint = new Vector2();
		newgameBounds = new Rectangle(glGraphics.getWidth()/2-256, glGraphics.getHeight()/2-25, 512, 50);
		helpBounds = new Rectangle(glGraphics.getWidth()/2-256, glGraphics.getHeight()/2-115, 512, 50);
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				touchPoint.set(event.x, event.y);
				//menuCam.touchToWorld(touchPoint);
				if(OverlapTester.pointInRectangle(newgameBounds, touchPoint)){
					game.setScreen(new GameScreen(game));
					return;
				}
			}
		}

	}

	@Override
	public void present(float deltaTime) {
		GL10 gl = glGraphics.getGL();
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		menuCam.setViewportAndMatrices();
		gl.glEnable(GL10.GL_TEXTURE_2D);
		//gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		batcher.beginBatch(Assets.menu);
		batcher.drawSprite(glGraphics.getWidth()/2, glGraphics.getHeight()/2, 512, 50, Assets.menuNewgame);
		batcher.drawSprite(glGraphics.getWidth()/2, glGraphics.getHeight()/2-90, 512, 50, Assets.menuHelp);
		batcher.endBatch();
		//gl.glDisable(GL10.GL_BLEND);
		//batcher.drawSprite(0, 100, 512, 50, Assets.menuNewgame);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
