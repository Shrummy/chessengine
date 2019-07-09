
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
/*
 * 
 */



public class Grid {
	private char[][] board;
	private char[][] newBoard;
	private boolean isWhite;
	private int[][]attackedSquaresW;
	private int[][]attackedSquaresB;
	private int eps=-1;
	private boolean epsTrue=false;
	private boolean isAttacking=false;
	private boolean WcastleK, WcastleQ;
	private boolean BcastleK, BcastleQ;
	private int over=-1;
	
	public Grid(String filename)
	{
		eps=-1;
		
		WcastleK=true;
		WcastleQ=true;
		BcastleK=true;
		BcastleQ=true;
		
		attackedSquaresW=new int[8][8];
		attackedSquaresB=new int[8][8];
		board=new char[8][8];
		
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				board[i][j]='_';
			}
		}
		newBoard=new char[8][8];
		
		readData(filename,board);
		isWhite=true;
		over=0;
	}
	
	public void getAttackedSquares(char[][]b)
	{
		isAttacking=true;
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
			{
				attackedSquaresW[i][j]=0;
				attackedSquaresB[i][j]=0;	
				
			}
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(b[i][j]!='_')
				{
					if(Character.isUpperCase(b[i][j]))
					{
	
						for(String f:getMoves(b[i][j],true,i,j))
						{
							int c1 = Integer.parseInt(f.substring(0,1));
							int c2 = Integer.parseInt(f.substring(1,2));
							attackedSquaresW[c1][c2]=1;	
						}
					}
					else if(Character.isLowerCase(b[i][j]))
					{
						for(String f:getMoves(b[i][j],false,i,j))
						{
							int c1 = Integer.parseInt(f.substring(0,1));
							int c2 = Integer.parseInt(f.substring(1,2));
							attackedSquaresB[c1][c2]=1;	
						}
					}
				}
			}
		}
		isAttacking=false;
	}
	
	
	public boolean isCheck(boolean isWhite)
	{
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(board[i][j]=='K'&&attackedSquaresB[i][j]==1)
					return true;
				if(board[i][j]=='k'&&attackedSquaresW[i][j]==1)
					return true;
			}
		}
		return false;
	}
	
	public int getMaterial(boolean isWhite)
	{
		int m=0;
		for(int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++)
			{
				if(isWhite&&Character.isUpperCase(board[i][j])||!isWhite&&Character.isLowerCase(board[i][j]))
				{
					if((board[i][j]+"").equalsIgnoreCase("P"))
						m++;
					else if((board[i][j]+"").equalsIgnoreCase("N"))
						m+=3;
					else if((board[i][j]+"").equalsIgnoreCase("B"))
						m+=3;
					else if((board[i][j]+"").equalsIgnoreCase("R"))
						m+=5;
					else if((board[i][j]+"").equalsIgnoreCase("Q"))
						m+=9;
				}
			}
		}
		return m;
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
				boolean promote=false;
				int x = ((int)c0-97);
				int y=8-Integer.parseInt(c1+"");
				String p="";
				
				if(s.charAt(s.length()-2)=='=')
				{
					p="="+s.charAt(s.length()-1);
					promote=true;
				}
				
				String t;
				
				if(y>0)
				{
					t = (10*y+x)+""+p;
				}
				else
				{
					t="0"+x+p;
				}
				
				
				
				for(int row=0;row<8;row++)
				{
					if(board[row][x]=='P'&&isWhite)
						{
							for(String r:getPawnMoves(true,row,x))
							{
								if(r.equals(t))
								{
									if(t.charAt(t.length()-2)=='=')
									{
										return "YES"+row+x+y+x+"="+t.charAt(t.length()-1);
									}
									else
										return "YES"+row+x+y+x;
								}
							}
						}
						else if(board[row][x]=='p'&&!isWhite)
						{
							for(String r:getPawnMoves(false,row,x))
							{
								if(r.equals(t))
								{
									if(promote)
									{
										return "YES"+row+x+y+x+"="+p;
									}
									else
									{
										return "YES"+row+x+y+x;
									}
								}
							}
						}
				}
			}
			//pawn captures
			else
			{
				boolean promote=false;
				char c2 = s.charAt(2);
				char c3 = s.charAt(3);
				String p="";
				
				int w = ((int)c2-97);
				int x = ((int)c0-97);
				int y=8-Integer.parseInt(c3+"");
				
				
				
				if(s.charAt(s.length()-2)=='=')
				{
					p="="+s.charAt(s.length()-1);
					promote=true;
				}
				
				
				String t=y+""+w+""+p;
				
				for(int row=0;row<8;row++)
				{
					for(int col=0;col<8;col++)
					{
				
						if((board[row][col]+"").equalsIgnoreCase("P"))
						{
							for(String r:getPawnMoves(isWhite,row,col))
							{
								if(r.equals(t)&&col==x)
								{
									if(promote)
									{
										return "YES"+row+col+y+w+"="+p;
									}
									else
									{
										return "YES"+row+col+y+w;
									}
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
			
			int rt=-1, c=-1;
			char c2;
			char c3;
			int x=0; 
			int y=0;
			
			if(s.indexOf('x')>-1)
			{
				s=s.substring(0,s.indexOf('x'))+s.substring(s.indexOf('x')+1);
			}
			
			c1 = s.charAt(1);
			c2 = s.charAt(2);
			
			if(s.length()==3) //Ne4
			{
				x = ((int)c1-97);
				y=8-Integer.parseInt(c2+"");
			}
			else if(s.length()==4) //Nce4 or N1e4
			{
				c3 = s.charAt(3);
				if(Character.isDigit(c1))
				{
					rt=8-Integer.parseInt(c1+"");
				}
				else
				{
					c=((int)c1-97);
				}
				x = ((int)c2-97);
			    y=8-Integer.parseInt(c3+"");
			}
			else if(s.length()==5) //Nc3e4
			{
				c3 = s.charAt(3);
				c=((int)c1-97);
				rt=8-Integer.parseInt(c2+"");
				char c4=s.charAt(4);
				y=8-Integer.parseInt(c4+"");
				x = ((int)c3-97);
			}
			
			String t=y+""+x;
			for(int row=0;row<8;row++)
			{
				for(int col=0;col<8;col++)
				{
			
					if((board[row][col]+"").equalsIgnoreCase(c0+"")&&Character.isUpperCase(board[row][col])==isWhite)
					{
						for(String r:getMoves(c0,isWhite,row,col))
						{
							if(r.equals(t))
							{
								if((rt>-1&&rt==row||rt==-1)&&(c>-1&&c==col||c==-1))
									return "YES"+row+col+y+x;
								
							}
						}
					}
				}
			}
		}
		
		
		return "NOPE";
	}
	
	public boolean move(String s)
	{	
		String t = isLegalMove(s);
		if(t.substring(0, 3).equals("YES"))
		{
			//Normal Move
			int x = Integer.parseInt(t.substring(3,4));
			int y = Integer.parseInt(t.substring(4,5));
			int z = Integer.parseInt(t.substring(5,6));
			int w = Integer.parseInt(t.substring(6,7));
			
			
			
			if(isCheck(isWhite))
			{
				for(int i=0;i<8;i++)
				{
					for(int j=0;j<8;j++)
					{
						newBoard[i][j]=board[i][j];
					}
				}
				board[z][w]=board[x][y];
				board[x][y]='_';
				if(isCheck(isWhite))
				{
					for(int i=0;i<8;i++)
					{
						for(int j=0;j<8;j++)
						{
							board[i][j]=newBoard[i][j];
						}
					}
					return false;
				}
			}
			else
			{
				board[z][w]=board[x][y];
				board[x][y]='_';
			}
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
				if(s.charAt(s.length()-2)=='=')
				{
					board[z][w]=s.charAt(s.length()-1);
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
				if(s.charAt(s.length()-2)=='=')
				{
					board[z][w]=Character.toLowerCase(s.charAt(s.length()-1));
				}
			}
			epsTrue=false;
			isWhite=!isWhite;
			getAttackedSquares(board);
			return true;
		}
		getAttackedSquares(board);
		return false;
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
			return getPawnMoves(true,i,j);	
		}
		case 'p':{
			return getPawnMoves(false,i,j);	
		}
		case 'N':{
			return getKnightMoves(true,i,j);
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
			if(i==6&&!isAttacking)
			{
				if(board[5][j]=='_')
				{
					if(board[4][j]=='_')
						s.add("4"+j);
				}
			}
			//regular move (with promotion)
			if(i>0&&board[i-1][j]=='_'&&!isAttacking)
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
			if(j>0&& i>0&& (isAttacking||board[i-1][j-1]!='_'&&!Character.isUpperCase(board[i-1][j-1])))
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
			if(j<7&& i>0 && (isAttacking||board[i-1][j+1]!='_'&&!Character.isUpperCase(board[i-1][j+1])))
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
				if(board[2][j]=='_'&&!isAttacking)
				{
					if(board[3][j]=='_')
						s.add("3"+j);
				}
			}
			//regular move (with promotion)
			if(i<7&&board[i+1][j]=='_'&&!isAttacking)
			{
				if(i==6)
				{
					s.add((i+1)+""+j+"=Q");
					s.add((i+1)+""+j+"=R");
					s.add((i+1)+""+j+"=B");
					s.add((i+1)+""+j+"=N");
				}
				else
				{
					s.add((i+1)+""+j);
				}
			}
			//captures left
			if(j>0&& i<7&& (isAttacking||board[i+1][j-1]!='_'&&Character.isUpperCase(board[i+1][j-1])))
			{
				if(i==6)
				{
					s.add((i+1)+""+(j-1)+"=Q");
					s.add((i+1)+""+(j-1)+"=R");
					s.add((i+1)+""+(j-1)+"=B");
					s.add((i+1)+""+(j-1)+"=N");
				}
				else
				{
					s.add((i+1)+""+(j-1));
				}
			}
			//captures right
			if(j<7&& i>0 &&(isAttacking||board[i+1][j+1]!='_'&&Character.isUpperCase(board[i+1][j+1])))
			{
				if(i==6)
				{
					s.add((i+1)+""+(j+1)+"=Q");
					s.add((i+1)+""+(j+1)+"=R");
					s.add((i+1)+""+(j+1)+"=B");
					s.add((i+1)+""+(j+1)+"=N");
				}
				else
				{
					s.add((i+1)+""+(j+1));
				}
			}
			//en passant left
			if(j>0&& i==4 && board[5][j-1]=='_'&&eps==j-1)
			{
				s.add((i+1)+""+(j-1));
				epsTrue=true;
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
		if(i<6&&j<7&&(isAttacking||board[i+2][j+1]=='_'||Character.isUpperCase(board[i+2][j+1])!=isWhite))
			s.add((i+2)+""+(j+1));
		
		if(i<6&&j>0&&(isAttacking||board[i+2][j-1]=='_'||Character.isUpperCase(board[i+2][j-1])!=isWhite))
			s.add((i+2)+""+(j-1));

		if(i<7&&j<6&&(isAttacking||board[i+1][j+2]=='_'||Character.isUpperCase(board[i+1][j+2])!=isWhite))
			s.add((i+1)+""+(j+2));
		
		if(i<7&&j>1&&(isAttacking||board[i+1][j-2]=='_'||Character.isUpperCase(board[i+1][j-2])!=isWhite))
			s.add((i+1)+""+(j-2));
		
		if(i>1&&j<7&&(isAttacking||board[i-2][j+1]=='_'||Character.isUpperCase(board[i-2][j+1])!=isWhite))
			s.add((i-2)+""+(j+1));
	
		if(i>1&&j>0&&(isAttacking||board[i-2][j-1]=='_'||Character.isUpperCase(board[i-2][j-1])!=isWhite))
			s.add((i-2)+""+(j-1));
	
		if(i>0&&j<6&&(isAttacking||board[i-1][j+2]=='_'||Character.isUpperCase(board[i-1][j+2])!=isWhite))
			s.add((i-1)+""+(j+2));

		if(i>0&&j>1&&(isAttacking||board[i-1][j-2]=='_'||Character.isUpperCase(board[i-1][j-2])!=isWhite))
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
			else if(isAttacking||Character.isUpperCase(board[a][b])!=isWhite)
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
			else if(isAttacking||Character.isUpperCase(board[a][b])!=isWhite)
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
			else if(isAttacking||Character.isUpperCase(board[a][b])!=isWhite)
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
			else if(isAttacking||Character.isUpperCase(board[a][b])!=isWhite)
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
			else if(isAttacking||Character.isUpperCase(board[a][b])!=isWhite)
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
			else if(isAttacking||Character.isUpperCase(board[a][b])!=isWhite)
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
			else if(isAttacking||Character.isUpperCase(board[a][b])!=isWhite)
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
			else if(isAttacking||Character.isUpperCase(board[a][b])!=isWhite)
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
		if(isWhite)
		{
			if(i>0&&j>0&&(isAttacking||((board[i-1][j-1]=='_'||!Character.isUpperCase(board[i-1][j-1]))&&attackedSquaresB[i-1][j-1]==0)))		
				s.add((i-1)+""+(j-1));
			
			if(i>0&&(isAttacking||((board[i-1][j]=='_'||!Character.isUpperCase(board[i-1][j]))&&attackedSquaresB[i-1][j]==0)))
			{
				s.add((i-1)+""+j);
			}
			
			if(i>0&&j<7&&(isAttacking||((board[i-1][j+1]=='_'||!Character.isUpperCase(board[i-1][j+1]))&&attackedSquaresB[i-1][j+1]==0)))
			{
				s.add((i-1)+""+(j+1));
			}
			
			if(i<7&&j>0&&(isAttacking||((board[i+1][j-1]=='_'||!Character.isUpperCase(board[i+1][j-1]))&&attackedSquaresB[i+1][j-1]==0)))
				s.add((i+1)+""+(j-1));
			
			if(i<7&&(isAttacking||((board[i+1][j]=='_'||!Character.isUpperCase(board[i+1][j]))&&attackedSquaresB[i+1][j]==0)))
				s.add((i+1)+""+j);
			
			if(i<7&&j<7&&(isAttacking||((board[i+1][j+1]=='_'||!Character.isUpperCase(board[i+1][j+1]))&&attackedSquaresB[i+1][j+1]==0)))
				s.add((i+1)+""+(j+1));
			
			if(j>0&&(isAttacking||((board[i][j-1]=='_'||!Character.isUpperCase(board[i][j-1]))&&attackedSquaresB[i][j-1]==0)))
				s.add(i+""+(j-1));
			
			if(j<7&&(isAttacking||((board[i][j+1]=='_'||!Character.isUpperCase(board[i][j+1]))&&attackedSquaresB[i][j+1]==0)))
				s.add(i+""+(j+1));
		}
		else
		{
			if(i>0&&j>0&&(isAttacking||((board[i-1][j-1]=='_'||Character.isUpperCase(board[i-1][j-1]))&&attackedSquaresW[i-1][j-1]==0)))		
				s.add((i-1)+""+(j-1));
			
			if(i>0&&(isAttacking||((board[i-1][j]=='_'||Character.isUpperCase(board[i-1][j]))&&attackedSquaresW[i-1][j]==0)))
				s.add((i-1)+""+j);
			
			if(i>0&&j<7&&(isAttacking||((board[i-1][j+1]=='_'||Character.isUpperCase(board[i-1][j+1]))&&attackedSquaresW[i-1][j+1]==0)))
				s.add((i-1)+""+(j+1));
			
			
			if(i<7&&j>0&&(isAttacking||((board[i+1][j-1]=='_'||Character.isUpperCase(board[i+1][j-1]))&&attackedSquaresW[i+1][j-1]==0)))
				s.add((i+1)+""+(j-1));
			
			if(i<7&&(isAttacking||((board[i+1][j]=='_'||Character.isUpperCase(board[i+1][j]))&&attackedSquaresW[i+1][j]==0)))
				s.add((i+1)+""+j);
			
			if(i<7&&j<7&&(isAttacking||((board[i+1][j+1]=='_'||Character.isUpperCase(board[i+1][j+1]))&&attackedSquaresW[i+1][j+1]==0)))
				s.add((i+1)+""+(j+1));
			
			if(j>0&&(isAttacking||((board[i][j-1]=='_'||Character.isUpperCase(board[i][j-1]))&&attackedSquaresW[i][j-1]==0)))
				s.add(i+""+(j-1));
			
			if(j<7&&(isAttacking||((board[i][j+1]=='_'||Character.isUpperCase(board[i][j+1]))&&attackedSquaresW[i][j+1]==0)))
				s.add(i+""+(j+1));
		}
		return s;
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
	
	public int[][] getAW(){
		return attackedSquaresW;
	}
	
	public int[][] getAB(){
		return attackedSquaresB;
	}
	
}
