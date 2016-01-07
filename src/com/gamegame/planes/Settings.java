package com.gamegame.planes;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;



import framework.FileIO;

public class Settings {
	public static boolean soundEnabled = true;
	public static int level = 1;

	public static void Load(FileIO files) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(
					files.readFile(".planes")));
			soundEnabled = Boolean.parseBoolean(in.readLine());
			level = Integer.parseInt(in.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void Save(FileIO file) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					file.writeFile(".planes")));
			out.write(Boolean.toString(soundEnabled));
			out.write(Integer.toString(level));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
