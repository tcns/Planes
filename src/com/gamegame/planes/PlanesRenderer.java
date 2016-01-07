package com.gamegame.planes;


import java.util.List;


import static java.lang.Math.*;
import javax.microedition.khronos.opengles.GL10;

import framework.Camera2D;
import framework.GLPrimitives;
import framework.ParticleSystem;

import framework.SpriteBatcher;

import implemented.GLGraphics;

public class PlanesRenderer {

	static final float FRUSTUM_WIDTH = 10;
	static final float FRUSTUM_HEIGHT = 6;
	GLGraphics glGraphics;
	Camera2D cam;
	SpriteBatcher batcher;
	Camera2D guiCam;
	static float x[], y[];
	static float pTotal, eTotal;

	public  float getRealHeight(float relative) {
		return ((float) glGraphics.getHeight() / FRUSTUM_HEIGHT) * relative;
	}

	public float getRealWidth(float relative) {
		return ((float) glGraphics.getWidth() / FRUSTUM_WIDTH) * relative;
	}
	public  float getFrustumHeight(float real) {
		return (real/ glGraphics.getHeight()) * FRUSTUM_HEIGHT;
	}

	public float getFrustumWidth(float real) {
		return (real/ glGraphics.getWidth()) * FRUSTUM_WIDTH;
	}

	public PlanesRenderer(GLGraphics graphics, SpriteBatcher batcher) {
		glGraphics = graphics;
		this.batcher = batcher;
		this.cam = new Camera2D(glGraphics, FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		this.guiCam = new Camera2D(glGraphics, glGraphics.getWidth(),
				glGraphics.getHeight());
	}
	
	

	public void renderObjects(List<Plane> p, List<Island> i, ParticleSystem ps) {
		GL10 gl = glGraphics.getGL();

		gl.glEnable(GL10.GL_BLEND);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);

		guiCam.setViewportAndMatrices();

		batcher.beginBatch(Assets.items);
		renderStats(i, p);
		batcher.endBatch();

		cam.setViewportAndMatrices();

		batcher.beginBatch(Assets.planeItems);

		renderPlanes(p);
		renderPlanets(i);

		batcher.endBatch();

		gl.glDisable(GL10.GL_TEXTURE_2D);

		// renderLines();
		gl.glDisable(GL10.GL_BLEND);

	}

	@SuppressWarnings("unused")
	private void renderLines() {

		int a = Island.playerIslands.size();
		x = new float[a];
		y = new float[a];
		int j = 0;
		for (Island i : Island.playerIslands) {
			x[j] = i.getRealX();
			y[j] = glGraphics.getHeight() - i.getRealY();
			j++;
		}
		for (int i = 0; i < x.length - 1; i++) {
			for (j = i + 1; j < x.length; j++) {
				GLPrimitives.drawLine(glGraphics, x[i], y[i], x[j], y[j]);
			}
		}

		// GLPrimitives.filledPolygon(glGraphics, 0, x, y);
	}

	public void renderPlanes(List<Plane> planes) {

		for (Plane plane : planes) {

			batcher.drawSprite(plane.getX() + 0.5f, plane.getY() - 0.5f, 0.5f,
					0.5f, plane.getRotation(), plane.img);
			for (int i = 0; i < plane.positionList.size(); i++) {
				float r = plane.getRotation();
				// TODO ¬ыровн€ть расположение точек относительно X
				batcher.drawSprite(
						(float) (plane.positionList.get(i).getX() + cos(toRadians(r)) * 0.5f) + 0.5f,
						(float) (plane.positionList.get(i).getY() + sin(toRadians(r)) * 0.5f) - 0.5f,
						0.08f, 0.08f, Assets.particle);
			}
		}
	}

	public void renderPlanets(List<Island> islands) {

		for (Island i : islands) {

			batcher.drawSprite(i.getX() + 0.5f, i.getY() - 0.5f, i.getSize(),
					i.getSize(), i.img);

		}
	}

	public void renderStats(List<Island> islands, List<Plane> planes) {

		pTotal = 0;
		eTotal = 0;
		for (Island i : islands) {
			if (i.getCiv() == 1)
				pTotal += i.getPopulation();
			else if (i.getCiv() == 2)
				eTotal += i.getPopulation();

			float x = i.getRealX() + getRealWidth(0.5f)
					- (i.getPopulation() / 10 < 1 ? 0 : 8);
			float y = glGraphics.getHeight() - i.getRealY()
					- getRealHeight(0.5f);
			Assets.font.drawText(batcher, String.valueOf(i.getPopulation()), x,
					y, 0.4f);
		}

		

	}
	
	public void renderZoom(float zoom, float bool, float dist){
		Assets.font.drawText(batcher, zoom + " " + bool+" "+dist,
				(float) (glGraphics.getWidth() * 0.2),
				(float) glGraphics.getWidth() / 2, 0.5f);
	}

}
