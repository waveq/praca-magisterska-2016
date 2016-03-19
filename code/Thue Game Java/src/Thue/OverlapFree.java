package Thue;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class OverlapFree extends AbstractFree {

	public OverlapFree() {
		super();
	}

	public void startGame() {
		boolean finished = false;
		sequence.add(getValueFromUser(FIRST_NUMBER_MESSAGE));
		printSequence(sequence);
		while(!finished) {
			addNumberToSequence();
			printSequence(sequence);
			List<Integer> repeatedSequence = GameHandlingAlgorythm.findOverlap(sequence);
			if(repeatedSequence != null) {
				System.out.println(OVERLAP_FOUND);
				printSequence(repeatedSequence);
				finished = true;
			}
		}
	}
}
