package de.fellmann.judge.competition.data;

public class DanceCompetitorKey
{
	private Dance dance;
	private Competitor competitor;

	public DanceCompetitorKey(Dance dance, Competitor competitor)
	{
		this.dance = dance;
		this.competitor = competitor;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((competitor == null) ? 0 : competitor.hashCode());
		result = prime * result + ((dance == null) ? 0 : dance.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DanceCompetitorKey other = (DanceCompetitorKey) obj;
		if (competitor == null)
		{
			if (other.competitor != null)
				return false;
		}
		else if (!competitor.equals(other.competitor))
			return false;
		if (dance == null)
		{
			if (other.dance != null)
				return false;
		}
		else if (!dance.equals(other.dance))
			return false;
		return true;
	}
	public Dance getDance()
	{
		return dance;
	}
	public void setDance(Dance dance)
	{
		this.dance = dance;
	}
	public Competitor getCompetitor()
	{
		return competitor;
	}
	public void setCompetitor(Competitor competitor)
	{
		this.competitor = competitor;
	}
}
