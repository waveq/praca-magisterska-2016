package thue.algorithm;

import thue.dataHolder.Subsequence;
import thue.gameConfig.ConfigRetriever;
import thue.dataHolder.NestingLevels;
import thue.gameLogic.AbstractFree;
import thue.gameLogic.OverlapFree;
import thue.gameLogic.SquareFree;

import java.util.*;

public class ComputerOpponent {

	private AbstractFree gameTypeInstance;
	private int power;
	private int builderNestingLevel;
	private int painterNestingLevel;
	private boolean stopThinking = false;
	private Long maximumThinkTime = ConfigRetriever.getMaxThinkTime();

	private HashMap<String, Long> timeMeasures = new HashMap<>();

	public ComputerOpponent(AbstractFree gameTypeInstance, int power, NestingLevels nestingLevels) {
		this.gameTypeInstance = gameTypeInstance;
		this.power = power;
		this.builderNestingLevel = nestingLevels.getBuilderNestingLevel();
		this.painterNestingLevel = nestingLevels.getPainterNestingLevel();
	}

	private int findRightColorGeneral(List<Integer> sequence, int index, int ignore) {
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

	private int findRightColorGreedy(List<Integer> sequence, int index) {
		for(int i=0;i<power; i++) {
			sequence.add(index, i);
			if(pickProperFind(sequence) == null) {
				sequence.remove(index);
				return i;
			} else {
				sequence.remove(index);
			}
		}

		return 0;
	}

	public int findRightColorPredicting(List<Integer> sequence, int index) {
		if(painterNestingLevel == 0) {
			return findRightColorGreedy(sequence, index);
		}

		List<Integer> predictList = initPredictList(power);
		List<Integer> colorList = getFitableColorList(sequence, index);

		for(int color: colorList) {
			sequence.add(index, color);
			simulation(sequence, color, predictList, painterNestingLevel);
			sequence.remove(index);
			if(getTime("painter") > maximumThinkTime) {
				return -1;
			}
		}
	//	return predictList.indexOf(Collections.max(predictList));
		return getRandomMaxFromPredict(predictList);
	}

	private int getRandomMaxFromPredict(List<Integer> predictList) {
		int max = Collections.max(predictList);
		List<Integer> indexesOfMax = new ArrayList<>();
		for(int i = 0; i<predictList.size();i++) {
			if(predictList.get(i) == max) {
				indexesOfMax.add(i);
			}
		}
		return indexesOfMax.get(getRandomNumberInRange(0, indexesOfMax.size() - 1));
	}

	private int getRandomMinFromPredict(List<Integer> predictList) {
		int min = Collections.min(predictList);
		List<Integer> indexesOfMin = new ArrayList<>();
		for(int i = 0; i<predictList.size();i++) {
			if(predictList.get(i) == min) {
				indexesOfMin.add(i);
			}
		}
		return indexesOfMin.get(getRandomNumberInRange(0, indexesOfMin.size()-1));
	}

	private int getRandomNumberInRange(int min, int max) {

		if(min == max) {
			return min;
		}
		if (min > max) {
			throw new IllegalArgumentException("Max musi być większy bądź równy min.");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	private int findRightIndexGreedy(List<Integer> sequence) {
		int winner = -1;
		int smallestColorSize = power;

		for (int i =0;i<sequence.size()+1;i++) {
			List<Integer> colors = getFitableColorList(sequence, i);
			if (colors.size() <= smallestColorSize) {
				smallestColorSize = colors.size();
				winner = i;
			}
		}
		return winner;
	}

	public int findRightIndex(List<Integer> sequence) {
		if(builderNestingLevel == 0) {
			return findRightIndexGreedy(sequence);
		}
		List<Integer> predictList = initPredictList(sequence.size()+1);

		for (int i =0;i<sequence.size()+1;i++) {
			List<Integer> colors = getFitableColorList(sequence, i);
			for (int color : colors) {
				sequence.add(i, color);
				simulation(sequence, i, predictList, builderNestingLevel);
				sequence.remove(i);
				if(getTime("builder") > maximumThinkTime) {
					return -1;
				}
			}

		}
	//	return predictList.indexOf(Collections.min(predictList));
		return getRandomMinFromPredict(predictList);
	}

	private void simulation(List<Integer> sequence, int indexInPreditctList, List<Integer> predictList, int invokes) {
		invokes--;
		for(int j=0;j<sequence.size()+1;j++) {
			List<Integer> colorList = getFitableColorList(sequence, j);
			updatePredictList(predictList, indexInPreditctList, colorList.size());
			for(int color: colorList) {
				sequence.add(j, color);
				if(invokes > 0) {
					simulation(sequence, indexInPreditctList, predictList, invokes);
				}
				sequence.remove(j);
			}
		}
	}

	private List<Integer> getFitableColorList(List<Integer> sequence, int index) {
		int colorIndex = -1;
		int colorFromPreviousLoop;
		List<Integer> fitableColorsList = new ArrayList<>();

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

	public Long getTime(String measureName) {
		Long currentTime = System.nanoTime();
		Long startTime = timeMeasures.get(measureName);
		if(startTime == null) {
			startTime = currentTime;
		}
		return currentTime - startTime;
	}

	public void startTime(String measureName) {
		timeMeasures.put(measureName, System.nanoTime());
	}

	public Long resetTime(String measureName) {
		return timeMeasures.remove(measureName);
	}



	public Long endTime(String measureName) {
		return timeMeasures.remove(measureName);
	}
}
