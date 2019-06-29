import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import processing.core.PApplet;
import processing.core.PImage;

public class Grid {
	private PImage[] images;
	private char[][] board;
	private boolean pickFirstCell;
	private boolean isWhite;
	private ArrayList<Integer> markedMoves;
	private ArrayList<Integer> attackedSquares;
	private int eps=-1;
	private boolean epsTrue=false;
	private int over;
	private int this1, this2;
	private int temp1, temp2;
	public Grid(){
		images=new PImage[12];
		markedMoves=new ArrayList<Integer>();
		attackedSquares=new ArrayList<Integer>();
		pickFirstCell=false;
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
	public Grid(String filename, PApplet drawer){
		images=new PImage[12];
		
		images[0]=drawer.loadImage("lib/pieces/black/blackpawn.png");
		images[1]=drawer.loadImage("lib/pieces/black/blackknight.png");
		images[2]=drawer.loadImage("lib/pieces/black/blackbishop.png");
		images[3]=drawer.loadImage("lib/pieces/black/blackrook.png");
		images[4]=drawer.loadImage("lib/pieces/black/blackqueen.png");
		images[5]=drawer.loadImage("lib/pieces/black/blackking.png");
		
		images[6]=drawer.loadImage("lib/pieces/white/whitepawn.png");
		images[7]=drawer.loadImage("lib/pieces/white/whiteknight.png");
		images[8]=drawer.loadImage("lib/pieces/white/whitebishop.png");
		images[9]=drawer.loadImage("lib/pieces/white/whiterook.png");
		images[10]=drawer.loadImage("lib/pieces/white/whitequeen.png");
		images[11]=drawer.loadImage("lib/pieces/white/whiteking.png");
		
		
		
		attackedSquares=new ArrayList<Integer>();
		markedMoves=new ArrayList<Integer>();
		pickFirstCell=true;
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
		float cellWidth=85;
		float cellHeight=85;
		marker.stroke(0);
		
		
		
		
		
		
		for(int row=0;row<board.length;row++)
		{
			for(int col=0;col<board[0].length;col++)
			{
				
       			if(pickFirstCell&&row==temp1&&col==temp2&&isWhite==Character.isUpperCase(board[row][col]))
       			{
       				this1=temp1;
       				this2=temp2;

       			}
       			
       			if(board[row][col]!='_')
				{
       				if(Character.isUpperCase(board[row][col]))
       					marker.fill(255);
       				else
       					marker.fill(0);
					
       				float xt=x+(col+0.5f)*cellWidth-32-10;
       				float yt=y+(row+0.5f)*cellHeight-32-10;
       				
					if(board[row][col]=='p')
						marker.image(images[0], xt, yt, cellWidth,cellHeight);
					else if(board[row][col]=='n')
						marker.image(images[1], xt, yt,cellWidth,cellHeight);
					else if(board[row][col]=='b')
						marker.image(images[2], xt, yt,cellWidth,cellHeight);
					else if(board[row][col]=='r')
						marker.image(images[3], xt, yt,cellWidth,cellHeight);
					else if(board[row][col]=='q')
						marker.image(images[4],xt,yt,cellWidth,cellHeight);
					else if(board[row][col]=='k')
						marker.image(images[5], xt, yt,cellWidth,cellHeight);
					else if(board[row][col]=='P')
						marker.image(images[6], xt, yt,cellWidth,cellHeight);
					else if(board[row][col]=='N')
						marker.image(images[7], xt, yt,cellWidth,cellHeight);
					else if(board[row][col]=='B')
						marker.image(images[8], xt, yt,cellWidth,cellHeight);
					else if(board[row][col]=='R')
						marker.image(images[9], xt, yt,cellWidth,cellHeight);
					else if(board[row][col]=='Q')
						marker.image(images[10], xt, yt,cellWidth,cellHeight);
					else if(board[row][col]=='K')
						marker.image(images[11], xt, yt,cellWidth,cellHeight);
				}
			
			}
			for(int i=0;i<markedMoves.size()-1;i++)
			{
				if(i%2==0)
				{
					int yc = markedMoves.get(i+1);
					int xc = markedMoves.get(i);
					marker.fill(240,230,140);
					marker.ellipseMode(marker.CENTER);
					marker.ellipse(x+(yc+0.5f)*cellWidth,y+(xc+0.5f)*cellHeight,cellWidth/5f,cellHeight/5f);
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
		{
			for(int t=0;t<markedMoves.size()-1;t++)
			{
				if(t%2==0)
				{
					
					if(k==markedMoves.get(t)&&j==markedMoves.get(t+1))
					{
						board[k][j]=board[this1][this2];
						board[this1][this2]='_';
						eps=-1;
						
						if(isWhite)
						{
							if(this1==6&&k==4&&board[k][j]=='P')
							{
								eps=j;
							}
							if(epsTrue)
							{
								board[k+1][j]='_';
							}
						}
						else
						{
							if(this1==1&&k==3&&board[k][j]=='p')
							{
								eps=j;
							}
							if(epsTrue)
							{
								board[k-1][j]='_';
							}
						}
						epsTrue=false;
						isWhite=!isWhite;
					}
				}
			}
			markedMoves=new ArrayList<Integer>();
			pickFirstCell=true;
			return new Point(k,j);
		}
		return null;
	}
	
	
	public ArrayList<String> getPawnMoves(boolean isWhite, int i, int j)
	{
		ArrayList<String> s=new ArrayList<String>();
		//first move and white
		if(i==6&&isWhite)
		{
			if(board[5][j]=='_')
			{
				if(board[4][j]=='_')
					s.add("4"+j);
			}
		}
		//first move and black
		else if(i==1&&!isWhite)
		{
			if(board[2][j]=='_')
			{
				if(board[3][j]=='_')
					s.add("3"+j);
			}
		}
		
		//regular move
		if(i>0&&board[i-1][j]=='_'&&isWhite)
		{
			s.add((i-1)+""+j);
		}
		else if(i<7&&board[i+1][j]=='_'&&!isWhite)
		{
			s.add((i+1)+""+j);
		}
		
		//captures left
		if(j>0&& i>0&& board[i-1][j-1]!='_'&&(Character.isUpperCase(board[i-1][j-1])!=isWhite)&&isWhite)
		{
			s.add((i-1)+""+(j-1));
		}
		else if(j>0&& i<7&& board[i+1][j-1]!='_'&&(Character.isUpperCase(board[i+1][j-1])!=isWhite)&&!isWhite)
		{
			s.add((i+1)+""+(j-1));
		}
		
		//captures right
		if(j<7&& i>0 && board[i-1][j+1]!='_'&&(Character.isUpperCase(board[i-1][j+1])!=isWhite)&&isWhite)
		{
			s.add((i-1)+""+(j+1));
		}
		else if(j<7&& i<7 && board[i+1][j+1]!='_'&&(Character.isUpperCase(board[i+1][j+1])!=isWhite)&&!isWhite)
		{
			s.add((i+1)+""+(j+1));
		}
		
		//en passant left
		
		if(i==3&&j>0&& board[2][j-1]=='_'&&eps==j-1&&isWhite)
		{
			s.add((i-1)+""+(j-1));
			epsTrue=true;
		}
		else if(i==4&&j>0&& board[5][j-1]=='_'&&eps==j-1&&!isWhite)
		{
			s.add((i+1)+""+(j-1));
			epsTrue=true;
		}
		
		
		//en passant right
		
		if(i==3&&j<7&& board[2][j+1]=='_'&&eps==j+1&&isWhite)
		{
			s.add((i-1)+""+(j+1));
			epsTrue=true;
		}
		else if(i==4&&j<7&& board[5][j+1]=='_'&&eps==j+1&&!isWhite)
			{
				s.add((i+1)+""+(j+1));
				epsTrue=true;
			}
		
		
		
		return s;	
	}
	
	
	public ArrayList<String> getKnightMoves(boolean isWhite,int i,int j)
	{	
		ArrayList<String> s=new ArrayList<String>();
		if(i<6&&j<7&&(board[i+2][j+1]=='_'||Character.isUpperCase(board[i+2][j+1])!=isWhite))
			s.add((i+2)+""+(j+1));
		
		if(i<6&&j>0&&(board[i+2][j-1]=='_'||Character.isUpperCase(board[i+2][j-1])!=isWhite))
			s.add((i+2)+""+(j-1));

		if(i<7&&j<6&&(board[i+1][j+2]=='_'||Character.isUpperCase(board[i+1][j+2])!=isWhite))
			s.add((i+1)+""+(j+2));
		
		if(i<7&&j>1&&(board[i+1][j-2]=='_'||Character.isUpperCase(board[i+1][j-2])!=isWhite))
			s.add((i+1)+""+(j-2));
		
		if(i>1&&j<7&&(board[i-2][j+1]=='_'||Character.isUpperCase(board[i-2][j+1])!=isWhite))
			s.add((i-2)+""+(j+1));
	
		if(i>1&&j>0&&(board[i-2][j-1]=='_'||Character.isUpperCase(board[i-2][j-1])!=isWhite))
			s.add((i-2)+""+(j-1));
	
		if(i>0&&j<6&&(board[i-1][j+2]=='_'||Character.isUpperCase(board[i-1][j+2])!=isWhite))
			s.add((i-1)+""+(j+2));

		if(i>0&&j>1&&(board[i-1][j-2]=='_'||Character.isUpperCase(board[i-1][j-2])!=isWhite))
			s.add((i-1)+""+(j-2));

		return s;
	}
	
	public ArrayList<String> getRookMoves(boolean isWhite,int i,int j)
	{	
		ArrayList<String> s=new ArrayList<String>();
		int a=i;
		int b=j;
		boolean stop=false;
		while(a>0&&!stop)
		{
			a--;
			if(board[a][b]=='_')
				s.add(a+""+b);
			else if(Character.isUpperCase(board[a][b])!=isWhite)
			{
				s.add(a+""+b);
				stop=true;
			}
			else
				stop=true;
		}
		stop=false;
		a=i;
		while(a<7&&!stop)
		{
			a++;
			if(board[a][b]=='_')
				s.add(a+""+b);
			else if(Character.isUpperCase(board[a][b])!=isWhite)
			{
				s.add(a+""+b);
				stop=true;
			}
			else
				stop=true;
		}
		stop=false;
		a=i;
		while(b>0&&!stop)
		{
			b--;
			if(board[a][b]=='_')
				s.add(a+""+b);
			else if(Character.isUpperCase(board[a][b])!=isWhite)
			{
				s.add(a+""+b);
				stop=true;
			}
			else
				stop=true;
		}
		stop=false;
		b=j;
		while(b<7&&!stop)
		{
			b++;
			if(board[a][b]=='_')
				s.add(a+""+b);
			else if(Character.isUpperCase(board[a][b])!=isWhite)
			{
				s.add(a+""+b);
				stop=true;
			}
			else
				stop=true;
		}
		

		
		return s;
	}
	
	public ArrayList<String> getBishopMoves(boolean isWhite,int i,int j)
	{
		ArrayList<String> s=new ArrayList<String>();
		int a=i;
		int b=j;
		boolean stop=false;
		while(a<7&&b>0&&!stop)
		{
			a++;
			b--;
			if(board[a][b]=='_')
				s.add(a+""+b);
			else if(Character.isUpperCase(board[a][b])!=isWhite)
			{
				s.add(a+""+b);
				stop=true;
			}
			else
				stop=true;
		}
		a=i;
		b=j;
		stop=false;
		while(a<7&&b<7&&!stop)
		{
			a++;
			b++;
			if(board[a][b]=='_')
				s.add(a+""+b);
			else if(Character.isUpperCase(board[a][b])!=isWhite)
			{
				s.add(a+""+b);
				stop=true;
			}
			else
				stop=true;
		}
		a=i;
		b=j;
		stop=false;
		while(a>0&&b>0&&!stop)
		{
			a--;
			b--;
			if(board[a][b]=='_')
				s.add(a+""+b);
			else if(Character.isUpperCase(board[a][b])!=isWhite)
			{
				s.add(a+""+b);
				stop=true;
			}
			else
				stop=true;
		}
		a=i;
		b=j;
		stop=false;
		while(a>0&&b<7&&!stop)
		{
			a--;
			b++;
			if(board[a][b]=='_')
				s.add(a+""+b);
			else if(Character.isUpperCase(board[a][b])!=isWhite)
			{
				s.add(a+""+b);
				stop=true;
			}
			else
				stop=true;
		}
		return s;
	}
	
	
	
	public ArrayList<String> getQueenMoves(boolean isWhite,int i,int j)
	{
		ArrayList<String> s = getBishopMoves(isWhite,i,j);
		s.addAll(getRookMoves(isWhite,i,j));
		return s;
	}
	public ArrayList<String> getKingMoves(boolean isWhite,int i,int j)
	{
		ArrayList<String> s = new ArrayList<String>();
		if(i>0&&j>0&&(board[i-1][j-1]=='_'||Character.isUpperCase(board[i-1][j-1])!=isWhite))
			s.add((i-1)+""+(j-1));
		
		if(i>0&&(board[i-1][j]=='_'||Character.isUpperCase(board[i-1][j])!=isWhite))
			s.add((i-1)+""+j);
		
		if(i>0&&j<7&&(board[i-1][j+1]=='_'||Character.isUpperCase(board[i-1][j+1])!=isWhite))
			s.add((i-1)+""+(j+1));
		
		
		if(i<7&&j>0&&(board[i+1][j-1]=='_'||Character.isUpperCase(board[i+1][j-1])!=isWhite))
			s.add((i+1)+""+(j-1));
		
		if(i<7&&(board[i+1][j]=='_'||Character.isUpperCase(board[i+1][j])!=isWhite))
			s.add((i+1)+""+j);
		
		if(i<7&&j<7&&(board[i+1][j+1]=='_'||Character.isUpperCase(board[i+1][j+1])!=isWhite))
			s.add((i+1)+""+(j+1));
		
		if(j>0&&(board[i][j-1]=='_'||Character.isUpperCase(board[i][j-1])!=isWhite))
			s.add(i+""+(j-1));
		
		if(j<7&&(board[i][j+1]=='_'||Character.isUpperCase(board[i][j+1])!=isWhite))
			s.add(i+""+(j+1));
		
		return s;
	}
	
	public void toggleCell(int i, int j, boolean isWhiteturn) 
	{
		temp1=i;
		temp2=j;
		if(pickFirstCell)
		{
			if(board[i][j]=='P'&&isWhite)
			{
				for(String s:getPawnMoves(true,temp1,temp2))
				{
					int n = Integer.parseInt(s);
					markedMoves.add(n/10);
					markedMoves.add(n%10);
				}
			}
			else if(board[i][j]=='N'&&isWhite)
			{
				for(String s:getKnightMoves(true,temp1,temp2))
				{
					int n = Integer.parseInt(s);
					markedMoves.add(n/10);
					markedMoves.add(n%10);
				}
			}
			else if(board[i][j]=='B'&&isWhite)
			{
				for(String s:getBishopMoves(true,temp1,temp2))
				{
					int n = Integer.parseInt(s);
					markedMoves.add(n/10);
					markedMoves.add(n%10);
				}
			}
			else if(board[i][j]=='R'&&isWhite)
			{
				for(String s:getRookMoves(true,temp1,temp2))
				{
					int n = Integer.parseInt(s);
					markedMoves.add(n/10);
					markedMoves.add(n%10);
				}
			}
			else if(board[i][j]=='Q'&&isWhite)
			{
				for(String s:getQueenMoves(true,temp1,temp2))
				{
					int n = Integer.parseInt(s);
					markedMoves.add(n/10);
					markedMoves.add(n%10);
				}
			}
			else if(board[i][j]=='K'&&isWhite)
			{
				for(String s:getKingMoves(true,temp1,temp2))
				{
					int n = Integer.parseInt(s);
					markedMoves.add(n/10);
					markedMoves.add(n%10);
				}
			}
			else if(board[i][j]=='p'&&!isWhite)
			{
				for(String s:getPawnMoves(false,temp1,temp2))
				{
					int n = Integer.parseInt(s);
					markedMoves.add(n/10);
					markedMoves.add(n%10);
				}
			}
			else if(board[i][j]=='n'&&!isWhite)
			{
				for(String s:getKnightMoves(false,temp1,temp2))
				{
					int n = Integer.parseInt(s);
					markedMoves.add(n/10);
					markedMoves.add(n%10);
				}
			}
			else if(board[i][j]=='b'&&!isWhite)
			{
				for(String s:getBishopMoves(false,temp1,temp2))
				{
					int n = Integer.parseInt(s);
					markedMoves.add(n/10);
					markedMoves.add(n%10);
				}
			}
			else if(board[i][j]=='r'&&!isWhite)
			{
				for(String s:getRookMoves(false,temp1,temp2))
				{
					int n = Integer.parseInt(s);
					markedMoves.add(n/10);
					markedMoves.add(n%10);
				}
			}
			else if(board[i][j]=='q'&&!isWhite)
			{
				for(String s:getQueenMoves(false,temp1,temp2))
				{
					int n = Integer.parseInt(s);
					markedMoves.add(n/10);
					markedMoves.add(n%10);
				}
			}
			else if(board[i][j]=='k'&&!isWhite)
			{
				for(String s:getKingMoves(false,temp1,temp2))
				{
					int n = Integer.parseInt(s);
					markedMoves.add(n/10);
					markedMoves.add(n%10);
				}
			}
			else
			{
		
				pickFirstCell=false;
				markedMoves=new ArrayList<Integer>();
			}
		}
		
		/*else
		{
			markedMoves=new ArrayList<Integer>();
			pickFirstCell=false;
		}*/
	
		
		/*
		if(!pickFirstCell&&board[i][j]!='_')
		{
			temp1=i;
			temp2=j;
			pickFirstCell=true;
			System.out.println("NANI");
		}
		else if(pickFirstCell&&board[i][j]=='_');
		{
			
		}
		else if(isWhiteturn)
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
