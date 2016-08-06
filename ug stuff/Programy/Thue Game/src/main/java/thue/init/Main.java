package thue.init;

import thue.dataHolder.ConfigHolder;
import thue.dataHolder.NestingLevels;
import thue.gameConfig.ConfigRetriever;
import thue.gameConfig.GameMode;
import thue.gameConfig.ResultWriter;
import thue.gameLogic.AbstractFree;
import thue.gameLogic.OverlapFree;
import thue.gameLogic.SquareFree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	private static final String SQUARE = "Square";
	private static final String OVERLAP = "Overlap";
	private static final String SLEEP_ERROR_MESSAGE = "Error during sleep";

	public static void main (String[] args) {
		startGame();
	}

	private static void startGame() {
		if(!ConfigRetriever.isMakeOverallTest() && !ConfigRetriever.isMakeQueueTest()) {
			startGameRegular();
		}

		if(ConfigRetriever.isMakeQueueTest()) {
			executeQueue();
		}

		if(ConfigRetriever.isMakeOverallTest()) {
			performLoops(SQUARE);
			performLoops(OVERLAP);
		}

	}

	private static void executeQueue() {
		List<ConfigHolder> executionQueue = Arrays.asList(
				new ConfigHolder().builderLevel(1).painterLevel(1).gameType(SQUARE).gameMode(GameMode.pcPc).powLvl(5)
		);
		executionQueue.forEach(elem -> executeGame(elem));
	}

	private static void performLoops(String gameType) {
		for(int builder = 0;builder<7;builder++) {
			for (int painter = 0;painter<7;painter++) {
				for (int powlvl = 1; powlvl<8;powlvl++) {

					if(ignoreBelowLevel(1, 6, builder, painter)) {
						continue;
					}
					ConfigHolder configHolder =
							new ConfigHolder().builderLevel(builder).painterLevel(painter).gameType(gameType).gameMode(GameMode.pcPc).powLvl(powlvl);

					executeGame(configHolder);
				}
			}
		}
	}

	private static void executeGame(ConfigHolder configHolder) {
		String gameType = configHolder.getGameType();
		String gameMode = configHolder.getGameMode().getGameModeName();
		int powlvl = configHolder.getPowlvl();
		int painter = configHolder.getNestingLevels().getPainterNestingLevel();
		int builder = configHolder.getNestingLevels().getBuilderNestingLevel();

		ResultWriter.initConfigValues(gameType, gameMode, powlvl, builder, painter);

		AbstractFree t = null;
		if(gameType == SQUARE) {
			t = new SquareFree(new NestingLevels(builder, painter), powlvl, GameMode.pcPc);
		}
		if(gameType == OVERLAP) {
			t = new OverlapFree(new NestingLevels(builder, painter), powlvl, GameMode.pcPc);
		}
		if(t != null) {
			t.startGame();
		}

		ResultWriter.closeStream();
		sleepFor(2);
	}


	private static boolean ignoreBelowLevel(int ignoreBuilder, int ignorePainter, int currentBuilder, int currentPainter) {
		if(currentBuilder <= ignoreBuilder && currentPainter <= ignorePainter) {
			return true;
		}
		return false;
	}

	private static  void sleepFor(int seconds) {
		try {
			Thread.sleep(1000*seconds);
		} catch (InterruptedException e) {
			System.out.println(SLEEP_ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private static void startGameRegular() {
		int builder = -1;
		int painter = -1;
		if(ConfigRetriever.getGameMode() == GameMode.pcPc || ConfigRetriever.getGameMode() == GameMode.pcBuilder) {
			builder = ConfigRetriever.getBuilderNestingLevel();
		}
		if(ConfigRetriever.getGameMode() == GameMode.pcPc || ConfigRetriever.getGameMode() == GameMode.humanBuilder) {
			painter = ConfigRetriever.getPainterNestingLevel();
		}
		ResultWriter.initConfigValues(ConfigRetriever.getGameType(), ConfigRetriever.getGameMode().getGameModeName(), ConfigRetriever.getSetPower(), builder, painter);

		AbstractFree t = getGameType();
		if(t != null) {
			t.startGame();
		}

		ResultWriter.closeStream();
	}

	private static AbstractFree getGameType() {
		String gameType = ConfigRetriever.getGameType();
		if(gameType.equals(SQUARE)) {
			return new SquareFree(ConfigRetriever.getNestingLevels(), ConfigRetriever.getSetPower(), ConfigRetriever.getGameMode());
		}
		if(gameType.equals(OVERLAP)) {
			return new OverlapFree(ConfigRetriever.getNestingLevels(), ConfigRetriever.getSetPower(), ConfigRetriever.getGameMode());
		}

		return null;
	}
}
