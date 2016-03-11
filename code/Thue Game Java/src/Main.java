import Thue.AbstractFree;
import Thue.OverlapFree;
import Thue.SquareFree;

public class Main {
	public static void main (String[] args) {
		AbstractFree t = new SquareFree();
		//t = new OverlapFree();
		t.startGame();
	}
}
