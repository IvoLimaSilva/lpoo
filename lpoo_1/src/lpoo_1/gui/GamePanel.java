package lpoo_1.gui;

import lpoo_1.logic.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GamePanel extends JPanel implements KeyListener
{
	private static final long serialVersionUID = 1L;
	private static int matrixSize;
	private static char[][] matrix;
	static Game game;
	
	public GamePanel(boolean random, int size)
	{
		super();
		LoadNewMatrix(random, size);
	}
	
	public void LoadNewMatrix(boolean random, int size)
	{
		this.removeKeyListener(this);
		
		if (matrixSize != size)
			setBounds((1920 - ((size + 1) * 32)) / 2, (1080 - ((size + 1) * 32 + 65)) / 2, (size + 1) * 32, (size + 1) * 32 + 65);
		
		GamePanel.matrixSize = size;
		game = new Game(size, random);
		matrix = game.getMatrix();
		
		System.out.println("Matrix loaded!");
		printMatrix();
		
		this.addKeyListener(this);
	}
	
	public void keyPressed(KeyEvent e)
	{
		// Move heroi
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_LEFT: 
				System.out.println("Key left;");
				game.moveHero('a');
				break;
				
			case KeyEvent.VK_RIGHT:
				System.out.println("Key right;");
				game.moveHero('d');
				break;
	
			case KeyEvent.VK_UP:
				System.out.println("Key up;");
				game.moveHero('w');
				break;
	
			case KeyEvent.VK_DOWN:
				System.out.println("Key down;");
				game.moveHero('s');
				break;
		}
	
		// Verifica se o jogo acabou
		if (game.gameOver())
		{
			// Desenha matrix
			matrix = game.getMatrix();
			System.out.println("1");
			printMatrix();
			repaint();
			
			// Encerra a janela
			this.removeKeyListener(this);
		}
		else
		{
			// Move drag�es
			game.moveDragon();
			
			// Desenha matrix
			matrix = game.getMatrix();
			System.out.println("2");
			printMatrix();
			repaint();
		}
		
		// Verifica se o jogo acabou
		if (game.gameOver())
		{
			// Desenha matrix
			matrix = game.getMatrix();
			System.out.println("3");
			printMatrix();
			repaint();
			
			// Encerra a janela
			this.removeKeyListener(this);
			
			JOptionPane.showMessageDialog(null, "Acabou o jogo!", "End Game", JOptionPane.OK_OPTION);
		}
		else
		{
			// Desenha matrix
			matrix = game.getMatrix();
			System.out.println("4");
			printMatrix();
			repaint();
		}
	}
	
	public void paintComponent(Graphics g)
	{
		setBounds(20, 50, (matrixSize + 1) * 32, (matrixSize + 1) * 32 + 65);
		
		Image img;
		
		super.paintComponent(g);
		
		for (int i = 0; i < matrixSize; i++)
		{
			for (int j = 0; j < matrixSize; j++)
			{
				ImageIcon aux_img = new ImageIcon("img/map/floor.png");
				img = aux_img.getImage();
				g.drawImage(img, i * 32, j * 32, null);
				
				if (matrix[j][i] == 'X')
					aux_img = new ImageIcon("img/map/wall.png");
				
				else if (matrix[j][i] == '^')
					aux_img = new ImageIcon("img/hero/back.png");
				
				else if (matrix[j][i] == 'v')
					aux_img = new ImageIcon("img/hero/front.png");
				else if (matrix[j][i] == '>')
					aux_img = new ImageIcon("img/hero/right.png");
				else if (matrix[j][i] == '<')
					aux_img = new ImageIcon("img/hero/left.png");
				
				else if (matrix[j][i] == '1')
					aux_img = new ImageIcon("img/hero/right_sword.png");
				else if (matrix[j][i] == '2')
					aux_img = new ImageIcon("img/hero/left_sword.png");
				else if (matrix[j][i] == '3')
					aux_img = new ImageIcon("img/hero/front_sword.png");
				
				else if (matrix[j][i] == '4')
					aux_img = new ImageIcon("img/hero/right_shield.png");
				else if (matrix[j][i] == '5')
					aux_img = new ImageIcon("img/hero/left_shield.png");
				else if (matrix[j][i] == '6')
					aux_img = new ImageIcon("img/hero/front_shield.png");
				
				else if (matrix[j][i] == '7')
					aux_img = new ImageIcon("img/hero/right_sword_shield.png");
				else if (matrix[j][i] == '8')
					aux_img = new ImageIcon("img/hero/left_sword_shield.png");
				else if (matrix[j][i] == '9')
					aux_img = new ImageIcon("img/hero/front_sword_shield.png");
				
				else if (matrix[j][i] == 'W')
					aux_img = new ImageIcon("img/hero/winner.png");
				
				else if (matrix[j][i] == 'D' || matrix[j][i] == 'F' || matrix[j][i] == '.' || matrix[j][i] == '�')
					aux_img = new ImageIcon("img/dragon/front.png");
				else if (matrix[j][i] == 'd' || matrix[j][i] == 'f' || matrix[j][i] == '_' || matrix[j][i] == '�')
					aux_img = new ImageIcon("img/dragon/back.png");
				
				else if (matrix[j][i] == 'S')
					aux_img = new ImageIcon("img/map/exit.png");
				else if (matrix[j][i] == 'E')
					aux_img = new ImageIcon("img/map/sword.png");
				else if (matrix[j][i] == 'o')
					aux_img = new ImageIcon("img/map/shield.png");
				else if (matrix[j][i] == '*')
					aux_img = new ImageIcon("img/map/dart.png");
				else if (matrix[j][i] == '�')
					aux_img = new ImageIcon("img/map/grave.png");
					
				img = aux_img.getImage();				
				g.drawImage(img, i * 32, j * 32, null);
			}
			
		}
	}

	private static void printMatrix()
	{
		for (int i = 0; i < matrixSize; i++)
		{
			for (int j = 0; j < matrixSize; j++)
				System.out.print(matrix[i][j] + " ");
			
			System.out.println();
		}
	}

	public void keyReleased(KeyEvent e)
	{
	}

	public void keyTyped(KeyEvent e)
	{
		System.out.println("Key down;");
		game.moveHero('+');
	}
	
	public static Game getGame()
	{
		System.out.println("Saved Game:");
		printMatrix();
		return game;
	}
	
	public static int getMatrixSize()
	{
		return matrixSize;
	}

	public static void loadGame(Game newgame)
	{
		System.out.println("Loaded Game:");
		game.loadNewGame(newgame);
		matrixSize = newgame.getMatrix().length;
		
		printMatrix();
	}
}
