package Tetris;

import java.awt.Color;
import java.awt.Graphics;

public class Shape {
	
	public int[][] coordinates;
	public Color color;
	public int id;
	public Tetris tetris;
	private int x,y;
	private int offset;
	private boolean rotate;
	private long time, timeOffset;
	private boolean collide;
	private int speed = 450;
	private int speedUp;

	
	Shape(int[][] coordinates, Color color, int id, Tetris tetris){
		this.coordinates = coordinates;
		this.color = color;
		this.id = id;
		this.tetris = tetris;
		
		x = 5;
		y = 0;
		time = 0;
		timeOffset = System.currentTimeMillis();
		collide = false;
		offset = 0;
		rotate = false;
		speedUp = 450;
	}
	
	Shape(Shape shape){
		this(shape.coordinates, shape.color, shape.id, shape.tetris);
	}
	
	public void update() {

		if(collide){
			fixToBoard();
			return;
		}
		
		rotate();
		moveX();
		moveY();
		checkCollide();
	}
	
	private void moveY(){
		time += System.currentTimeMillis() - timeOffset;
		timeOffset = System.currentTimeMillis();
		speed = speedUp;
		
		if(time > speed) {
			if(y+1 != Tetris.sizeY-coordinates.length)
				y ++;
			else 
				collide = true;
			
			time = 0;
		}
	}

	private void moveX(){		// should I get a new reference to getBoard instead of having multiple ones?

		if(tetris.getBoard()[y][x + offset] == 0 && tetris.getBoard()[y][x + coordinates[0].length + offset - 1] == 0){		
			for(int row = 0; row < coordinates.length; row++)
				for(int col = 0; col < coordinates[0].length; col++)
					if(coordinates[row][col] == 1)
						if(tetris.getBoard()[y + row][x + col + offset] != 0)
							return;	
			x += offset;
		}
		
		offset = 0;
	}
	
	private void rotate(){
		if(rotate){
			if(x + transposeMatrix(coordinates)[0].length < Tetris.sizeX && y + transposeMatrix(coordinates).length < Tetris.sizeY)
				coordinates = transposeMatrix(coordinates);
			rotate = false;
		}
	}
	
	private void checkCollide(){
		for(int row = 0; row<coordinates.length; row++)
			for(int col=0; col<coordinates[row].length; col++) {
				if(coordinates[row][col] != 0 &&
					tetris.getBoard()[row + y + 1][col + x] != 0) 
					collide = true;	
			}
	}
	
	public void fixToBoard() {
		for(int row = 0; row<coordinates.length; row++)
			for(int col=0; col<coordinates[row].length; col++) 
					if(coordinates[row][col] != 0) {
						if(row + y == 1) tetris.gameOver();
						tetris.getBoard()[row + y][col+x] = id;
						tetris.newShape();
			}		
			
		tetris.clearRows();
	}
	
	public void render(Graphics graphics) {
		
		for(int row = 0; row<coordinates.length; row++)
			for(int col=0; col<coordinates[row].length; col++) 
				if(coordinates[row][col] != 0) {
					graphics.setColor(color);
					graphics.fillRect(col*tetris.getBoxSize() + x*tetris.getBoxSize(), row*tetris.getBoxSize() + y*tetris.getBoxSize(),
										tetris.getBoxSize(), tetris.getBoxSize());
					graphics.setColor(Color.BLACK);
				}
	}
	
	private int[][] transposeMatrix(int [][] matrix){
        int[][] temp = new int[matrix[0].length][matrix.length];
        for (int row = 0; row < matrix.length; row++)
            for (int col = 0; col < matrix[0].length; col++)
                temp[col][matrix.length - row - 1] = matrix[row][col];
        return temp;
    }

	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public void setRotate(boolean rotate){
		this.rotate = rotate;
	}
	public void setSpeed(int speed){
		this.speedUp = speed;
	}
	
}

