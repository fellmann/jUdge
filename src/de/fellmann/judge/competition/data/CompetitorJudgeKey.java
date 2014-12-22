package de.fellmann.judge.competition.data;

public class CompetitorJudgeKey
{
	private Competitor competitor;
	private Judge judge;
	
	public CompetitorJudgeKey(Competitor competitor, Judge judge)
	{
		this.competitor = competitor;
		this.judge = judge;
	}
	public Competitor getCompetitor()
	{
		return competitor;
	}
	public void setCompetitor(Competitor competitor)
	{
		this.competitor = competitor;
	}
	public Judge getJudge()
	{
		return judge;
	}
	public void setJudge(Judge judge)
	{
		this.judge = judge;
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((competitor == null) ? 0 : competitor.hashCode());
		result = prime * result + ((judge == null) ? 0 : judge.hashCode());
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
		CompetitorJudgeKey other = (CompetitorJudgeKey) obj;
		if (competitor == null)
		{
			if (other.competitor != null)
				return false;
		}
		else if (!competitor.equals(other.competitor))
			return false;
		if (judge == null)
		{
			if (other.judge != null)
				return false;
		}
		else if (!judge.equals(other.judge))
			return false;
		return true;
	}
	
}
