package thue.gameLogic;

import thue.algorithm.ComputerOpponent;
import thue.algorithm.GameHandlingAlgorythm;
import thue.dataHolder.Subsequence;
import thue.gameConfig.GameMode;
import thue.gameConfig.NestingLevels;

public class OverlapFree extends AbstractFree {



	public OverlapFree(NestingLevels nestingLevels) {
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
			Subsequence repeatedSequence = GameHandlingAlgorythm.findOverlap(sequence);
			if(repeatedSequence != null) {
				printlnAndLog(OVERLAP_FOUND);
				printlnAndLog(" ");
				printSubsequence(repeatedSequence, 0);
				printAndLog(" <-> ");
				printSubsequence(repeatedSequence, repeatedSequence.getLength() - 1);
				printlnAndLog(String.format(POINTS_MESSAGE, sequence.size()));
				finished = true;
			}
		}
	}
}