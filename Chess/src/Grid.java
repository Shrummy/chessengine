import java.awt.Point;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import processing.core.PApplet;

public class Grid {
	private char[][] board;
	private boolean pickFirstCell;
	private boolean isWhite;
	private int over;
	private int temp1, temp2;
	public Grid(){
		board=new char[8][8];
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				board[i][j]='_';
			}
		}
		isWhite=true;
		over=0;
	}
	public Grid(String filename){
		board=new char[8][8];
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				board[i][j]='_';
			}
		}
		readData(filename,board);
		isWhite=true;
		over=0;
	}
	
	public void readData (String filename, char[][] gameData) {
		File dataFile = new File(filename);

		if (dataFile.exists()) {
			int count = 0;

			FileReader reader = null;
			Scanner in = null;
			try {
					reader = new FileReader(dataFile);
					in = new Scanner(reader);

					while (in.hasNext()) {
						String line = in.nextLine();
						for(int i = 0; i < line.length(); i++)
						{
							if (count < gameData.length && i < gameData[count].length)
							{	
								boolean b = line.charAt(i)=='_';
								b=b||line.charAt(i)=='P'||line.charAt(i)=='p';
								b=b||line.charAt(i)=='N'||line.charAt(i)=='n';
								b=b||line.charAt(i)=='B'||line.charAt(i)=='b';
								b=b||line.charAt(i)=='R'||line.charAt(i)=='r';
								b=b||line.charAt(i)=='Q'||line.charAt(i)=='q';
								b=b||line.charAt(i)=='K'||line.charAt(i)=='k';
								
								
								if(b)
								{
									gameData[count][i]=line.charAt(i);
								}
							}
						}
						count++;
					}
			} catch (IOException ex) {
				throw new IllegalArgumentException("Data file " + filename + " cannot be read.");
			} finally {
				if (in != null)
					in.close();
			}
			
		} else {
			throw new IllegalArgumentException("Data file " + filename + " does not exist.");
		}
    }
	
	public void draw(PApplet marker, float x, float y, float width, float height) 
	{	
		float cellWidth=(float)width/(board[0].length);
		float cellHeight=(float)height/board.length;
		
		marker.stroke(0);
		
		for(int row=0;row<board.length;row++)
		{
			for(int col=0;col<board[0].length;col++)
			{
				if((row+col)%2==0)
					marker.fill(0);
				else
					marker.fill(240,0,0);
       			marker.rect(x+col*cellWidth,y+row*cellHeight,cellWidth,cellHeight);
       			if(!pickFirstCell&&temp1==row&&temp2==col&&(board[row][col]=='P'||board[row][col]=='p'))
       			{
       				marker.noStroke();
       				marker.fill(255,255,0);
       				marker.ellipse(x+(col+0.5f)*cellWidth,y+(row+0.5f)*cellHeight,0.9f*cellWidth,0.9f*cellHeight);
       				marker.stroke(0);
       			}
       			
				if(board[row][col]!='_')
				{
					marker.fill(255);
					marker.textSize(cellWidth);
					marker.text(board[row][col],x+col*(cellWidth+1),y+row*(cellWidth+1)+10);
				}
			}
		}	
	}
	public Point clickToIndex(Point p, float x, float y, float width, float height) 
	{
		float cellWidth=(float)width/(board[0].length);
		float cellHeight=(float)height/board.length;
		
		int j = (int)((p.getX()-x)/cellWidth);
		int k = (int)((p.getY()-y)/cellHeight);
		if(0<=k && k<board.length && 0<=j && j<board[0].length)
			return new Point(k,j);
		return null;
	}
	
	public void toggleCell(int i, int j, boolean isWhiteturn) 
	{
		if(pickFirstCell&&board[i][j]!='_')
		{
			temp1=i;
			temp2=j;
			pickFirstCell=false;
		}
		else if(pickFirstCell&&board[i][j]=='_');
		/*else if(isWhiteturn)
		{
			if((count[temp1][temp2]==1||count[temp1][temp2]==3)&&(temp1-2==i)&&temp2+2==j)
			{
				grid[temp1][temp2]='_';
				grid[temp1-1][temp2+1]='_';
				grid[i][j]='P';
				pickFirstCell=true;
				whiteToPlay=false;
				whiteMustCapture=false;
			}
			else if((count[temp1][temp2]==2||count[temp1][temp2]==3)&&temp1-2==i&&temp2-2==j)
			{
				grid[temp1][temp2]='_';
				grid[temp1-1][temp2-1]='_';
				grid[i][j]='P';
				pickFirstCell=true;
				whiteToPlay=false;
				whiteMustCapture=false;
			}
			else if((count[temp1][temp2]==5||count[temp1][temp2]==7)&&temp1-1==i&&temp2+1==j&&!whiteMustCapture)
			{

				grid[temp1][temp2]='_';
				grid[i][j]='P';
				pickFirstCell=true;
				whiteToPlay=false;
			}
			else if((count[temp1][temp2]==6||count[temp1][temp2]==7)&&temp1-1==i&&temp2-1==j&&!whiteMustCapture)
			{

				grid[temp1][temp2]='_';
				grid[i][j]='P';
				pickFirstCell=true;
				whiteToPlay=false;
			}
			else
			{
				pickFirstCell=true;	
			}
		}	
		else if(!isWhiteturn)
		{
			if((count[temp1][temp2]==1||count[temp1][temp2]==3)&&(temp1+2==i)&&temp2+2==j)
			{
				grid[temp1][temp2]='_';
				grid[temp1+1][temp2+1]='_';
				grid[i][j]='p';
				pickFirstCell=true;
				whiteToPlay=true;
				blackMustCapture=false;
			}
			else if((count[temp1][temp2]==2||count[temp1][temp2]==3)&&temp1+2==i&&temp2-2==j)
			{
				grid[temp1][temp2]='_';
				grid[temp1+1][temp2-1]='_';
				grid[i][j]='p';
				pickFirstCell=true;
				whiteToPlay=true;
				blackMustCapture=false;
			}
			else if((count[temp1][temp2]==5||count[temp1][temp2]==7)&&temp1+1==i&&temp2+1==j&&!blackMustCapture)
			{

				board[temp1][temp2]='_';
				board[i][j]='p';
				pickFirstCell=true;
			}
			else if((count[temp1][temp2]==6||count[temp1][temp2]==7)&&temp1+1==i&&temp2-1==j&&!blackMustCapture)
			{

				board[temp1][temp2]='_';
				board[i][j]='p';
				pickFirstCell=true;
			}
			else
			{
				pickFirstCell=true;	
			}
		}*/
	}
	
	
	
	
	
	
	
	
	public char getPos(int row,int col)
	{
		return board[row][col];
	}
	
	public void step()
	{
		//Scanner kboard = new Scanner(System.in);
		//int x = kboard.nextInt();
		//int y = kboard.nextInt();
	    isWhite=!isWhite;
	}
	
	
	
	
	public String toString() {
		String s="";
		for(int row=0;row<board.length;row++)
		{
			for(int col=0;col<board[0].length;col++)
			{
				s+=board[row][col];
			}
			s+="\n";
		}	
		return s;
	}
	public boolean getPickFirstCell() {
		return pickFirstCell;
	}
	public boolean isWhite() {
		return isWhite;
	}
	
}
