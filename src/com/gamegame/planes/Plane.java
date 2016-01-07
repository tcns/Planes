package com.gamegame.planes;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Path;
import android.graphics.PathMeasure;

public class Plane extends GameEntity {

	Island planeIsland, planeEndIsland;
	boolean isDestroyed = false;
	float k;
	float xTo, xFrom, yTo, yFrom, realxTo, realxFrom, realyTo, realyFrom;
	float rotation, speed, distance;
	Path path;
	PathMeasure measure;
	float[] pos, tan;
	private int age;
	public List<Position> positionList;

	class Position {
		private float x, y;

		public Position(float x, float y) {
			this.x = x;
			this.y = y;
		}

		public float getX() {
			return x;
		}

		public float getY() {
			return y;
		}
	}

	public Plane(Island islandFrom, Island islandTo) {
		planeIsland = islandFrom;
		planeEndIsland = islandTo;
		xTo = islandTo.getX();
		yTo = islandTo.getY();
		realxTo = islandTo.getRealX();
		realyTo = islandTo.getRealY();
		xFrom = islandFrom.getX();
		yFrom = islandFrom.getY();
		realxFrom = islandFrom.getRealX();
		realyFrom = islandFrom.getRealY();
		path = new Path();
		path.moveTo(xFrom, yFrom);
		path.lineTo(xTo, yTo);
		measure = new PathMeasure(path, false);
		speed = 2.5f;
		pos = new float[2];
		tan = new float[2];
		setX(xFrom, srcWidth);
		setY(xFrom, srcHeight);
		age = 0;
		positionList = new ArrayList<Plane.Position>();
	}

	@Override
	public float getRealX() {
		return srcWidth / PlanesRenderer.FRUSTUM_WIDTH * getX();
	}

	@Override
	public float getRealY() {
		return srcHeight / PlanesRenderer.FRUSTUM_HEIGHT * getY();
	}

	public void setCiv(int val) {
		civil = val;
		if (civil == 2) {
			img = Assets.spaceship_enemy;
		}
		if (civil == 1) {
			img = Assets.spaceship;
		}
	}

	public int getAge() {
		return age;
	}

	public void fly(float delta) {

		float len = measure.getLength();
		if (distance < len) {
			measure.getPosTan(distance, pos, tan);
			distance += speed * delta;
			setX(pos[0]);
			setY(pos[1]);

			age++;
			if (age % 5 == 0)
				positionList.add(new Position(pos[0], pos[1]));
		} else {
			isDestroyed = true;
			positionList.clear();
		}
	}

	public float getRotation() {
		double rot = Math.toDegrees(Math.atan2(realyTo - realyFrom, realxFrom
				- realxTo));
		return (float) rot;
	}

	public void destroy() {
		planeEndIsland.destroyPopulation(population, getCiv());
		if (planeEndIsland.getPopulation() < 0) {
			planeEndIsland.setPopulation(Math.abs(planeEndIsland
					.getPopulation()));
			planeEndIsland.setCiv(getCiv());
			planeEndIsland.Unselect();
		}
	}
}
