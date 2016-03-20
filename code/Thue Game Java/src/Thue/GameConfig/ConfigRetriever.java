package Thue.GameConfig;

import java.io.FileInputStream;
import java.io.InputStream;

public class ConfigRetriever {

	public static final String GAME_MODE_PROPERTY = "gameMode";
	public static final String GAME_TYPE_PROPERTY = "gameType";

	private static final String PROPERTIES_FILE = "src/Thue/GameConfig/config.properties";
	private static final String ERROR_MESSAGE = "Probably there is no such property %s";
	private static final String HUMAN_HUMAN = "humanHuman";
	private static final String HUMAN_BUILDER = "humanBuilder";
	private static final String PC_BUILDER = "pcBuilder";
	private static final String PC_PC = "pcPc";

	private static String getProperty(String propertyKey) {
		try {
			InputStream is = new FileInputStream(PROPERTIES_FILE);
			java.util.Properties prop = new java.util.Properties();
			prop.load(is);
			return prop.getProperty(propertyKey);
		} catch (Exception e) {
			System.out.println(String.format(ERROR_MESSAGE, propertyKey));
			return null;
		}
	}

	public static String getGameType() {
		return getProperty(GAME_TYPE_PROPERTY);
	}

	public static GameMode getGameMode() {
		if(getProperty(GAME_MODE_PROPERTY).equals(HUMAN_HUMAN)) {
			return GameMode.humanHuman;
		}
		if(getProperty(GAME_MODE_PROPERTY).equals(HUMAN_BUILDER)) {
			return GameMode.humanBuilder;
		}
		if(getProperty(GAME_MODE_PROPERTY).equals(PC_BUILDER)) {
			return GameMode.pcBuilder;
		}
		if(getProperty(GAME_MODE_PROPERTY).equals(PC_PC)) {
			return GameMode.pcPc;
		}
		else {
			return GameMode.notFound;
		}
	}
}
