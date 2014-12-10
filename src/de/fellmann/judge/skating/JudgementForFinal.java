package de.fellmann.judge.skating;


public class JudgementForFinal {
	final private int competitors;
	final private int judges;
	final private int dances;
	final private byte[][][] judgements;

	public JudgementForFinal(int dances, int competitors, int judges) {
		this.dances = dances;
		this.competitors = competitors;
		this.judges = judges;

		judgements = new byte[dances][competitors][judges];
	}

	public int getCompetitors() {
		return competitors;
	}

	public int getJudges() {
		return judges;
	}

	public int getDances() {
		return dances;
	}

	public void setJudgment(int dance, int competitor, int judge, Byte value) {
		judgements[dance][competitor][judge] = value;
	}

	public Byte getJudgment(int dance, int competitor, int judge) {
		return judgements[dance][competitor][judge];
	}
	
	public boolean isSet(int dance, int competitor)
	{
		// TODO check in judgement
		return true;
	}
}
