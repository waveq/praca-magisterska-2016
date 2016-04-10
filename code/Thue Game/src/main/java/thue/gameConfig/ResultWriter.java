package thue.gameConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultWriter {

	private static final String OUTPUT_PATH = "output/";
	private static final String UTF8 = "UTF-8";
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String EXCEPTION_MESSAGE = "Coś poszło nie tak podczas zapisywania danych do pliku.";

	private static String gameMode = "";
	private static String gameType = "";
	private static int setPower = 0;

	private static FileWriter fileWriter;
	private static BufferedWriter writer;

	public static void writelnToFile(String text) {
		try {
			initWriter();
			writeNewLine(writer, text);
		} catch (Exception e) {
			System.out.println(EXCEPTION_MESSAGE);
			e.printStackTrace();
		}
	}

	public static void writeToFile(String text) {
		try {
			initWriter();
			writeinOneLine(writer, text);
		} catch (Exception e) {
			System.out.println(EXCEPTION_MESSAGE);
			e.printStackTrace();
		}
	}

	public static void closeStream() {
		try {
			writer.close();
		} catch (IOException e) {
			System.out.println("Cannot close stream.");
			e.printStackTrace();
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
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String date = sdf.format(new Date());
		return String.format("%s %s %s %s colors.txt", date, gameType, gameMode, setPower);
	}

	public static void initConfigValues(String gameType, String gameMode, int setPower) {
		ResultWriter.gameType =  gameType;
		ResultWriter.gameMode = gameMode;
		ResultWriter.setPower = setPower;
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
