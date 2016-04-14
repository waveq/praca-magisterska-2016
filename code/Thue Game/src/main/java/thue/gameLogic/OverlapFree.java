package thue.gameLogic;

import thue.algorithm.ComputerOpponent;
import thue.algorithm.GameHandlingAlgorythm;
import thue.dataHolder.Subsequence;
import thue.gameConfig.GameMode;
import thue.dataHolder.NestingLevels;

public class OverlapFree extends AbstractFree {



	public OverlapFree(NestingLevels nestingLevels, int power) {
		super(nestingLevels, power);
		computerOpponent = new ComputerOpponent(this, power, nestingLevels);
		computerOpponent.startTime(GAME_TIME);
	}

	public void startGame(GameMode gameMode) {
		sequence.add(0);
		printSequence(sequence);
		try {
			gameLoop(gameMode);
		} catch(Exception e) {
			System.out.println("\nSPRAWDZ PLIK CONFIG.PROPERTIES");
			e.printStackTrace();
		}
	}

	protected void gameLoop(GameMode gameMode) throws Exception {
		while(!finished) {
			handleGameModes(gameMode);
			printSequence(sequence);
			Subsequence repeatedSequence = GameHandlingAlgorythm.findOverlap(sequence);
			if(repeatedSequence != null) {
				printAndLog(OVERLAP_FOUND);

				printGameOverOverlap(repeatedSequence);
				printlnAndLog(LINELN);

				printOtherFinalOptions();
				printlnAndLog(LINELN);

				printlnAndLog(String.format(POINTS_MESSAGE, sequence.size(), convertNanoSecondsToSeconds(computerOpponent.getTime(GAME_TIME))));
				finished = true;
			}
		}
	}

	private void printOtherFinalOptions() {
		int failedColor = sequence.get(builderIndex);
		printAndLog(OTHER_SCENARIOS_MESSAGE);
		for(int i =0;i<power;i++) {
			if(i == failedColor) {
				continue;
			}
			sequence.set(builderIndex, i);
			Subsequence repeatedSequence = GameHandlingAlgorythm.findOverlap(sequence);
			printAndLog(String.format(FOR_NUMBER, i));
			printGameOverOverlap(repeatedSequence);
		}
	}

	private void printGameOverOverlap(Subsequence repeatedSequence) {
		printlnAndLog(" ");
		printSubsequence(repeatedSequence, 0);
		printAndLog(" <-> ");
		printSubsequence(repeatedSequence, repeatedSequence.getLength() - 1);
	}
}
