package Thue.GameLogic;

import Thue.Algorithm.ComputerOpponent;
import Thue.DataHolder.Subsequence;
import Thue.GameConfig.GameMode;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public abstract class AbstractFree {

	protected int power;
	protected int builderIndex;

	protected Scanner lineReader = new Scanner(System.in);
	protected List<Integer> numberSet = new ArrayList<>();
	protected List<Integer> sequence = new ArrayList<>();
	protected ComputerOpponent computerOpponent;

	private static final String SET_SIZE_MESSAGE = "#> Podaj moc zbioru: ";
	private static final String AVAILABLE_NUMBERS_MESSAGE = "#> Możesz użyć następujących liczb: ";
	private static final String GET_INDEX_MESSAGE = "\n#> Podaj indeks: ";
	private static final String GET_VALUE_MESSAGE = "#> Podaj liczbę: ";
	private static final String LINE = "## ============== ##";
	private static final String INVALID_NUMBERS = "#> Wprowadziłeś niepoprawne liczby, popraw się!";
	private static final String INVALID_INDEX = "#> Wprowadziłeś niepoprawny indeks, popraw się!";
	private static final String INVALID_NUMBER = "#> Wprowadziłeś niepoprawną liczbę, popraw się!";

	protected static final String FIRST_NUMBER_MESSAGE = "#> Podaj pierwszą liczbę: ";
	protected static final String SQUARE_FOUND = "\n#> Znaleziono kwadrat: ";
	protected static final String OVERLAP_FOUND = "\n#> Znaleziono nasunięcie: ";
	protected static final String LIST_ELEMENT_FORMAT = " %s: { %s } ";
	protected static final String COMPUTER_PICKED_COLOR = "#> Komputer wybrał kolor: %s";
	protected static final String COMPUTER_PICKED_INDEX = "\n#> Komputer wybrał indeks: %s";
	protected static final String COMPUTER_LOST = "#> Komputer nie był w stanie znaleźć odpowiedniego koloru. Wygrałeś!";
	protected static final String POINTS_MESSAGE = "\n#> Rozrgrywka trwała %s ruchów.";

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
		int index =0;
		for(int i=0;i<sequence.size();i++) {
			System.out.print(String.format(LIST_ELEMENT_FORMAT, index++, sequence.get(i)));
		}
		System.out.print(String.format(LIST_ELEMENT_FORMAT, sequence.size(), " "));
	}

	protected void printSubsequence(Subsequence subsequence, int moveBy) {
		int index = subsequence.getBegin();
		index += moveBy;
		int subsequenceEnd = subsequence.getBegin() + subsequence.getLength();
		for(int i=subsequence.getBegin();i<subsequenceEnd;i++) {
			System.out.print(String.format(LIST_ELEMENT_FORMAT, index++, sequence.get(i)));
		}
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

	protected void humanBuilderPCPainterGetNumber() {
		boolean invalidInput = true;

		while(invalidInput) {
			int index = getValueFromUser(GET_INDEX_MESSAGE);
			if(invalidIndex(index)) {
				System.out.println(INVALID_INDEX);
				invalidInput = true;
			} else {
				builderIndex = index;
				invalidInput = false;
			}
		}
	}

	protected int humanPainterPcBuilder() {
		boolean invalidInput = true;

		while(invalidInput) {
			int number = getValueFromUser(GET_VALUE_MESSAGE);
			if(invalidNumber(number)) {
				System.out.println(INVALID_NUMBERS);
				invalidInput = true;
			} else {
				return number;
			}
		}
		return -1;
	}

	protected void handleGameModes(GameMode gameMode) throws Exception {
		if(gameMode == GameMode.humanHuman) {
			addNumberToSequence();
		} else if(gameMode == GameMode.humanBuilder) {
			humanBuilder();
		} else if(gameMode == GameMode.pcBuilder) {
			pcBuilder();
		} else if(gameMode == GameMode.pcPc) {
			pcPc();
		} else {
			throw new Exception();
		}
	}

	protected void humanBuilder() {
		humanBuilderPCPainterGetNumber();
		int color = computerOpponent.findRightColorPredicting(sequence, builderIndex);
		if(color > -1) {
			System.out.println(String.format(COMPUTER_PICKED_COLOR, color));
			sequence.add(builderIndex, color);
		} else {
			System.out.println(COMPUTER_LOST);
			sequence.add(builderIndex, 0);
		}
	}



	protected void pcBuilder() {
		int index = computerOpponent.findRightIndex(sequence);
		System.out.println(String.format(COMPUTER_PICKED_INDEX, index));
		int number = humanPainterPcBuilder();
		sequence.add(index, number);
	}

	protected void pcPc() {
		builderIndex = computerOpponent.findRightIndex(sequence);
		System.out.println(String.format(COMPUTER_PICKED_INDEX, builderIndex));
		int color = computerOpponent.findRightColorPredicting(sequence, builderIndex);
		System.out.println(String.format(COMPUTER_PICKED_COLOR, color));

		sequence.add(builderIndex, color);

	}



	private boolean invalidIndex(int index) {
		return index < 0 || index > sequence.size();
	}

	private boolean invalidNumber(int number) {
		return number < 0 || number > power-1;
	}

	private boolean invalidNumbers(int index, int number) {
		return invalidIndex(index) || invalidNumber(number);
	}

	public abstract void startGame(GameMode gameMode);

	protected abstract void gameLoop(GameMode gameMode) throws Exception;
}
