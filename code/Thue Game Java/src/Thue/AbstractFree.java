package Thue;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public abstract class AbstractFree {

	protected int power;
	protected Scanner lineReader = new Scanner(System.in);
	protected List<Integer> numberSet = new ArrayList<>();
	protected List<Integer> sequence = new ArrayList<>();

	private static final String SET_SIZE_MESSAGE = "#> Podaj moc zbioru: ";
	private static final String AVAILABLE_NUMBERS_MESSAGE = "#> Możesz użyć następujących liczb: ";
	private static final String GET_INDEX_MESSAGE = "\n#> Podaj indeks: ";
	private static final String GET_VALUE_MESSAGE = "#> Podaj liczbę: ";
	private static final String LINE = "## ============== ##";
	private static final String INVALID_NUMBERS = "#> Wprowadziłeś niepoprawne liczby, popraw się!";

	protected static final String FIRST_NUMBER_MESSAGE = "#> Podaj pierwszą liczbę: ";
	protected static final String SQUARE_FOUND = "\n#> Znaleziono kwadrat: ";
	protected static final String OVERLAP_FOUND = "\n#> Znaleziono nasunięcie: ";
	protected static final String LIST_ELEMENT_FORMAT = " | %s |";

	public AbstractFree() {
		power = getValueFromUser(SET_SIZE_MESSAGE);
		populateNumberSet(power);
		printNumberSet();
	}

	protected int getValueFromUser(String message) {
		System.out.println(message);
		return lineReader.nextInt();
	}

	protected void printNumberSet() {
		System.out.println(AVAILABLE_NUMBERS_MESSAGE);
		numberSet.forEach(elem -> System.out.println(elem));
	}

	private void populateNumberSet(int power) {
		for(int i=0;i<power;i++) {
			numberSet.add(i);
		}
	}

	protected void printSequence(List<Integer> sequence) {
		System.out.println(LINE);
		sequence.forEach(elem -> System.out.print(String.format(LIST_ELEMENT_FORMAT, elem)));
	}

	protected void addNumberToSequence() throws InputMismatchException {
		boolean invalidNumbers = true;

		while(invalidNumbers) {
			int index = getValueFromUser(GET_INDEX_MESSAGE);
			int number = getValueFromUser(GET_VALUE_MESSAGE);
			if (invalidNumbers(index, number)) {
				System.out.println(INVALID_NUMBERS);
				invalidNumbers = true;
			} else {
				sequence.add(index, number);
				invalidNumbers = false;
			}
		}
	}

	private boolean invalidNumbers(int index, int number) {
		return number < 0 || index < 0 || index > sequence.size() || number > power-1;
	}

	protected boolean listsAreEqual(List<Integer> left, List<Integer> right) {
		for(int i =0;i<left.size();i++) {
			if(left.get(i) != right.get(i)) {
				return false;
			}
		}
		return true;
	}

	public abstract void startGame();
}
