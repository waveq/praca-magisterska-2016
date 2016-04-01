package Thue.GameConfig;


public enum GameMode {
	humanHuman("humanHuman"),
	humanBuilder("humanBuilder"),
	pcBuilder("pcBuilder"),
	pcPc("pcPc");

	private String gameModeName;

	GameMode(String gameModeName) {
		this.gameModeName = gameModeName;
	}

	public String getGameModeName() {
		return gameModeName;
	}

	public static GameMode forGameModeName(String gameModeName) {
		for (GameMode gameMode : GameMode.values()) {
			if(gameMode.getGameModeName().equals(gameModeName)) {
				return gameMode;
			}
		}
		return null;
	}
}
