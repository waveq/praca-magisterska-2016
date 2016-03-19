package Thue;

import Thue.DataHolder.Subsequence;

import java.util.List;

public class SquareFree extends AbstractFree {

	public SquareFree() {
		super();
	}

	public void startGame() {
		boolean finished = false;
		sequence.add(getValueFromUser(FIRST_NUMBER_MESSAGE));
		printSequence(sequence);
		while(!finished) {
			//addNumberToSequence();
			builderMode();
			printSequence(sequence);
			Subsequence repeatedSequence = GameHandlingAlgorythm.findSquare(sequence);
			if(repeatedSequence != null) {
				System.out.println(SQUARE_FOUND);
				System.out.println();
				printSubsequence(repeatedSequence, 0);
				printSubsequence(repeatedSequence, repeatedSequence.getLength());
				finished = true;
			}
		}
	}

	private void builderMode() {
		humanBuilderPCPainterGetNumber();
		int colorIndex = ComputerOpponent.findRightColor(sequence, builderIndex, power);
		if(colorIndex > -1) {
			System.out.println(String.format(COMPUTER_PICKED_COLOR, colorIndex));
			sequence.add(builderIndex, colorIndex);
		} else {
			System.out.println(COMPUTER_LOST);
			sequence.add(builderIndex, 0);
		}
	}
}
