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

	private int findRightColorGreedy(List<Integer> sequence, int index, int power) {
		for(int symbol=0;symbol<power; symbol++) {
			sequence.add(index, symbol);
			if(pickProperFind(sequence) == null) {
				sequence.remove(index);
				return symbol;
			} else {
				sequence.remove(index);
			}
		}

		return 0;
	}

	public int findRightColorPredicting(List<Integer> sequence, int index) {
		if(painterNestingLevel == 0) {
			return findRightColorGreedy(sequence, index, power);
		}
		List<Integer> scoreList = initScoreList(power);
		List<Integer> colorList = getFitableColorList(sequence, index);
		for(int color: colorList) {
			sequence.add(index, color);
			simulation(sequence, color, scoreList, painterNestingLevel);
			sequence.remove(index);
			if(getTime("painter") > maximumThinkTime) {
				return -1;
			}
		}
		return getRandomFromScoreList(scoreList);
	}

	private int getRandomFromScoreList(List<Integer> predictList) {
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

	private int findRightIndexGreedy(List<Integer> sequence, int power) {
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
			return findRightIndexGreedy(sequence, power);
		}
		List<Integer> scoreList = initScoreList(sequence.size() + 1);

		for (int i =0;i<sequence.size()+1;i++) {
			List<Integer> colors = getFitableColorList(sequence, i);
			for (int color : colors) {
				sequence.add(i, color);
				simulation(sequence, i, scoreList, builderNestingLevel);
				sequence.remove(i);
				if(getTime("builder") > maximumThinkTime) {
					return -1;
				}
			}

		}
		return getRandomMinFromPredict(scoreList);
	}

	private void simulation(List<Integer> sequence, int indexInScoreList, List<Integer> scoreList, int invokes) {
		invokes--;
		for(int j=0;j<sequence.size()+1;j++) {
			List<Integer> colorList = getFitableColorList(sequence, j);
			updateScoreList(scoreList, indexInScoreList, colorList.size());
			for(int color: colorList) {
				sequence.add(j, color);
				if(invokes > 0) {
					simulation(sequence, indexInScoreList, scoreList, invokes);
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

	private void updateScoreList(List<Integer> scoreList, int colorIndex, int colorsFitting) {
		scoreList.set(colorIndex, scoreList.get(colorIndex)+colorsFitting);
	}

	private List<Integer> initScoreList(int size) {
		List<Integer> scoreList = new ArrayList<>();
		for(int i=0;i<size;i++) {
			scoreList.add(0);
		}
		return scoreList;
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
