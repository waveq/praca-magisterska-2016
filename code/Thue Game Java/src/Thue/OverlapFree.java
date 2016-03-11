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
			List<Integer> repeatedSequence = findOverlap();
			if(repeatedSequence != null) {
				System.out.println(OVERLAP_FOUND);
				printSequence(repeatedSequence);
				finished = true;
			}
		}
	}

	private List<Integer> findOverlap() {
		List<Integer> repeatedSequence = null;
		int maxSeqSize = (sequence.size()/2)+1;
		int minSeqSize = 3;
		for(int subSeqSize=minSeqSize; subSeqSize<=maxSeqSize; subSeqSize++) {
			repeatedSequence = compareSubSequencesOverlap(subSeqSize);
			if(repeatedSequence != null) {
				return repeatedSequence;
			}
		}
		return null;
	}

	private List<Integer> compareSubSequencesOverlap(int subSeqSize) {
		List<Integer> left = new ArrayList<>();
		List<Integer> right = new ArrayList<>();
		int comparesFitInSequence = (sequence.size()-subSeqSize*2) + 2;
		for(int i=0; i<comparesFitInSequence; i++) {
			for(int j =0;j<subSeqSize;j++) {
				left.add(sequence.get(i+j));
				right.add(sequence.get(i+j+subSeqSize-1));
			}
			if(listsAreEqual(left, right)) {
				return left;
			}
			left.clear();
			right.clear();
		}
		return null;
	}
}
