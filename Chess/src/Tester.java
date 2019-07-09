
public class Tester {
	public static void main(String[] args)
	{
		Grid board = new Grid("data/initial.txt");
		board.game("1. h3 b6 2. b4 e6 3. Bb2 Nf6 4. a3 Bb7 5. e3 c5 6. bxc5 bxc5 7. c4 Nc6 8. Nc3 Be7 9. Nf3 O-O 10. d4 cxd4 11. exd4 d5 12. c5 Qa5 13. Qd2 Ba6 14. Nxd5 Qxd2+ 15. Nxd2 Bxf1 16. Nxe7+ Nxe7 17. Rxf1 Rfd8 18. Nc4 Nc6 19. O-O-O Rd5 20. Rfe1 Rad8 21. Nd6 Ne8 22. Nxe8 Rxe8 23. Bc3 Red8 24. Kc2 Kf8 25. f4 Ke7 26. Re4 f5 27. Re3 Nxd4+ 28. Kb2 Rb8+ 29. Ka2 Ne2 30. Be5 Rxd1 31. Bxb8 Nd4 32. g3 Rd2+ 33. Kb1 Nc6 34. Bd6+ Kd7 35. Kc1 Rh2 36. g4 g6 37. Rb3 Kc8 38. Re3 Kd7 39. Rd3 Kc8 40. Rb3 Ra2 41. g5 Rg2 42. Kd1 Rg1+ 43. Kd2 Rg2+ 44. Ke1 Rh2 45. Kf1 Ra2 46. Kg1 Ra1+ 47. Kg2 Ra2+ 48. Kg3 Ra1 49. Rd3 Re1 50. Kf2 Re4 51. Rb3 Rc4 52. h4 Rc2+ 53. Kg3 Rc1 54. h5 Rh1 55. hxg6 hxg6 56. Kg2 Rh8 57. a4 Rh7 58. Re3 Kd7 59. Re1 a5 60. Bf8 Nb4 61. Rd1+ Nd5 62. Kg3 Kc6 63. Re1 Kd7 64. c6+ Kxc6 65. Rxe6+ Kb7 66. Rxg6 Rh3+ 67. Kf2 Nxf4 68. Rf6 ");
		//System.out.println(board.getMaterial(false)-board.getMaterial(true));
	}
}
