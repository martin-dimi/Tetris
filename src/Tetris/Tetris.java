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
	public static final int sizeX = 16, sizeY = 22;	// kude da initialise-vam
	private int[][] board;
	private final int boxSize = 30;
	private final Shape[] shapes;
	private final Map<Integer, Color> colours;
	//private final Timer timer;
	private Shape currShape;

	private final Random random;
	private int score = 0;
	
	Tetris(){
			
		board = new int[sizeY][sizeX];
		for(int row = 0; row<sizeY; row++)
			for(int col = 0; col<sizeX; col++)
				board[row][col] = (col == 0 || col == sizeX-1 || row == sizeY-1) ? 7 : 0;
		
		colours = new HashMap<>();
		colours.put(1, Color.CYAN);
		colours.put(2, Color.green);
		colours.put(3, Color.red);
		colours.put(4, Color.ORANGE);
		colours.put(5, Color.GREEN);
		colours.put(6, Color.YELLOW);
		colours.put(7, Color.GRAY);
		
		shapes = new Shape[7];
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
		
		/*
		timer = new Timer(1000/60, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currShape.update();
				repaint();
			}
		});
		timer.start();
		*/
		
		random = new Random();
		currShape = new Shape(shapes[random.nextInt(7)]);

	}
	
	public void run(){
		while(true){
			currShape.update();
			repaint();
		}
	}
	
	@Override 
	public void paintComponent(Graphics graphics) {
				
		super.paintComponent(graphics);
		currShape.render(graphics);
		
		// draw diagram	
		/*
		for(int i=0; i<sizeX; i++) 
			graphics.drawLine(0, i*boxSize, board[i].length*boxSize, i*boxSize);
			
		for(int i=0; i<sizeY; i++)
			graphics.drawLine(i*boxSize, 0, i*boxSize, board.length*boxSize);
		*/
		
		for(int i=0; i<sizeY; i++)
			for(int j=0; j<sizeX; j++)
				if(board[i][j] != 0){
					graphics.setColor(colours.get(board[i][j]));
					graphics.fillRect(j*boxSize, i*boxSize, boxSize, boxSize);
				}
		
		graphics.setColor(Color.red);
		graphics.setFont(new Font("Courier", Font.BOLD,50));
		graphics.drawString("Score: " + score, 10, 50);
		graphics.setColor(Color.DARK_GRAY);
	
	}
	
	public void newShape() {
		currShape = new Shape(shapes[random.nextInt(7)]);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_A)
			currShape.setOffset(-1);

		
		if(e.getKeyCode() == KeyEvent.VK_D)
			currShape.setOffset(1);
		
		if(e.getKeyCode() == KeyEvent.VK_W)
			currShape.setRotate(true);
		
		if(e.getKeyCode() == KeyEvent.VK_S)
			currShape.setSpeed(80);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_S)
			currShape.setSpeed(450);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	public void gameOver() {
		System.exit(0);
	}
	
	public void clearRows() {
		
		for(int row = 0; row < sizeY - 1; row++) {
			boolean full = true;
			
			for(int col = 1; col < sizeX - 1; col++) 
				if(board[row][col] == 0)
					full = false;
			
			if(full) {
				score += 100;
				pushDown(row);
			}
		}
	}

	private void pushDown(int row) {
		for(; row>0; row--)
			board[row] = board[row-1];
	}
		
	public int getBoxSize() {
		return boxSize;
	}

	public int[][] getBoard(){
		return board;
	}
}
