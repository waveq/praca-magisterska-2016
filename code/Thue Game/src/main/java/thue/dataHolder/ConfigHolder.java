package thue.dataHolder;

import thue.gameConfig.GameMode;

public class ConfigHolder {


	private String gameType;
	private GameMode gameMode;
	private int powlvl;
	private NestingLevels nestingLevels = new NestingLevels(0, 0);


	public ConfigHolder gameType(String gameType) {
		this.gameType = gameType;
		return this;
	}

	public ConfigHolder gameMode(GameMode gameMode) {
		this.gameMode = gameMode;
		return this;
	}

	public ConfigHolder powLvl(int powlvl) {
		this.powlvl = powlvl;
		return this;
	}

	public ConfigHolder builderLevel(int builderLevel) {
		this.nestingLevels.setBuilderNestingLevel(builderLevel);
		return this;
	}

	public ConfigHolder painterLevel(int painterLevel) {
		this.nestingLevels.setPainterNestingLevel(painterLevel);
		return this;
	}

	public String getGameType() {
		return gameType;
	}

	public GameMode getGameMode() {
		return gameMode;
	}

	public int getPowlvl() {
		return powlvl;
	}

	public NestingLevels getNestingLevels() {
		return nestingLevels;
	}
}
