package com.bump.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Assets
{
	public static float
		windowWidth = 1280,
		windowHeight = 720,
		PIXELS_TO_METERS = 100f;

	public static Texture
		backgroundMenu,
		backgroundGame,
		backgroundHighscores,

		buttonBump,
		buttonPlay,
		buttonPastGames,
		buttonInstructions,
		buttonExit,

		spriteRedBall,
		spriteRedTriangle,
		spriteRedSquare,
		spriteBlueBall,
		spriteBlueTriangle,
		spriteBlueSquare,
		
		buttonRedQuit,
		buttonBlueQuit,
		buttonRedWin,
		buttonBlueWin,
		buttonDraw,
		
		windowRedWin,
		windowBlueWin,
		windowDraw;

	public static Vector2
		penaltyPlayer1 = new Vector2(75 / Assets.PIXELS_TO_METERS, Assets.windowHeight / 2 / Assets.PIXELS_TO_METERS),
		penaltyPlayer2 = new Vector2((Assets.windowWidth - 75) / Assets.PIXELS_TO_METERS, Assets.windowHeight / 2 / Assets.PIXELS_TO_METERS);

	public static void load()
	{
		backgroundMenu = new Texture("menu/background.jpg");
		backgroundGame = new Texture("game/background.png");
		backgroundHighscores = new Texture("highscores/background.jpg");

		buttonBump = new Texture("menu/MainTile.jpg");
		buttonPlay = new Texture("menu/PlayTile.jpg");
		buttonPastGames = new Texture("menu/PastGamesTiles.jpg");
		buttonInstructions = new Texture("menu/InstructionsTile.jpg");
		buttonExit = new Texture("menu/ExitTile.jpg");

		spriteRedBall = new Texture("game/red/ball.png");
		spriteRedTriangle = new Texture("game/red/triangle.png");
		spriteRedSquare = new Texture("game/red/square.png");

		spriteBlueBall = new Texture("game/blue/ball.png");
		spriteBlueTriangle = new Texture("game/blue/triangle.png");
		spriteBlueSquare = new Texture("game/blue/square.png");
		
		buttonRedQuit = new Texture("game/red/close.png");
		buttonBlueQuit = new Texture("game/blue/close.png");
		buttonRedWin = new Texture("menu/red/check.png");
		buttonBlueWin = new Texture("menu/blue/check.png");
		buttonDraw = new Texture("menu/check.png");

		windowRedWin = new Texture("game/red/win.png");
		windowBlueWin = new Texture("game/blue/win.png");
		windowDraw = new Texture("game/draw.png");
	}

	public static float distance(float x1, float y1, float x2, float y2)
	{
		return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
}
