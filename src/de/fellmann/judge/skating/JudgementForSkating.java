package de.fellmann.judge.skating;


public class JudgementForSkating implements JudgementForMajor {
	private final JudgementForFinal judgement;
	
	public JudgementForSkating(JudgementForFinal judgement) {
		this.judgement = judgement;
	}

	public JudgementForFinal getJudgement() {
		return judgement;
	}
	
	@Override
	public int getCompetitors() {
		return judgement.getCompetitors();
	}
	
	@Override
	public int getDances() {
		return 1;
	}
	
	@Override
	public int getJudges() {
		return judgement.getJudges()*judgement.getDances();
	}
	
	@Override
	public boolean isSet(int competitor)
	{
		return true;
	}

	@Override
	public byte getJudgement(int competitor, int judge) {
		return judgement.getJudgment(judge/judgement.getJudges(), competitor, judge%judgement.getJudges());
	}
}
