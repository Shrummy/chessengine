
public class Tester {
	public static void main(String[] args)
	{
		Grid board = new Grid("data/initial.txt");
		board.game("1. e4 e5 2. d4 exd4 3. c4 dxc3 4. h3 cxb2 5. h4 bxa1=R");
		
		

		System.out.println(board);
		
	}
}
