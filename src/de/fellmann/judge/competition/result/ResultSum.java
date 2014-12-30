package de.fellmann.judge.competition.result;

import de.fellmann.judge.PlaceSum;
import de.fellmann.judge.competition.data.Competitor;

public class ResultSum
{
	private Competitor competitor;
	private PlaceSum placeSum;
	
	public ResultSum(Competitor competitor, PlaceSum placeSum)
	{
		this.competitor = competitor;
		this.placeSum = placeSum;
	}
	
	public ResultSum()
	{
	}
	
	public Competitor getCompetitor()
	{
		return competitor;
	}
	public void setCompetitor(Competitor competitor)
	{
		this.competitor = competitor;
	}
	public PlaceSum getPlaceSum()
	{
		return placeSum;
	}
	public void setPlaceSum(PlaceSum placeSum)
	{
		this.placeSum = placeSum;
	}
}
