package com.hereandtheregames.davethehelper;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.hereandtheregames.davethehelper.MainGame;
import com.hereandtheregames.davethehelper.utils.GameConfig;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		//config.setWindowedMode((int)GameConfig.VIEWPORT_WIDTH, (int)GameConfig.VIEWPORT_HEIGHT);
		config.setWindowedMode((int)GameConfig.VIEWPORT_WIDTH-400, (int)GameConfig.VIEWPORT_HEIGHT);
		config.setTitle("DaveTheHelper");
		new Lwjgl3Application(new MainGame(), config);
	}
}
