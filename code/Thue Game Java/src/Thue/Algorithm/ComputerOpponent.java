package Thue.Algorithm;

import java.util.List;

public class ComputerOpponent {

	public static int findRightColorSquare(List<Integer> sequence, int index, int power) {
		int colorIndex = -1;
		for(int i=0;i<power; i++) {
			sequence.add(index, i);
			if(GameHandlingAlgorythm.findSquare(sequence) == null) {
				colorIndex = i;
				sequence.remove(index);
				break;
			} else {
				sequence.remove(index);
			}
		}

		return colorIndex;
	}

	public static int findRightColorOverlap(List<Integer> sequence, int index, int power) {
		int colorIndex = -1;
		for(int i=0;i<power; i++) {
			sequence.add(index, i);
			if(GameHandlingAlgorythm.findOverlap(sequence) == null) {
				colorIndex = i;
				sequence.remove(index);
				break;
			} else {
				sequence.remove(index);
			}
		}

		return colorIndex;
	}
}
