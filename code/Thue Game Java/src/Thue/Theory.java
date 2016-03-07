package Thue;

import java.util.*;
import static java.util.stream.Collectors.toList;

/**
 * Created by szymonre on 24/11/15.
 */
public class Theory {

	private List<Integer> sequence = new ArrayList<>();
	private List<Integer> numberSet = new ArrayList<>();
	private Scanner lineReader = new Scanner(System.in);
	private int power;
	public Theory() {
		System.out.println("Podaj moc zbioru: ");
		power = lineReader.nextInt();
		populateNumberSet(power);
		printNumberSet();
	}

	public void theoryStart() throws InputMismatchException {
		System.out.println("Podaj pierwszą liczbę");
		boolean isFinished = false;
		sequence.add(lineReader.nextInt());
		while(!isFinished) {
			isFinished = addNumberToSequence();
			printSequence();
			List<Integer> repeatedSequence = findRepetition();
			if(repeatedSequence != null) {
				System.out.println("\nHas Repetitions: ");
				repeatedSequence.forEach(el -> System.out.print(" | " + el + " |"));
				isFinished = true;
			}
		}
	}

	private boolean addNumberToSequence() throws InputMismatchException {
		System.out.println("\nPodaj indeks");
		int index = lineReader.nextInt();
		System.out.println("Podaj liczbę");
		int number = lineReader.nextInt();

		if(number < 0 || index < 0 || index > sequence.size()) {
			System.out.println("Błąd, dobranoc");
			return true;
		} else {
			sequence.add(index, number);
			return false;
		}
	}

	private void printSequence() {
		System.out.println("<= ############# =>");
		sequence.forEach(elem -> System.out.print(" | "+ elem +" |"));
	}

	private void populateNumberSet(int power) {
		for(int i=0;i<power;i++) {
			numberSet.add(i);
		}
	}

	private void printNumberSet() {
		System.out.println("Możesz użyć następujących liczb: ");
		numberSet.forEach(elem -> System.out.println(elem));
	}

	private List<Integer> findRepetition() {
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

	private boolean listsAreEqual(List<Integer> left, List<Integer> right) {
		return left.stream().collect(toList()).containsAll(right);
	}
}
