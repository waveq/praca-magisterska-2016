import Thue.AbstractFree;
import Thue.OverlapFree;
import Thue.SquareFree;

import java.util.Scanner;

public class Main {
	private static Scanner lineReader = new Scanner(System.in);
	private static String answer;

	public static void main (String[] args) {
		AbstractFree t = null;

		System.out.println("1 - Overlap free\n2 - Square free");
		answer = lineReader.nextLine();

		if(answer.equals("1")) {
			t = new OverlapFree();
		}

		if(answer.equals("2")) {
			t = new SquareFree();
		}

		if(t != null) {
			t.startGame();
		}
	}
}
