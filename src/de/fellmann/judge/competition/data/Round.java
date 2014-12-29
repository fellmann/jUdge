
package de.fellmann.judge.competition.data;

import java.util.HashMap;

public class Round extends DataObject
{
	private ResultData resultProvider;
	private HashMap<Competitor, Boolean> disqualified = new HashMap<Competitor, Boolean>();
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

	public HashMap<Competitor, Boolean> getDisqualified()
	{
		return disqualified;
	}

	public void setDisqualified(HashMap<Competitor, Boolean> disqualified)
	{
		this.disqualified = disqualified;
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
