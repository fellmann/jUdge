package de.fellmann.judge.competition.result;

import de.fellmann.judge.Place;
import de.fellmann.judge.competition.data.Competitor;
import de.fellmann.judge.competition.data.Dance;

public class Result
{
	private Competitor competitor;
	private Place place;
	
	public Result(Competitor competitor, Place place)
	{
		this.competitor = competitor;
		this.place = place;
	}
	
	public Result()
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
	public Place getPlace()
	{
		return place;
	}
	public void setPlace(Place place)
	{
		this.place = place;
	}
}
