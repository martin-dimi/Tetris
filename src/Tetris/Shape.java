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
		this.coordinates = shape.coordinates;
		this.color = shape.color;
		this.id = shape.id;
		this.tetris = shape.tetris;
		
		x = 5;
		y = 0;
		time = 0;
		timeOffset = System.currentTimeMillis();
		collide = false;
		offset = 0;
		rotate = false;
		speedUp = 450;
	}
	
	public void update() {
		
		time += System.currentTimeMillis() - timeOffset;
		timeOffset = System.currentTimeMillis();
		
		if(collide)
			fixToBoard();
		
		if(rotate){
			coordinates = transposeMatrix(coordinates);
			rotate = false;
		}
		
		if(!(x + offset == 0 || x + offset == Tetris.sizeY - coordinates[0].length))
			x += offset;
		
		speed = speedUp;
		if(time > speed) {
			if(y+1 != Tetris.sizeX-coordinates.length)
				y ++;
			else 
				collide = true;
			time = 0;
		}
		
		for(int row = 0; row<coordinates.length; row++)
			for(int col=0; col<coordinates[row].length; col++) {
				if(coordinates[row][col] != 0 &&
					tetris.board[row + y + 1][col + x] != 0) 
					collide = true;	
			}
		
		offset = 0;
	}
	
	public void fixToBoard() {
		for(int row = 0; row<coordinates.length; row++)
			for(int col=0; col<coordinates[row].length; col++) 
					if(coordinates[row][col] != 0) {
						if(row + y == 1) tetris.gameOver();
						tetris.board[row + y][col+x] = id;
						tetris.newShape();
					//	System.out.println("Fixed well");
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
					//System.out.println(col*tetris.getBoxSize() + x*tetris.getBoxSize());
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

