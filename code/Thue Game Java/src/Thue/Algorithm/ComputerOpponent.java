package Thue.Algorithm;

import Thue.DataHolder.Subsequence;
import Thue.GameLogic.AbstractFree;
import Thue.GameLogic.OverlapFree;
import Thue.GameLogic.SquareFree;

import java.util.List;

public class ComputerOpponent {

	private AbstractFree gameTypeInstance;

	public ComputerOpponent(AbstractFree gameTypeInstance) {
		this.gameTypeInstance = gameTypeInstance;
	}

	public int findRightColorGeneral(List<Integer> sequence, int index, int power) {
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
