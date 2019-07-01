
public class Tester {
	public static void main(String[] args)
	{
		Grid board = new Grid("data/initial.txt");
		System.out.println(board);
		board.move("e4");
		System.out.println(board);
		board.move("e5");
		System.out.println(board);
		board.move("d4");
		System.out.println(board);
		board.move("d5");
		System.out.println(board);
	}
}
