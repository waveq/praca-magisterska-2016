package thue.gameLogic;

import thue.algorithm.ComputerOpponent;
import thue.algorithm.GameHandlingAlgorythm;
import thue.dataHolder.Subsequence;
import thue.gameConfig.GameMode;
import thue.dataHolder.NestingLevels;

public class SquareFree extends AbstractFree {

	public SquareFree(NestingLevels nestingLevels, int power, GameMode gameMode) {
		super(nestingLevels, power, gameMode);
		computerOpponent = new ComputerOpponent(this, power, nestingLevels);
		computerOpponent.startTime(GAME_TIME);
	}

	public void startGame() {
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
			Subsequence repeatedSequence = GameHandlingAlgorythm.findSquare(sequence);
			if(repeatedSequence != null) {
				printAndLog(SQUARE_FOUND);

				printGameOverSquare(repeatedSequence);
				printlnAndLog(LINELN);

				if(gameMode == GameMode.pcPc) {
					printOtherFinalOptions();
					printlnAndLog(LINELN);
				}
				finished = true;
			}
		}
		printlnAndLog(String.format(POINTS_MESSAGE, sequence.size(), convertNanoSecondsToSeconds(computerOpponent.getTime(GAME_TIME))));
	}

	private void printOtherFinalOptions() {
		int failedColor = sequence.get(builderIndex);
		printAndLog(OTHER_SCENARIOS_MESSAGE);
		for(int i =0;i<power;i++) {
			if(i == failedColor) {
				continue;
			}
			sequence.set(builderIndex, i);
			Subsequence repeatedSequence = GameHandlingAlgorythm.findSquare(sequence);
			printAndLog(String.format(FOR_NUMBER, i));
			printGameOverSquare(repeatedSequence);
		}
	}

	private void printGameOverSquare(Subsequence repeatedSequence) {
		printlnAndLog(" ");
		printSubsequence(repeatedSequence, 0);
		printAndLog(" <-> ");
		printSubsequence(repeatedSequence, repeatedSequence.getLength());
	}


}