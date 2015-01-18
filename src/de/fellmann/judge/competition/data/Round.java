
package de.fellmann.judge.competition.data;

import java.util.ArrayList;

public class Round extends DataObject
{
	private ResultData resultProvider;
	private ArrayList<Disqualification> disqualified = new ArrayList<Disqualification>();
	private RoundType roundType;
	private Drawing drawing;

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

	public RoundType getRoundType()
	{
		return roundType;
	}

	public void setRoundType(RoundType roundType)
	{
		this.roundType = roundType;
	}

	public Drawing getDrawing()
	{
		return drawing;
	}

	public void setDrawing(Drawing drawing)
	{
		this.drawing = drawing;
	}

	public ArrayList<Disqualification> getDisqualified()
	{
		return disqualified;
	}

	public void setDisqualified(ArrayList<Disqualification> disqualified)
	{
		this.disqualified = disqualified;
	}
}
