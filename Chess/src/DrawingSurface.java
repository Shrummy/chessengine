


import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;

import processing.core.PApplet;



public class DrawingSurface extends PApplet {
	private Grid board;
	private int runCount;
	private int speed;
	private Point prevToggle;
	private int temp1,temp2;
	
	
	public DrawingSurface() {
		board = new Grid();
		runCount = -1;
		speed = 120;
		prevToggle = null;
	}
	
	// The statements in the setup() function 
	// execute once when the program begins
	public void setup() {
		board = new Grid("data/initial.txt",this);
		board.move("e4");
		board.move("e5");
		board.move("d4");
		board.move("d5");
		//size(0,0,PApplet.P3D);
	}
	
	// The statements in draw() are executed until the 
	// program is stopped. Each statement is executed in 
	// sequence and after the last line is read, the first 
	// line is executed again.
	public void draw() { 
		image(loadImage("lib/board.png"), 0, 0,Math.min(width*7f/8,height),Math.min(width*7f/8,height));
		fill(255);
		textAlign(CENTER);
		textSize(12);
			//board.step();
			runCount = speed;
		
		if (board != null) {
			float dimension = Math.min(width,height);
			board.draw(this, 0, 0, dimension, dimension);
		}
		
	}
	
	
	public void mousePressed() {
		if (mouseButton == LEFT) {
			Point click = new Point(mouseX,mouseY);
			float dimension = Math.min(width,height);
			Point cellCoord = board.clickToIndex(click,0,0,dimension,dimension);
			if (cellCoord != null) {
				board.toggleCell(cellCoord.x, cellCoord.y,board.isWhite());
				prevToggle = cellCoord;
			}
		} 
	}
	
	
	public void mouseDragged() {
		if (mouseButton == LEFT) {
			Point click = new Point(mouseX,mouseY);
			float dimension = Math.min(width,height);
			Point cellCoord = board.clickToIndex(click,0,0,dimension,dimension);
			if (cellCoord != null && !cellCoord.equals(prevToggle)) 
			{	
				board.toggleCell(cellCoord.x, cellCoord.y,board.isWhite());
			}
		} 
	}
	
	
	public void keyPressed() {
		if (keyCode == KeyEvent.VK_SPACE) {
			if (runCount >= 0)
				runCount = -1;
			else
				runCount = 0;
		} else if (keyCode == KeyEvent.VK_DOWN) {
			speed = Math.min(300, speed*2);
			runCount = Math.max(runCount, speed);
		} else if (keyCode == KeyEvent.VK_UP) {
			speed = Math.max(15, speed/2);
			runCount = Math.min(runCount, speed);
		} else if (keyCode == KeyEvent.VK_ENTER) {
			//board.step();
		}
	}

	
}
