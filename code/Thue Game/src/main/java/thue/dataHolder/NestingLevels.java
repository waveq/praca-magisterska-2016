package thue.dataHolder;

public class NestingLevels {

	int builderNestingLevel = 0;
	int painterNestingLevel = 0;

	public NestingLevels(int builderNestingLevel, int painterNestingLevel) {
		this.builderNestingLevel = builderNestingLevel;
		this.painterNestingLevel = painterNestingLevel;
	}

	public int getBuilderNestingLevel() {
		return builderNestingLevel;
	}

	public int getPainterNestingLevel() {
		return painterNestingLevel;
	}

	public void setPainterNestingLevel(int painterNestingLevel) {
		this.painterNestingLevel = painterNestingLevel;
	}

	public void setBuilderNestingLevel(int builderNestingLevel) {
		this.builderNestingLevel= builderNestingLevel;
	}
}
