package Thue.GameLogic;

import Thue.Algorithm.ComputerOpponent;
import Thue.DataHolder.Subsequence;
import Thue.GameConfig.GameMode;
import Thue.Algorithm.GameHandlingAlgorythm;
import Thue.GameConfig.NestingLevels;

public class SquareFree extends AbstractFree {

	public SquareFree(NestingLevels nestingLevels) {
		super();
		computerOpponent = new ComputerOpponent(this, power, nestingLevels);
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
				printlnAndLog(SQUARE_FOUND);
				printlnAndLog(" ");
				printSubsequence(repeatedSequence, 0);
				printAndLog(" <-> ");
				printSubsequence(repeatedSequence, repeatedSequence.getLength());
				printlnAndLog(String.format(POINTS_MESSAGE, sequence.size()));
				finished = true;
			}
		}
	}


}
