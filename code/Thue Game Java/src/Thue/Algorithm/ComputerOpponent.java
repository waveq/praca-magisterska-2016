package Thue.Algorithm;

import Thue.DataHolder.Subsequence;
import Thue.GameLogic.AbstractFree;
import Thue.GameLogic.OverlapFree;
import Thue.GameLogic.SquareFree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ComputerOpponent {

	private AbstractFree gameTypeInstance;

	public ComputerOpponent(AbstractFree gameTypeInstance) {
		this.gameTypeInstance = gameTypeInstance;
	}

	public int findRightColorGeneral(List<Integer> sequence, int index, int power, int ignore) {
		int colorIndex = -1;
		for(int i=0;i<power; i++) {
			if(i <= ignore) {
				continue;
			}
			sequence.add(index, i);
			if(pickProperFind(sequence) == null) {
				colorIndex = i;
				sequence.remove(index);
				break;
			} else {
				sequence.remove(index);
			}
		}

		return colorIndex;
	}

	public int findRightColorGreedy(List<Integer> sequence, int index, int power) {
		int colorIndex = -1;
		for(int i=0;i<power; i++) {
			sequence.add(index, i);
			if(pickProperFind(sequence) == null) {
				colorIndex = i;
				sequence.remove(index);
				break;
			} else {
				sequence.remove(index);
			}
		}

		return colorIndex;
	}


	public int findRightColorPredicting(List<Integer> sequence, int index, int power) {
		int colorIndex = -1;
		int colorFromPreviousLoop;
		List<Integer> predictList = initPredictList(power);

		for(int k = 0;k<power;k++) {
			colorFromPreviousLoop = colorIndex;
			colorIndex = findRightColorGeneral(sequence, index, power, colorIndex);
			if(colorIndex == -1) {
				colorIndex = colorFromPreviousLoop+1;
				continue;
			}
			sequence.add(index, colorIndex);
			for (int currentIndex = 0; currentIndex < sequence.size()+1; currentIndex++) {
				int colorsFitting = howManyColorsFit(sequence, currentIndex, power);
				predictList.set(colorIndex, predictList.get(colorIndex)+colorsFitting);
			}
			sequence.remove(index);
		}

		return predictList.indexOf(Collections.max(predictList));
	}

	public int howManyColorsFit(List<Integer> sequence, int index, int power) {
		int colorsFiting = 0;
		for(int i=0;i<power; i++) {
			sequence.add(index, i);
			if(pickProperFind(sequence) == null) {
				colorsFiting++;
				sequence.remove(index);
			} else {
				sequence.remove(index);
			}
		}

		return colorsFiting;
	}

	private List<Integer> initPredictList(int size) {
		List<Integer> predictList = new ArrayList<>();
		for(int i=0;i<size;i++) {
			predictList.add(0);
		}
		return predictList;
	}

	private Subsequence pickProperFind(List<Integer> sequence) {
		if(gameTypeInstance instanceof OverlapFree) {
			return GameHandlingAlgorythm.findOverlap(sequence);
		} else if(gameTypeInstance instanceof SquareFree) {
			return GameHandlingAlgorythm.findSquare(sequence);
		}

		System.out.println("### ERROR AT PICK PROPER FIND ###");
		return null;
	}
}
