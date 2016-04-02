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
	private int power;
	private int nestingLevel;

	public ComputerOpponent(AbstractFree gameTypeInstance, int power, int nestingLevel) {
		this.gameTypeInstance = gameTypeInstance;
		this.power = power;
		this.nestingLevel = nestingLevel;
	}

	public int findRightColorGeneral(List<Integer> sequence, int index, int ignore) {
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

	public int findRightColorGreedy(List<Integer> sequence, int index) {
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

	public int findRightColorPredicting(List<Integer> sequence, int index) {
		if(nestingLevel == 0) {
			return findRightColorGreedy(sequence, index);
		}

		List<Integer> predictList = initPredictList(power);
		List<Integer> colorList = getFitableColorList(sequence, index);

		for(int color: colorList) {
			sequence.add(index, color);
			simulation(sequence, color, predictList, nestingLevel);
			sequence.remove(index);

		}
		return predictList.indexOf(Collections.max(predictList));
	}

	private void simulation(List<Integer> sequence, int colorIndexInPredictList, List<Integer> predictList, int invokes) {
		invokes--;
		for(int j=0;j<sequence.size()+1;j++) {
			List<Integer> colorList = getFitableColorList(sequence, j);
			updatePredictList(predictList, colorIndexInPredictList, colorList.size());
			for(int color: colorList) {
				sequence.add(j, color);
				if(invokes > 0) {
					simulation(sequence, colorIndexInPredictList, predictList, invokes);
				}
				sequence.remove(j);
			}
		}
	}

	private List<Integer> getFitableColorList(List<Integer> sequence, int index) {
		int colorIndex = -1;
		int colorFromPreviousLoop;
		List<Integer> fitableColorsList = new ArrayList<>();
		List<Integer> predictList = initPredictList(power);

		for(int k = 0;k<power;k++) {
			colorFromPreviousLoop = colorIndex;
			colorIndex = findRightColorGeneral(sequence, index, colorIndex);
			if (colorIndex == -1) {
				colorIndex = colorFromPreviousLoop + 1;
				continue;
			}
			fitableColorsList.add(colorIndex);
		}
		return fitableColorsList;
	}

	private void updatePredictList(List<Integer> predictList, int colorIndex, int colorsFitting) {
		predictList.set(colorIndex, predictList.get(colorIndex)+colorsFitting);
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
