package Thue.GameConfig;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultWriter {

	private static final String OUTPUT_PATH = "output/";
	private static final String UTF8 = "UTF-8";
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
	private static int setPower = 0;
	private static String gameMode = "";
	private static String gameType = "";


	private static FileWriter fileWriter;
	private static BufferedWriter writer;

	public static void writelnToFile(String text) {
		try {
			initWriter();
			writeNewLine(writer, text);
		} catch (Exception e) {
			System.out.println("Something went wrong during writing logs to file.");
		}
	}

	public static void writeToFile(String text) {
		try {
			initWriter();
			writeinOneLine(writer, text);
		} catch (Exception e) {
			System.out.println("Something went wrong during writing logs to file.");
		}
	}

	public static void writeEmptyLine() {
		try {
			initWriter();
			writeEmptyLine(writer);
		} catch (Exception e) {
			System.out.println("Something went wrong during writing logs to file.");
		}
	}

	public static void closeStream() {
		try {
			writer.close();
		} catch (IOException e) {
			System.out.println("Cannot close stream.");
		}
	}

	private static void initWriter() throws Exception {
		if(writer == null) {
			File file = new File(OUTPUT_PATH+generateFileName());
			fileWriter = new FileWriter(file, true);
			writer = new BufferedWriter(fileWriter);
		}
	}

	private static String generateFileName() {
		initConfigValues();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String date = sdf.format(new Date());
		return String.format("%s %s %s %s colors.txt", date, gameType, gameMode, setPower);
	}

	private static void initConfigValues() {
		gameType = ConfigRetriever.getGameType();
		gameMode = ConfigRetriever.getGameMode().getGameModeName();
		setPower = ConfigRetriever.getSetPower();
	}

	private static void writeNewLine(BufferedWriter writer, String text) throws Exception {
		writer.write(text);
		writer.newLine();
	}

	private static void writeinOneLine(BufferedWriter writer, String text) throws Exception {
		writer.write(text);
	}

	private static void writeEmptyLine(BufferedWriter writer) throws Exception {
		writer.newLine();
	}
}
