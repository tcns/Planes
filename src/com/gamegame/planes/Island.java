package com.gamegame.planes;

import java.util.ArrayList;
import java.util.List;

import framework.TextureRegion;

public class Island extends GameEntity {

	boolean isSel = false, isPopupShow = false, st = false, isfigShow = false,
			sf = false;
	private float size;
	int popupTimer = 0, figTimer = 0;
	String popupMessage = "";
	
	public static List<Island> playerIslands = new ArrayList<Island>();

	public float getSize() {
		size = (float)population/200 + 0.5f;
		return size;
	}

	public void grow() {
		if (population < 100) {
			if (getCiv() == 2)
				population += 1;
			else if (getCiv() == 1)
				population += 1;
		}
		if (population > 100) {
			population --;
		}
	}

	public void setSize() {
		if (civil == 2) {
			size = 3;
		} else {
			if (civil == 1) {
				size = 3;
			} else {
				size = population / 10;
			}
		}
	}

	public void destroyPopulation(int x, int civ) {
		int c = getCiv();
		if (civ == c) {
			population += x;
		} else {
			population -= x;
		}
	}

	public void setCiv(int val) {
		if (val >= 0 && val <= 2) {
			civil = val;
		}
		switch (civil) {
		case 1:
			playerIslands.add(this);
			img = Assets.planet_unselected;
			break;
		case 2:
			img = Assets.planet_enemy;
			break;
		case 0:
			img = Assets.planet_neutral;
			break;
		}

	}

	public void Select() {
		if (!isSel) {
			img = Assets.planet_selected;

			isSel = true;
		} else {
			img = Assets.planet_unselected;

			isSel = false;
		}
	}

	public void Unselect() {
		if (civil == 1) {
			img = Assets.planet_unselected;
			isSel = false;
		}
	}

	public void setImg(TextureRegion image) {
		img = image;
	}

	public void timer() {
		if (st) {
			popupTimer++;
		}
		if (popupTimer <= 2) {
			isPopupShow = true;
		}
		if (popupTimer >= 200) {
			isPopupShow = false;
			popupTimer = 0;
			st = false;
		}

	}

	public void timerFig() {
		if (sf) {
			figTimer++;
		}
		if (figTimer <= 2) {
			isfigShow = true;
		}
		if (figTimer >= 300) {
			isfigShow = false;
			figTimer = 0;
			sf = false;
		}

	}
}
