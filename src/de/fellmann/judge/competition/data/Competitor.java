
package de.fellmann.judge.competition.data;

public class Competitor extends DataObject
{
	private int countStarRounds;
	private CompetitorState state;
	private Club club;
	private String name;
	private int points;
	private int placings;
	
	public int getCountStarRounds()
	{
		return countStarRounds;
	}

	public void setCountStarRounds(int countStarRounds)
	{
		this.countStarRounds = countStarRounds;
	}

	public CompetitorState getState()
	{
		return state;
	}

	public void setState(CompetitorState state)
	{
		this.state = state;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getPoints()
	{
		return points;
	}

	public void setPoints(int points)
	{
		this.points = points;
	}

	public int getPlacings()
	{
		return placings;
	}

	public void setPlacings(int placings)
	{
		this.placings = placings;
	}

	public Club getClub()
	{
		return club;
	}

	public void setClub(Club club)
	{
		this.club = club;
	}
}
