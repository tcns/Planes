package com.gamegame.planes;

import java.io.IOException;
import java.io.InputStream;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import implemented.GLGame;
import framework.Font;
import framework.Texture;
import framework.TextureRegion;

public class Assets {
	static public TextureRegion spaceship, spaceship_enemy,
			planet_unselected, planet_enemy, planet_neutral, planet_selected,
			menuResume, menuNewgame, menuHelp, particle;
	static public Texture planeItems, items, menu;

	public static Font font;

	public static void load(GLGame game) {
		planeItems = new Texture(game, "1.png");

		menu = new Texture(game, "buttons.png");
		menuNewgame = new TextureRegion(menu, 0, 0, 512, 45);
		menuResume = new TextureRegion(menu, 0, 50, 512, 50);
		menuHelp = new TextureRegion(menu, 0, 100, 512, 50);
		spaceship_enemy = new TextureRegion(planeItems, 375, 0, 60, 50);
		spaceship = new TextureRegion(planeItems, 375, 70, 60, 50);
		planet_enemy = new TextureRegion(planeItems, 0, 15, 90, 90);
		planet_unselected = new TextureRegion(planeItems, 0, 145, 90, 90);
		planet_neutral = new TextureRegion(planeItems, 130, 15, 90, 90);
		planet_selected = new TextureRegion(planeItems, 260, 15, 90, 90);
		particle = new TextureRegion(planeItems, 474, 6, 10, 10);
		// items = new Texture(game, "items.png");
		// font = new Font(items, 224, 0, 16, 16, 20);
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			InputStream is;
			is = game.getFileIO().readAsset("font2.xml");
			parser.setInput(is, null);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		items = new Texture(game, "font2.PNG");
		font = new Font(items, parser);
		
	}

	public static void reload() {
		planeItems.reload();
		items.reload();
		menu.reload();
	}

}
