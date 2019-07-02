
public class Tester {
	public static void main(String[] args)
	{
		Grid board = new Grid("data/initial.txt");
		System.out.println(board);
		board.game("1. e4 e5 2. Nf3 d6 3. d4 Bg4 4. dxe5 Bxf3 5. Qxf3 dxe5 6. Bc4 Nf6 7. Qb3 Qe7 8. Nc3 c6 9. Bg5 b5 10. Nxb5 cxb5"
				+ " 11. Bxb5 Nd7");

		System.out.println(board);
		
	}
}
