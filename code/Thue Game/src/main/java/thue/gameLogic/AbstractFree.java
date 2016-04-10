package thue.gameLogic;

import thue.algorithm.ComputerOpponent;
import thue.dataHolder.Subsequence;
import thue.gameConfig.GameMode;
import thue.dataHolder.NestingLevels;
import thue.gameConfig.ResultWriter;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public abstract class AbstractFree {

	protected int power;
	protected int builderIndex;
	protected boolean finished = false;
	private NestingLevels nestingLevels;


	protected Scanner lineReader = new Scanner(System.in);
	protected List<Integer> numberSet = new ArrayList<>();
	protected List<Integer> sequence = new ArrayList<>();
	protected ComputerOpponent computerOpponent;

	private static final String SET_SIZE_MESSAGE = "#> Podaj moc zbioru: ";
	private static final String AVAILABLE_NUMBERS_MESSAGE = "#> W grze dostępne są następujące liczby: ";
	private static final String GET_INDEX_MESSAGE = "\n#> Podaj indeks: ";
	private static final String GET_VALUE_MESSAGE = "#> Podaj liczbę: ";
	private static final String LINE = "## ============== ##";
	private static final String INVALID_NUMBERS = "#> Wprowadziłeś niepoprawne liczby, popraw się!";
	private static final String INVALID_INDEX = "#> Wprowadziłeś niepoprawny indeks, popraw się!";
	private static final String INVALID_NUMBER = "#> Wprowadziłeś niepoprawną liczbę, popraw się!";
	private static final String BUILDER_LEVEL = "#> Poziom budowniczego ustawiono na: %s";
	private static final String PAINTER_LEVEL = "#> Poziom malarza ustawiono na: %s";
	private static final String PAINTER = "painter";
	private static final String BUILDER = "builder";

	protected static final String FIRST_NUMBER_MESSAGE = "#> Podaj pierwszą liczbę: ";
	protected static final String SQUARE_FOUND = "\n#> Znaleziono kwadrat: ";
	protected static final String OVERLAP_FOUND = "\n#> Znaleziono nasunięcie: ";
	protected static final String LIST_ELEMENT_FORMAT = " %s: { %s } ";
	protected static final String COMPUTER_PICKED_COLOR = "#> Komputer wybrał kolor: %s \t| Czas trwania obliczeń: %s s";
	protected static final String COMPUTER_PICKED_INDEX = "\n#> Komputer wybrał indeks: %s \t| Czas trwania obliczeń: %s s";
	protected static final String COMPUTER_LOST = "#> Komputer nie był w stanie znaleźć odpowiedniego koloru. Wygrałeś!";
	protected static final String TIMEOUT_MESSAGE = "\n#> Przekroczono limit czasu oczekiwania na decyzje komputera: %s s";
	protected static final String POINTS_MESSAGE = "\n#> Rozrgrywka trwała %s ruchów.";

	public AbstractFree(NestingLevels nestingLevels, int power) {
		this.power = power;
		this.nestingLevels = nestingLevels;
		writeConfig();
		populateNumberSet(power);
		printNumberSet();
	}

	private void writeConfig() {
		printlnAndLog(String.format(BUILDER_LEVEL, nestingLevels.getBuilderNestingLevel()));
		printlnAndLog(String.format(PAINTER_LEVEL, nestingLevels.getPainterNestingLevel()));
	}

	protected int getValueFromUser(String message) {
		System.out.println(message);
		return lineReader.nextInt();
	}

	protected void printNumberSet() {
		printlnAndLog(AVAILABLE_NUMBERS_MESSAGE);
		numberSet.forEach(elem -> printlnAndLog(elem.toString()));
	}

	private void populateNumberSet(int power) {
		for(int i=0;i<power;i++) {
			numberSet.add(i);
		}
	}

	protected void printSequence(List<Integer> sequence) {
		printlnAndLog(LINE);
		int index =0;
		for(int i=0;i<sequence.size();i++) {
			printAndLog(String.format(LIST_ELEMENT_FORMAT, index++, sequence.get(i)));
		}
		printAndLog(String.format(LIST_ELEMENT_FORMAT, sequence.size(), " "));
	}

	protected void printSubsequence(Subsequence subsequence, int moveBy) {
		int index = subsequence.getBegin();
		index += moveBy;
		int subsequenceEnd = subsequence.getBegin() + subsequence.getLength();
		for(int i=subsequence.getBegin();i<subsequenceEnd;i++) {
			printAndLog(String.format(LIST_ELEMENT_FORMAT, index++, sequence.get(i)));
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
		computerOpponent.startTime(PAINTER);

		int color = computerOpponent.findRightColorPredicting(sequence, builderIndex);
		if(color > -1) {
			printlnAndLog(String.format(COMPUTER_PICKED_COLOR, color, convertNanoSecondsToSeconds(computerOpponent.getTime(PAINTER))));
			sequence.add(builderIndex, color);
		} else {
			printlnAndLog(String.format(TIMEOUT_MESSAGE, convertNanoSecondsToSeconds(computerOpponent.getTime(PAINTER))));
			finished = true;
			return;
		}
	}

	private double convertNanoSecondsToSeconds(Long nano) {
		double value =  (double)nano / 1000000000.0;
		return  Math.round( value * 1000.0 ) / 1000.0;
	}

	protected void printlnAndLog(String text) {
		System.out.println(text);
		ResultWriter.writelnToFile(text);
	}

	protected void printAndLog(String text) {
		System.out.print(text);
		ResultWriter.writeToFile(text);
	}

	protected void pcBuilder() {
		computerOpponent.startTime(BUILDER);
		int index = computerOpponent.findRightIndex(sequence);
		if(index > -1) {
			printlnAndLog(String.format(COMPUTER_PICKED_INDEX, index, convertNanoSecondsToSeconds(computerOpponent.getTime(BUILDER))));
			int number = humanPainterPcBuilder();
			sequence.add(index, number);
		} else {
			printlnAndLog(String.format(TIMEOUT_MESSAGE, convertNanoSecondsToSeconds(computerOpponent.getTime(BUILDER))));
			finished = true;
			return;
		}
	}

	protected void pcPc() {
		computerOpponent.startTime(BUILDER);
		builderIndex = computerOpponent.findRightIndex(sequence);
		if(builderIndex > -1) {
			printlnAndLog(String.format(String.format(COMPUTER_PICKED_INDEX, builderIndex, convertNanoSecondsToSeconds(computerOpponent.getTime(BUILDER)))));
		} else {
			printlnAndLog(String.format(TIMEOUT_MESSAGE, convertNanoSecondsToSeconds(computerOpponent.getTime(BUILDER))));
			finished = true;
			return;
		}

		computerOpponent.startTime(PAINTER);
		int color = computerOpponent.findRightColorPredicting(sequence, builderIndex);
		if(color > -1) {
			printlnAndLog(String.format(COMPUTER_PICKED_COLOR, color, convertNanoSecondsToSeconds(computerOpponent.getTime(PAINTER))));
			sequence.add(builderIndex, color);
		} else {
			printlnAndLog(String.format(TIMEOUT_MESSAGE, convertNanoSecondsToSeconds(computerOpponent.getTime(PAINTER))));
			finished = true;
			return;
		}

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
