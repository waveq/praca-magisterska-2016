package Thue;

import java.util.*;

public class SquareFree extends AbstractFree {

	public SquareFree() {
		super();
	}

	public void startGame() {
		boolean finished = false;
		sequence.add(getValueFromUser(FIRST_NUMBER_MESSAGE));
		printSequence(sequence);
		while(!finished) {
			addNumberToSequence();
			printSequence(sequence);
			List<Integer> repeatedSequence = findSquare();
			if(repeatedSequence != null) {
				System.out.println(SQUARE_FOUND);
				printSequence(repeatedSequence);
				finished = true;
			}
		}
	}

	private List<Integer> findSquare() {
		List<Integer> squareSeq = null;
		int maxSeqSize = sequence.size()/2;
		int minSeqSize = 1;
		for(int subSeqSize=minSeqSize;
			subSeqSize<=maxSeqSize;
			subSeqSize++) {
			squareSeq = compareSubSeq(subSeqSize);
			if(squareSeq != null) {
				return squareSeq;
			}
		}
		return null;
	}

	private List<Integer> compareSubSeq(int subSeqSize) {
		List<Integer> left = new ArrayList<>();
		List<Integer> right = new ArrayList<>();
		int comparesFitInSequence = (sequence.size() + 1) - (subSeqSize*2) ;
		for(int i=0; i<comparesFitInSequence; i++) {
			for(int j =0;j<subSeqSize;j++) {
				left.add(sequence.get(i+j));
				right.add(sequence.get(i+j+subSeqSize));
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
