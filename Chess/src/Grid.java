
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Grid {
	private char[][] board;
	private boolean pickFirstCell;
	private boolean isWhite;
	private char promote;
	private ArrayList<Integer> markedMoves;
	private ArrayList<Integer> attackedSquares;
	
	private int eps=-1;
	private boolean epsTrue=false;
	private boolean WcastleK, WcastleQ;
	private boolean BcastleK, BcastleQ;
	private int over;
	private int this1, this2;
	private int temp1, temp2;
	public Grid(){
		markedMoves=new ArrayList<Integer>();
		attackedSquares=new ArrayList<Integer>();
		pickFirstCell=false;
		eps=-1;
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
	public Grid(String filename)
	{
		eps=-1;
		promote=' ';
		
		WcastleK=true;
		WcastleQ=true;
		BcastleK=true;
		BcastleQ=true;
		
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
	
	//PRINTS OUT YES initx inity laterx latery
	public String isLegalMove(String s)
	{
		if(s.charAt(s.length()-1)=='#'||s.charAt(s.length()-1)=='+')
			s=s.substring(0, s.length()-1);
		char c0 = s.charAt(0);
		char c1 = s.charAt(1);
		
		//pawn moves
		if(!Character.isUpperCase(c0))
		{
			if(c1!='x')
			{
				int x = ((int)c0-97);
				int y=8-Integer.parseInt(c1+"");
				
				String t = (10*y+x)+"";
				for(int row=0;row<8;row++)
				{
					if(board[row][x]=='P'&&isWhite)
						{
							for(String r:getPawnMoves(true,row,x))
							{
								
								if(r.equals(t))
									return "YES"+row+x+y+x;
							}
						}
						else if(board[row][x]=='p'&&!isWhite)
						{
							for(String r:getPawnMoves(false,row,x))
							{
								if(r.equals(t))
									return "YES"+row+x+y+x;
							}
						}
				}
			}
			//pawn captures
			else
			{
				char c2 = s.charAt(2);
				char c3 = s.charAt(3);
				char c4;
				
				if(s.charAt(s.length()-2)=='=')
				{
	 				promote=s.charAt(s.length()-1);
 				}
				
				
				int w = ((int)c2-97);
				int x = ((int)c0-97);
				int y=8-Integer.parseInt(c3+"");
				
				
				
				String t = (10*y+w)+"";
				
				
				
				
				for(int row=0;row<8;row++)
				{
					for(int col=0;col<8;col++)
					{
				
						if((board[row][col]+"").equalsIgnoreCase("P")&&Character.isUpperCase(board[row][col])==isWhite)
						{
							for(String r:getPawnMoves(isWhite,row,col))
							{
								if(r.equals(t)&&col==x)
								{
									return "YES"+row+col+y+w;
								}
							}
						}
					}
				}
	
				
			}
		}
		//anything else move
		else
		{
			
			char c2;
			char c3;
			int x; 
			int y;

			
			if(c1=='x')
			{
				c2=s.charAt(2);
				c3=s.charAt(3);
				x = ((int)c2-97);
			    y=8-Integer.parseInt(c3+"");
				
			}
			else
			{
				c2=s.charAt(2);
				x = ((int)c1-97);
				y=8-Integer.parseInt(c2+"");
			}
			
			String t = (10*y+x)+"";
			
			
			for(int row=0;row<8;row++)
			{
				for(int col=0;col<8;col++)
				{
			
					if((board[row][col]+"").equalsIgnoreCase(c0+"")&&Character.isUpperCase(board[row][col])==isWhite)
					{
						for(String r:getMoves(c0,isWhite,row,col))
						{
							if(r.equals(t))
								return "YES"+row+col+y+x;
						}
					}
				}
			}
		}

		return "NOPE";
	}
	
	public void move(String s)
	{
		String t = isLegalMove(s);
		if(t.substring(0, 3).equals("YES"))
		{
			//Normal Move
			int x = Integer.parseInt(t.substring(3,4));
			int y = Integer.parseInt(t.substring(4,5));
			int z = Integer.parseInt(t.substring(5,6));
			int w = Integer.parseInt(t.substring(6,7));
			board[z][w]=board[x][y];
			board[x][y]='_';
			eps=-1;
			if(isWhite)
			{
				if(x==6&&z==4&&board[z][w]=='P')
				{
					eps=w;
				}
				if(epsTrue)
				{
					board[z+1][w]='_';
				}
				if(promote!=' ')
				{
					board[z][w]=promote;
				}
			}
			else
			{
				if(x==1&&z==3&&board[z][w]=='p')
				{
					eps=w;
				}
				if(epsTrue)
				{
					board[z-1][w]='_';
					
				}
				if(promote!=' ')
				{
					board[z][w]=Character.toLowerCase(promote);
				}
			}
			epsTrue=false;
			promote=' ';
			isWhite=!isWhite;
		}
	}
	
	
	public void game(String s)
	{
		Scanner kboard = new Scanner(System.in);
		for(int i=1;s.indexOf(i+".")>-1;i++)
		{
			int a = s.indexOf(i+".");
			int b = s.indexOf((i+1)+".");
			if(b>-1)
			{
				String r = s.substring(a,b-1);
				if(i<10)
					r=r.substring(3);
				else if(i<100)
					r=r.substring(4);
				int c = r.indexOf(" ");
				if(c>-1)
				{
					move(r.substring(0,c));
					kboard.nextLine();
					System.out.println(this);
					
					move(r.substring(c+1));
					kboard.nextLine();
					System.out.println(this);
				}
				else
				{
					move(r);
					kboard.nextLine();
					System.out.println(this);
				}
			}
			else
			{
				String r = s.substring(a);
				if(i<10)
					r=r.substring(3);
				else if(i<100)
					r=r.substring(4);
				int c = r.indexOf(" ");
				if(c>-1)
				{
					move(r.substring(0,c));
					kboard.nextLine();
					System.out.println(this);
					
					move(r.substring(c+1));
					kboard.nextLine();
					System.out.println(this);
				}
				else
				{
					move(r);
					kboard.nextLine();
					System.out.println(this);
				}
			}
		}
		kboard.close();
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

	
	
	public ArrayList<String> getMoves(char c, boolean b, int i, int j)
	{
		if(!b)
		{
			c=Character.toLowerCase(c);
			
		}
		switch(c)
		{
		case 'P':{
			return getPawnMoves(isWhite,i,j);	
		}
		case 'N':{
			return getKnightMoves(isWhite,i,j);
		}
		case 'n':{
			return getKnightMoves(false,i,j);
		}
		case 'B':{
			return getBishopMoves(true,i,j);
		}
		case 'b':{
			return getBishopMoves(false,i,j);
		}
		case 'R':{
			return getRookMoves(true,i,j);
		}
		case 'r':{
			return getRookMoves(false,i,j);
		}
		case 'Q':{
			return getQueenMoves(true,i,j);
		}
		case 'q':{
			return getQueenMoves(false,i,j);
		}
		case 'K':{
			return getKingMoves(true,i,j);
		}
		case 'k':{
			return getKingMoves(false,i,j);
		}
		}
		return null;
	}
	
	
	public ArrayList<String> getPawnMoves(boolean isWhite, int i, int j)
	{
		ArrayList<String> s=new ArrayList<String>();
		if(isWhite)
		{
			//first two square move
			if(i==6)
			{
				if(board[5][j]=='_')
				{
					if(board[4][j]=='_')
						s.add("4"+j);
				}
			}
			//regular move (with promotion)
			if(i>0&&board[i-1][j]=='_')
			{
				if(i==1)
				{
					s.add((i-1)+""+j+"=Q");
					s.add((i-1)+""+j+"=R");
					s.add((i-1)+""+j+"=B");
					s.add((i-1)+""+j+"=N");
				}
				else
				{
					s.add((i-1)+""+j);
				}
			}
			//captures left (with promotion)
			if(j>0&& i>0&& board[i-1][j-1]!='_'&&!Character.isUpperCase(board[i-1][j-1]))
			{
				if(i==1)
				{
					s.add((i-1)+""+(j-1)+"=Q");
					s.add((i-1)+""+(j-1)+"=R");
					s.add((i-1)+""+(j-1)+"=B");
					s.add((i-1)+""+(j-1)+"=N");
				}
				else
				{
					s.add((i-1)+""+(j-1));
				}
			}
			//captures right (with promotion)
			if(j<7&& i>0 && board[i-1][j+1]!='_'&&!Character.isUpperCase(board[i-1][j+1]))
			{
				if(i==1)
				{
					s.add((i-1)+""+(j+1)+"=Q");
					s.add((i-1)+""+(j+1)+"=R");
					s.add((i-1)+""+(j+1)+"=B");
					s.add((i-1)+""+(j+1)+"=N");
				}
				else
				{
					s.add((i-1)+""+(j+1));
				}
			}
			//en passant left
			if(i==3&&j>0&& board[2][j-1]=='_'&&eps==j-1)
			{
				s.add((i-1)+""+(j-1));
				epsTrue=true;
			}
			//en passant right
			
			if(i==3&&j<7&& board[2][j+1]=='_'&&eps==j+1)
			{
				s.add((i-1)+""+(j+1));
				epsTrue=true;
			}
			
		}
		else
		{
			//first two square move
			if(i==1)
			{
				if(board[2][j]=='_')
				{
					if(board[3][j]=='_')
						s.add("3"+j);
				}
			}
			//regular move (with promotion)
			if(i<7&&board[i+1][j]=='_')
			{
				if(i==6)
				{
					s.add((i+1)+""+j+"=Q");
					s.add((i+1)+""+j+"=R");
					s.add((i+1)+""+j+"=B");
					s.add((i+1)+""+j+"=B");
				}
				else
				{
					s.add((i+1)+""+j);
				}
			}
			//captures left
			if(j>0&& i<7&& board[i+1][j-1]!='_'&&Character.isUpperCase(board[i+1][j-1]))
			{
				if(i==6)
				{
					s.add((i+1)+""+(j-1)+"=Q");
					s.add((i+1)+""+(j-1)+"=R");
					s.add((i+1)+""+(j-1)+"=B");
					s.add((i+1)+""+(j-1)+"=B");
				}
				else
				{
					s.add((i+1)+""+(j-1));
				}
			}
			//captures right
			if(j<7&& i>0 && board[i-1][j+1]!='_'&&Character.isUpperCase(board[i-1][j+1]))
			{
				if(i==6)
				{
					s.add((i+1)+""+(j+1)+"=Q");
					s.add((i+1)+""+(j+1)+"=R");
					s.add((i+1)+""+(j+1)+"=B");
					s.add((i+1)+""+(j+1)+"=B");
				}
				else
				{
					s.add((i+1)+""+(j+1));
				}
			}
			//en passant left
			if(j<7&& i==4 && board[i+1][j+1]!='_'&&Character.isUpperCase(board[i+1][j+1]))
			{
				s.add((i+1)+""+(j+1));
			}
			//en passant right
			if(i==4&&j<7&& board[5][j+1]=='_'&&eps==j+1)
			{
				s.add((i+1)+""+(j+1));
				epsTrue=true;
			}
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
	}
	
	
	
	
	public char getPos(int row,int col)
	{
		return board[row][col];
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
