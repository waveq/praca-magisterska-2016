package thue.algorithm;

import thue.dataHolder.Subsequence;

import java.util.ArrayList;
import java.util.List;

public class GameHandlingAlgorythm {

	public static Subsequence findSquare(List<Integer> sequence) {
		Subsequence squareSubsequence = null;
		int maxSeqSize = sequence.size()/2;
		int minSeqSize = 1;
		for(int subSeqSize=minSeqSize; subSeqSize<=maxSeqSize; subSeqSize++) {
			squareSubsequence = compareSubSequence(subSeqSize, sequence);
			if(squareSubsequence != null) {
				return squareSubsequence;
			}
		}
		return null;
	}

	private static Subsequence compareSubSequence(int subSeqSize, List<Integer> sequence) {
		List<Integer> left = new ArrayList<>();
		List<Integer> right = new ArrayList<>();
		int comparesFitInSequence = (sequence.size() + 1) - (subSeqSize*2) ;
		for(int i=0; i<comparesFitInSequence; i++) {
			for(int j =0;j<subSeqSize;j++) {
				left.add(sequence.get(i+j));
				right.add(sequence.get(i+j+subSeqSize));
			}
			if(listsAreEqual(left, right)) {
				return new Subsequence(left, i, subSeqSize);
			}
			left.clear();
			right.clear();
		}
		return null;
	}

	public static Subsequence findOverlap(List<Integer> sequence) {
		Subsequence repeatedSequence = null;
		int maxSeqSize = (sequence.size()/2)+1;
		int minSeqSize = 3;
		for(int subSeqSize=minSeqSize; subSeqSize<=maxSeqSize; subSeqSize++) {
			repeatedSequence = compareSubSequencesOverlap(subSeqSize, sequence);
			if(repeatedSequence != null) {
				return repeatedSequence;
			}
		}
		return null;
	}

	private static Subsequence compareSubSequencesOverlap(int subSeqSize, List<Integer> sequence) {
		List<Integer> left = new ArrayList<>();
		List<Integer> right = new ArrayList<>();
		int comparesFitInSequence = (sequence.size() + 2) - (subSeqSize*2);
		for(int i=0; i<comparesFitInSequence; i++) {
			for(int j =0;j<subSeqSize;j++) {
				left.add(sequence.get(i+j));
				right.add(sequence.get(i+j+subSeqSize-1));
			}
			if(listsAreEqual(left, right)) {
				return new Subsequence(left, i, subSeqSize);
			}
			left.clear();
			right.clear();
		}
		return null;
	}

	public static boolean listsAreEqual(List<Integer> left, List<Integer> right) {
		for(int i =0;i<left.size();i++) {
			if(left.get(i) != right.get(i)) {
				return false;
			}
		}
		return true;
	}
}
