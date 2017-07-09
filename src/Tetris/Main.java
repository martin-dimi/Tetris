package Tetris;

import javax.swing.JFrame;

public class Main {

	public static void main (String[] Args) {
		
		JFrame window = new JFrame();
		Tetris tetris = new Tetris();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(12*tetris.getBoxSize() + 15, 18*tetris.getBoxSize() + tetris.getBoxSize()/2);
		
		window.add(tetris);
		window.addKeyListener(tetris);	
		window.setVisible(true);

	}
	
}
