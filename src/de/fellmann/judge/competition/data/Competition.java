package de.fellmann.judge.competition.data;

import java.util.ArrayList;

public class Competition extends DataObject
{
	private ArrayList<Round> rounds = new ArrayList<Round>();
	private ArrayList<Competitor> competitors = new ArrayList<Competitor>();
	private ArrayList<Judge> judges = new ArrayList<Judge>();
	private ArrayList<Dance> dances = new ArrayList<Dance>();
	private DisqualificationMode disqualificationMode = DisqualificationMode.DISQUALIFIED_LEAVE_EMPTY;

	public ArrayList<Round> getRounds()
	{
		return rounds;
	}

	public void setRounds(ArrayList<Round> rounds)
	{
		this.rounds = rounds;
	}

	public ArrayList<Competitor> getCompetitors()
	{
		return competitors;
	}

	public void setCompetitors(ArrayList<Competitor> competitors)
	{
		this.competitors = competitors;
	}

	public ArrayList<Dance> getDances()
	{
		return dances;
	}

	public void setDances(ArrayList<Dance> dances)
	{
		this.dances = dances;
	}

	public ArrayList<Judge> getJudges()
	{
		return judges;
	}

	public void setJudges(ArrayList<Judge> judges)
	{
		this.judges = judges;
	}

	public DisqualificationMode getDisqualificationMode()
	{
		return disqualificationMode;
	}

	public void setDisqualificationMode(DisqualificationMode disqualificationMode)
	{
		this.disqualificationMode = disqualificationMode;
	}
}
