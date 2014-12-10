package de.fellmann.judge.skating;


public class JudgementForDance implements JudgementForMajor {
	private final JudgementForFinal judgement;
	private final int dance;
	
	public JudgementForDance(JudgementForFinal judgement, int dance) {
		this.judgement = judgement;
		this.dance = dance;
	}

	public JudgementForFinal getJudgement() {
		return judgement;
	}

	public int getDance() {
		return dance;
	}
	
	@Override
	public int getCompetitors() {
		return judgement.getCompetitors();
	}
	
	@Override
	public int getDances() {
		return judgement.getDances();
	}
	
	@Override
	public int getJudges() {
		return judgement.getJudges();
	}
	
	@Override
	public boolean isSet(int competitor)
	{
		return judgement.isSet(dance, competitor);
	}

	@Override
	public byte getJudgement(int competitor, int judge) {
		return judgement.getJudgment(dance, competitor, judge);
	}
}
