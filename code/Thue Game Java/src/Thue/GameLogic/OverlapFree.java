package Thue.GameLogic;

import Thue.Algorithm.ComputerOpponent;
import Thue.DataHolder.Subsequence;
import Thue.GameConfig.GameMode;
import Thue.Algorithm.GameHandlingAlgorythm;

public class OverlapFree extends AbstractFree {

	public OverlapFree() {
		super();
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
				System.out.println(OVERLAP_FOUND);
				System.out.println();
				printSubsequence(repeatedSequence, 0);
				System.out.print(" <-> ");
				printSubsequence(repeatedSequence, repeatedSequence.getLength()-1);
				finished = true;
			}
		}
	}

	private void handleGameModes(GameMode gameMode) throws Exception {
		if(gameMode == GameMode.humanHuman) {
			addNumberToSequence();
		} else if(gameMode == GameMode.humanBuilder) {
			builderMode();
		} else if(gameMode == GameMode.pcBuilder) {
			System.out.println("NOT SUPPORTED");
			throw new Exception();
		} else if(gameMode == GameMode.pcPc) {
			System.out.println("NOT SUPPORTED");
			throw new Exception();
		} else {
			throw new Exception();
		}
	}

	private void builderMode() {
		humanBuilderPCPainterGetNumber();
		int colorIndex = ComputerOpponent.findRightColorOverlap(sequence, builderIndex, power);
		if(colorIndex > -1) {
			System.out.println(String.format(COMPUTER_PICKED_COLOR, colorIndex));
			sequence.add(builderIndex, colorIndex);
		} else {
			System.out.println(COMPUTER_LOST);
			sequence.add(builderIndex, 0);
		}
	}
}
