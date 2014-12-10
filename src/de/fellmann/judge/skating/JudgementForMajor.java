package de.fellmann.judge.skating;

public interface JudgementForMajor {
	public abstract int getCompetitors();

	public abstract int getDances();

	public abstract int getJudges();

	public abstract boolean isSet(int competitor);

	public abstract byte getJudgement(int competitor, int judge);

}