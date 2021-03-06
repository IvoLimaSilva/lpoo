package com.bump.game.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bump.assets.Assets;
import com.bump.game.Bump;

public class DesktopLauncher
{
	public static void main (String[] arg)
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Bump";
		config.height = (int) Assets.windowHeight;
		config.width = (int) Assets.windowWidth;
		config.fullscreen = true;
		config.addIcon("game/icon.png", Files.FileType.Internal);
		new LwjglApplication(new Bump(), config);
	}
}
