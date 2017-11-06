package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.ExGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "LP2 Game";
		cfg.width = 1280;
		cfg.height = 720;
		cfg.useGL30 = true;
		cfg.resizable = true;
		
		new LwjglApplication(new ExGame(), cfg);
	}
}
