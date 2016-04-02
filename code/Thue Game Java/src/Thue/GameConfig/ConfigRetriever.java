package Thue.GameConfig;

import java.io.FileInputStream;
import java.io.InputStream;

public class ConfigRetriever {

	public static final String GAME_MODE_PROPERTY = "gameMode";
	public static final String GAME_TYPE_PROPERTY = "gameType";
	public static final String GAME_DIFFICULTY_PROPERTY = "gameDifficulty";

	private static final String PROPERTIES_FILE = "src/Thue/GameConfig/config.properties";
	private static final String ERROR_MESSAGE = "Probably there is no such property %s";

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

	public static int getGameDifficulty() {
		return Integer.parseInt(getProperty(GAME_DIFFICULTY_PROPERTY));
	}

	public static String getGameType() {
		return getProperty(GAME_TYPE_PROPERTY);
	}

	public static GameMode getGameMode() {
		return GameMode.forGameModeName(getProperty(GAME_MODE_PROPERTY));
	}
}