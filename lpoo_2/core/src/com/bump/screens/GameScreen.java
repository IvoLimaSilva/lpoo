package com.bump.screens;

import java.util.ArrayList;

import com.bump.game.Bump;
import com.bump.objects.Wall;
import com.bump.objects.Ball;
import com.bump.assets.Assets;
import com.bump.objects.Button;
import com.bump.objects.Piece;
import com.bump.objects.Square;
import com.bump.objects.Triangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class GameScreen extends ScreenAdapter implements InputProcessor
{
	// General vars
	private Bump
		game;
	OrthographicCamera
		guiCam;
	private Wall
		wallTop,
		wallBottom,
		wallLeft,
		wallRight;
	ArrayList<Piece>
		piecesGlobal = new ArrayList<Piece>();
	Piece
		selectedPiece,
		pieceToPlay;
	World
		world;
	Box2DDebugRenderer
		debugRenderer;
	Matrix4
		debugMatrix;
	public enum
		PlayerTurn {Player1, Player2}
	PlayerTurn
		playerTurn;
	float
		torque = 0.0f;
	int
		state = 0,
		startX,
		startY;
	boolean
		nextPiece = false;
	
	// Player 1 vars
	ArrayList<Piece>
		piecesPlayer1 = new ArrayList<Piece>();
	Ball
		ball1;
	Triangle
		triangle1;
	Square
		square1;
	Sprite
		spritePlayer1Square,
		spritePlayer1Ball,
		spritePlayer1Triangle;
	Button
		buttonRedQuit;
	Integer
		points1 = 0,
		ronda1 = 0;
	Texture
		score1;

	// Player 2 vars
	ArrayList<Piece>
		piecesPlayer2 = new ArrayList<Piece>();
	Ball
		ball2;
	Triangle
		triangle2;
	Square
		square2;
	Sprite
		spritePlayer2Square,
		spritePlayer2Ball,
		spritePlayer2Triangle;
	Button
		buttonBlueQuit;
	Integer
		points2 = 0,
		ronda2 = 0;
	Texture
		score2;

	public GameScreen(Bump game)
	{
		this.game = game;
		guiCam = new OrthographicCamera(Assets.windowWidth, Assets.windowHeight);
		guiCam.position.set(Assets.windowWidth / 2, Assets.windowHeight / 2, 0);
		
		buttonRedQuit = new Button(Assets.buttonRedQuit, 25, 25);
		buttonBlueQuit = new Button(Assets.buttonBlueQuit, Assets.windowWidth - 125, Assets.windowHeight - 125);

		spritePlayer1Square = new Sprite(Assets.spriteRedSquare);
		spritePlayer2Square = new Sprite(Assets.spriteBlueSquare);
		spritePlayer1Ball = new Sprite(Assets.spriteRedBall);
		spritePlayer2Ball = new Sprite(Assets.spriteBlueBall);
		spritePlayer1Triangle = new Sprite(Assets.spriteRedTriangle);
		spritePlayer2Triangle = new Sprite(Assets.spriteBlueTriangle);

		score1 = new Texture("game/red/score/" + points1 + ".png");
		score2 = new Texture("game/blue/score/" + points2 + ".png");

		world = new World(new Vector2(0, 0f), true);
		
		createWalls();
		createPlayer1Pieces();
		createPlayer2Pieces();

		playerTurn = PlayerTurn.Player1;
		pieceToPlay = piecesPlayer1.get(ronda1);
		pieceToPlay.setToPenalty(playerTurn);
		
		Gdx.input.setInputProcessor(this);

		debugRenderer = new Box2DDebugRenderer();
	}
	
	public void createPlayer1Pieces()
	{
		square1 = new Square(PlayerTurn.Player1, world, spritePlayer1Square, -100f, -100f);
		piecesPlayer1.add(square1);
		ball1 = new Ball(PlayerTurn.Player1, world, spritePlayer1Ball, -100f, -100f);
		piecesPlayer1.add(ball1);
		triangle1 = new Triangle(PlayerTurn.Player1, world, spritePlayer1Triangle, -100f, -100f);
		piecesPlayer1.add(triangle1);

		piecesGlobal.add(square1);
		piecesGlobal.add(ball1);
		piecesGlobal.add(triangle1);
	}
	
	public void createPlayer2Pieces()
	{
		square2 = new Square(PlayerTurn.Player2, world, spritePlayer2Square, -100f, -100f);
		piecesPlayer2.add(square2);
		ball2 = new Ball(PlayerTurn.Player2, world, spritePlayer2Ball, -100f, -100f);
		piecesPlayer2.add(ball2);
		triangle2 = new Triangle(PlayerTurn.Player2, world, spritePlayer2Triangle, -100f, -100f);
		piecesPlayer2.add(triangle2);

		piecesGlobal.add(square2);
		piecesGlobal.add(ball2);
		piecesGlobal.add(triangle2);
	}
	
	public void createWalls()
	{
		wallTop = new Wall();
		wallTop.createWall(world, Assets.windowWidth / Assets.PIXELS_TO_METERS, Assets.windowHeight / Assets.PIXELS_TO_METERS, Assets.windowWidth / Assets.PIXELS_TO_METERS, 0);

		wallBottom = new Wall();
		wallBottom.createWall(world, Assets.windowWidth / Assets.PIXELS_TO_METERS, 0, Assets.windowWidth / Assets.PIXELS_TO_METERS, 0);

		wallLeft = new Wall();
		wallLeft.createWall(world, 0, 0, 0, Assets.windowHeight / Assets.PIXELS_TO_METERS);
		
		wallRight = new Wall();
		wallRight.createWall(world, Assets.windowWidth / Assets.PIXELS_TO_METERS, 0, 0, Assets.windowHeight / Assets.PIXELS_TO_METERS);
	}

	public void render(float delta)
	{
		guiCam.update();
		world.step(1f / 60f, 6, 2);

		Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.batcher.enableBlending();
		game.batcher.begin();
		game.batcher.draw(Assets.backgroundGame, 0f, 0f);
		game.batcher.draw(score1, 25f, Assets.windowHeight - 125f);
		game.batcher.draw(score2, Assets.windowWidth - 125f, 25f);
		game.batcher.end();
		
		buttonRedQuit.draw(game.batcher);
		buttonBlueQuit.draw(game.batcher);

		for (Piece piece: piecesGlobal)
		{
			piece.body.applyTorque(torque, true);
			piece.sprite.setPosition((piece.body.getPosition().x * Assets.PIXELS_TO_METERS) - piece.sprite.getWidth() / 2, (piece.body.getPosition().y * Assets.PIXELS_TO_METERS) - piece.sprite.getHeight() / 2);
			piece.sprite.setRotation((float) Math.toDegrees(piece.body.getAngle()));
		}

		// System.out.println("Speed: (" + body.getLinearVelocity().x + ", " + body.getLinearVelocity().y + ", " + body.getAngularVelocity() + ")");

		game.batcher.setProjectionMatrix(guiCam.combined);

		debugMatrix = game.batcher.getProjectionMatrix().cpy().scale(Assets.PIXELS_TO_METERS, Assets.PIXELS_TO_METERS, 0);

		game.batcher.begin();
		game.batcher.enableBlending();
		
		for (Piece piece: piecesGlobal)
		{
			game.batcher.draw(piece.sprite, piece.sprite.getX(), piece.sprite.getY(), piece.sprite.getOriginX(), piece.sprite.getOriginY(), piece.sprite.getWidth(), piece.sprite.getHeight(), piece.sprite.getScaleX(), piece.sprite.getScaleY(), piece.sprite.getRotation());
		}

		game.batcher.end();

		if (arePiecesStopped() && nextPiece)
		{
			checkPoints();
			if (playerTurn == PlayerTurn.Player1)
			{
				ronda1++;
				playerTurn = PlayerTurn.Player2;
				pieceToPlay = piecesPlayer2.get(ronda2);
			}
			else if (playerTurn == PlayerTurn.Player2)
			{
				ronda2++;
				playerTurn = PlayerTurn.Player1;
				pieceToPlay = piecesPlayer1.get(ronda1);
			}
			pieceToPlay.setToPenalty(playerTurn);
			nextPiece = false;
		}

		//debugRenderer.render(world, debugMatrix);
	}
	
	public void checkPoints()
	{
		points1 = 0;
		points2 = 0;
		for (Piece piece: piecesGlobal)
		{
			if (piece.checkPoints())
			{
				if (piece.player == PlayerTurn.Player1)
					points1++;
				else if (piece.player == PlayerTurn.Player2)
					points2++;
			}
		}

		score1 = new Texture("game/red/score/" + points1 + ".png");
		score2 = new Texture("game/blue/score/" + points2 + ".png");

		System.out.println("Player 1 (" + points1 + ") - (" + points2 + ") Player 2.");
	}
	
	public boolean arePiecesStopped()
	{
		Vector2
			nullVector = new Vector2(0f, 0f);
		for (Piece piece: piecesGlobal)
		{
			if (!piece.body.getLinearVelocity().equals(nullVector))
				return false;
		}
		//System.out.println("As pe�as est�o todas paradas.");
		return true;
	}

	public boolean keyDown(int keycode)
	{
		return false;
	}

	public boolean keyUp(int keycode)
	{
		return false;
	}

	public boolean keyTyped(char character)
	{
		return false;
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		//System.out.println("touchDown: " + (screenX - Assets.windowWidth / 2) + ", " + (Assets.windowHeight / 2 - screenY) + ", " + pointer + "," + button);
		//System.out.println("Red Quit: (" + buttonRedQuit.bounds.x + "," + buttonRedQuit.bounds.y + "; " + buttonRedQuit.bounds.width + ", " + buttonRedQuit.bounds.height + ").");

		if (buttonRedQuit.bounds.contains((screenX - Assets.windowWidth / 2), (Assets.windowHeight / 2 - screenY)))
			game.setScreen(new MenuScreen(game));
		else if (buttonBlueQuit.bounds.contains((screenX - Assets.windowWidth / 2), (Assets.windowHeight / 2 - screenY)))
			game.setScreen(new MenuScreen(game));
		
		state = -1;
		for (Piece piece: piecesGlobal)
		{
			if (piece.contains(screenX, Assets.windowHeight - screenY) && piece.isPlayableBy(playerTurn))
			{
				selectedPiece = piece;
				state = 0;
				startX = screenX;
				startY = screenY;
			}
		}

		return true;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		//System.out.println ("touchUp: " + screenX + ", " + screenY + ", " + pointer + "," + button);
		if (state == 1)
		{
			selectedPiece.body.applyAngularImpulse(0.1f, true);
			selectedPiece.body.applyForceToCenter((float) 0.5 * (screenX - startX), (float) 0.5 * (startY - screenY), true);
			System.out.println ("Movimento: (" + (screenX - startX) + ", " + (screenY - startY) + ").");
			nextPiece = true;
		}
		return false;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		//System.out.println ("Dragged: " + screenX + ", " + screenY + ", " + pointer);
		if (state == 0)
			state = 1;
		return false;
	}

	public boolean mouseMoved(int screenX, int screenY)
	{
		return false;
	}

	public boolean scrolled(int amount)
	{
		return false;
	}
}