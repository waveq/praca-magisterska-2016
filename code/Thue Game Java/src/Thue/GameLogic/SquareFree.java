package Thue.GameLogic;

import Thue.Algorithm.ComputerOpponent;
import Thue.DataHolder.Subsequence;
import Thue.GameConfig.GameMode;
import Thue.Algorithm.GameHandlingAlgorythm;

public class SquareFree extends AbstractFree {

	public SquareFree() {
		super();
		computerOpponent = new ComputerOpponent(this);
	}

	public void startGame(GameMode gameMode) {
		sequence.add(0);
		printSequence(sequence);
		try {
			gameLoop(gameMode);
		} catch(Exception e) {
			System.out.println("\nSPRAWDZ PLIK CONFIG.PROPERTIES");
		}
	}

	protected void gameLoop(GameMode gameMode) throws Exception {
		boolean finished = false;
		while(!finished) {
			handleGameModes(gameMode);
			printSequence(sequence);
			Subsequence repeatedSequence = GameHandlingAlgorythm.findSquare(sequence);
			if(repeatedSequence != null) {
				System.out.println(SQUARE_FOUND);
				System.out.println();
				printSubsequence(repeatedSequence, 0);
				System.out.print(" <-> ");
				printSubsequence(repeatedSequence, repeatedSequence.getLength());
				finished = true;
			}
		}
	}


}
