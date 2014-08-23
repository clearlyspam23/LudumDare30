package com.clearlyspam23.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.clearlyspam23.game.LD30SpaceGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1020;
		config.height = 800;
		new LwjglApplication(new LD30SpaceGame(), config);
	}
}
