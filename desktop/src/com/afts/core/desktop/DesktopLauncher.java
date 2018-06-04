package com.afts.core.desktop;

import com.afts.core.Utility.StaticSettings;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.afts.core.Core;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = StaticSettings.GAME_TITLE;
		config.width = 1280;
		config.height = 720;
		config.samples = 2;
		config.foregroundFPS = 300;
		new LwjglApplication(new Core(), config);
	}
}
