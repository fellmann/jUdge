package de.fellmann.judge.competition.result;

import java.util.ArrayList;

import de.fellmann.judge.competition.data.Competition;
import de.fellmann.judge.competition.data.Competitor;

public class CompetitionResult
{
	private ArrayList<RoundResult> roundResults = new ArrayList<RoundResult>();
	private ArrayList<Competitor> disqualified = new ArrayList<Competitor>();
	private Competition competition;
	
	public ArrayList<Competitor> getDisqualified()
	{
		return disqualified;
	}
	public void setDisqualified(ArrayList<Competitor> disqualified)
	{
		this.disqualified = disqualified;
	}

	public boolean getDisqualified(Competitor competitor)
	{
		return disqualified.contains(competitor);
	}

	public ArrayList<RoundResult> getRoundResults()
	{
		return roundResults;
	}

	public void setRoundResults(ArrayList<RoundResult> roundResults)
	{
		this.roundResults = roundResults;
	}
	public Competition getCompetition()
	{
		return competition;
	}
	public void setCompetition(Competition competition)
	{
		this.competition = competition;
	}
}
