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
				repeatedSequence.forEach(element -> System.out.print(String.format(LIST_ELEMENT_FORMAT, element)));
				finished = true;
			}
		}
	}

	private List<Integer> findSquare() {
		List<Integer> repeatedSequence = null;
		int maxSeqSize = sequence.size()/2;
		int minSeqSize = 1;
		for(int subSeqSize=minSeqSize; subSeqSize<=maxSeqSize; subSeqSize++) {
			repeatedSequence = compareSubSequences(subSeqSize);
			if(repeatedSequence != null) {
				return repeatedSequence;
			}
		}
		return null;
	}

	private List<Integer> compareSubSequences(int subSeqSize) {
		List<Integer> left = new ArrayList<>();
		List<Integer> right = new ArrayList<>();
		int comparesFitInSequence = (sequence.size()-subSeqSize*2) + 1;
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
