package Tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Tetris extends JPanel implements KeyListener{
	

	private static final long serialVersionUID = 1L;
	public static int sizeX, sizeY;
	public int[][] board;
	private Shape[] shapes;
	private int boxSize = 55;
	private Shape currShape;
	private Timer timer;
	private Random random;
	private int score;
	public static Map<Integer, Color> colours;
	private int diagram = 0;
	
	Tetris(){
				
		sizeX = 18;
		sizeY = 12;
		board = new int[sizeX][sizeY];
		for(int i = 0; i<sizeX; i++)
			for(int j = 0; j<sizeY; j++)
				board[i][j] = (j == 0 || j == sizeY-1 || i == sizeX-1) ? 7 : 0;
		
		shapes = new Shape[7];
		colours = new HashMap<>();
		colours.put(1, Color.CYAN);
		colours.put(2, Color.green);
		colours.put(3, Color.red);
		colours.put(4, Color.ORANGE);
		colours.put(5, Color.GREEN);
		colours.put(6, Color.YELLOW);
		colours.put(7, Color.GRAY);
		
		shapes[0] = new Shape(new int[][] {
			{1,1,1,1}
		}, colours.get(1), 1, this);
		
		shapes[1] = new Shape(new int[][] {
			{1,1,0},
			{0,1,1}
		}, colours.get(2), 2, this);
		
		shapes[2] = new Shape(new int[][] {
			{0,1,0},
			{1,1,1}
		}, colours.get(3), 3, this);
		
		shapes[3] = new Shape(new int[][] {
			{1,1},
			{1,1}
		}, colours.get(4), 4, this);
		
		shapes[4] = new Shape(new int[][] {
			{0,1,1},
			{1,1,0}
		}, colours.get(5), 5, this);
		
		shapes[5] = new Shape(new int[][] {
			{0,0,1},
			{1,1,1}
		}, colours.get(6), 6, this);
		
		shapes[6] = new Shape(new int[][] {
			{1,0,0},
			{1,1,1}
		}, colours.get(6), 6, this);
		
		timer = new Timer(1000/60, new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				update();
				repaint();
			}
		});
		
		timer.start();
		
		random = new Random();
		currShape = new Shape(shapes[random.nextInt(7)]);
		score = 0;
		
	}
	
	@Override 
	public void paintComponent(Graphics graphics) {
				
		super.paintComponent(graphics);
		currShape.render(graphics);
		
		// draw diagram
		
		if(diagram == 1){
		for(int i=0; i<sizeX; i++) 
			graphics.drawLine(0, i*boxSize, board[i].length*boxSize, i*boxSize);
			
		for(int i=0; i<sizeY; i++)
			graphics.drawLine(i*boxSize, 0, i*boxSize, board.length*boxSize);
		}
		
		
		for(int i=0; i<sizeX; i++)
			for(int j=0; j<sizeY; j++)
				if(board[i][j] != 0){
					graphics.setColor(colours.get(board[i][j]));
					graphics.fillRect(j*boxSize, i*boxSize, boxSize, boxSize);
				}
		
		graphics.setColor(Color.red);
		graphics.setFont(new Font("Courier", Font.BOLD,50));
		graphics.drawString("Score: " + score, 10, 50);
		graphics.setColor(Color.DARK_GRAY);
	
	}
	
	public int getBoxSize() {
		return boxSize;
	}
	
	private void update() {
		currShape.update();
	}
	
	public void newShape() {
		currShape = new Shape(shapes[random.nextInt(7)]);
		//currShape = new Shape(shapes[3]);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			currShape.setOffset(-1);
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			currShape.setOffset(1);
		
		if(e.getKeyCode() == KeyEvent.VK_UP)
			currShape.setRotate(true);
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			currShape.setSpeed(80);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			currShape.setSpeed(450);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}


	public void gameOver() {
		System.exit(0);
	}
	public void clearRows() {
		
		//System.out.println("clearRows");
		
		for(int row = 0; row < sizeX - 1; row++) {
			boolean full = true;
			for(int col = 1; col < sizeY - 1; col++) 
				if(board[row][col] == 0) {
					full = false;
			}
			if(full) {
				score += 100;
				pushDown(row);
			}
		}
	}


	private void pushDown(int row) {
		for(; row>0; row--) {
			board[row] = board[row-1];
		}
		
	}
	
}
