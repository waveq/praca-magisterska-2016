package thue.init;

import thue.dataHolder.NestingLevels;
import thue.gameConfig.ConfigRetriever;
import thue.gameConfig.GameMode;
import thue.gameConfig.ResultWriter;
import thue.gameLogic.AbstractFree;
import thue.gameLogic.OverlapFree;
import thue.gameLogic.SquareFree;

public class Main {
	private static final String SQUARE = "Square";
	private static final String OVERLAP = "Overlap";

	public static void main (String[] args) {
		startGame();
	}

	private static void startGame() {
		if(!ConfigRetriever.isMakeOverallTest()) {
			startGameRegular();
		} else {
			performLoops(SQUARE);
			performLoops(OVERLAP);
		}
	}

	private static void performLoops(String gameType) {
		for(int builder = 0;builder<7;builder++) {
			for (int painter = 0;painter<7;painter++) {
				for (int powlvl = 1; powlvl<8;powlvl++) {
					ResultWriter.initConfigValues(gameType, GameMode.pcPc.getGameModeName(), powlvl);

					AbstractFree t = null;
					if(gameType == SQUARE) {
						t = new SquareFree(new NestingLevels(builder, painter), powlvl);
					}
					if(gameType == OVERLAP) {
						t = new OverlapFree(new NestingLevels(builder, painter), powlvl);
					}
					if(t != null) {
						t.startGame(GameMode.pcPc);
					}

					ResultWriter.closeStream();
				}
			}
		}
	}

	private static void startGameRegular() {
		ResultWriter.initConfigValues(ConfigRetriever.getGameType(), ConfigRetriever.getGameMode().getGameModeName(),ConfigRetriever.getSetPower());

		AbstractFree t = getGameType();
		if(t != null) {
			t.startGame(ConfigRetriever.getGameMode());
		}

		ResultWriter.closeStream();
	}

	private static AbstractFree getGameType() {
		String gameType = ConfigRetriever.getGameType();
		if(gameType.equals(SQUARE)) {
			return new SquareFree(ConfigRetriever.getNestingLevels(), ConfigRetriever.getSetPower());
		}
		if(gameType.equals(OVERLAP)) {
			return new OverlapFree(ConfigRetriever.getNestingLevels(), ConfigRetriever.getSetPower());
		}

		return null;
	}
}
