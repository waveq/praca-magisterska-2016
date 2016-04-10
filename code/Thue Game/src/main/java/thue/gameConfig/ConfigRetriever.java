package thue.gameConfig;

import thue.dataHolder.NestingLevels;

import java.io.FileInputStream;
import java.io.InputStream;

public class ConfigRetriever {

	public static final String GAME_MODE_PROPERTY = "gameMode";
	public static final String GAME_TYPE_PROPERTY = "gameType";
	public static final String SET_POWER = "setPower";

	public static final String BUILDER_NESTING_LEVEL = "builderNestingLevel";
	public static final String PAINTER_NESTING_LEVEL = "painterNestingLevel";
	public static final String MAX_THINK_TIME = "maxThinkTime";

	private static final String PROPERTIES_FILE = "src/main/java/Thue/GameConfig/config.properties";
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

	public static Long getMaxThinkTime() {
		return Long.parseLong(getProperty(MAX_THINK_TIME));
	}

	public static int getBuilderNestingLevel() {
		return Integer.parseInt(getProperty(BUILDER_NESTING_LEVEL));
	}

	public static int getPainterNestingLevel() {
		return Integer.parseInt(getProperty(PAINTER_NESTING_LEVEL));
	}

	public static int getSetPower() {
		return Integer.parseInt(getProperty(SET_POWER));
	}

	public static NestingLevels getNestingLevels() {
		return new NestingLevels(getBuilderNestingLevel(), getPainterNestingLevel());
	}

	public static String getGameType() {
		return getProperty(GAME_TYPE_PROPERTY);
	}

	public static GameMode getGameMode() {
		return GameMode.forGameModeName(getProperty(GAME_MODE_PROPERTY));
	}
}
