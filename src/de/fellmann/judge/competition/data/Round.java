
package de.fellmann.judge.competition.data;

import java.util.HashMap;
import java.util.Map;

public class Round extends DataObject
{
	private Competition competition;
	private ResultData resultProvider;
	private Map<Competitor, Boolean> disqualified = new HashMap<Competitor, Boolean>();
	private RoundType roundType;
	
	public Round()
	{
	}

	public ResultData getResultData()
	{
		return resultProvider;
	}

	public void setResultData(ResultData resultProvider)
	{
		this.resultProvider = resultProvider;
	}

	public Map<Competitor, Boolean> getDisqualified()
	{
		return disqualified;
	}

	public void setDisqualified(Map<Competitor, Boolean> disqualified)
	{
		this.disqualified = disqualified;
	}

	public Competition getCompetition()
	{
		return competition;
	}

	public void setCompetition(Competition competition)
	{
		this.competition = competition;
	}

	public RoundType getRoundType()
	{
		return roundType;
	}

	public void setRoundType(RoundType roundType)
	{
		this.roundType = roundType;
	}
}
