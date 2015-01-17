
package de.fellmann.judge.competition.data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlValue;

public class Value
{
	private Dance dance;
	private Competitor competitor;
	private Judge judge;
	private int mark;

	public Value()
	{
	}

	public Value(Dance dance, Competitor competitor, Judge judge, int mark)
	{
		this.dance = dance;
		this.competitor = competitor;
		this.judge = judge;
		this.mark = mark;
	}

	@XmlIDREF
	@XmlAttribute(name = "d")
	public Dance getDance()
	{
		return dance;
	}

	public void setDance(Dance dance)
	{
		this.dance = dance;
	}

	@XmlIDREF
	@XmlAttribute(name = "c")
	public Competitor getCompetitor()
	{
		return competitor;
	}

	public void setCompetitor(Competitor competitor)
	{
		this.competitor = competitor;
	}

	@XmlIDREF
	@XmlAttribute(name = "j")
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
		result = prime * result + ((dance == null) ? 0 : dance.hashCode());
		result = prime * result + ((judge == null) ? 0 : judge.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final Value other = (Value) obj;
		if (competitor == null)
		{
			if (other.competitor != null)
			{
				return false;
			}
		}
		else if (!competitor.equals(other.competitor))
		{
			return false;
		}
		if (dance == null)
		{
			if (other.dance != null)
			{
				return false;
			}
		}
		else if (!dance.equals(other.dance))
		{
			return false;
		}
		if (judge == null)
		{
			if (other.judge != null)
			{
				return false;
			}
		}
		else if (!judge.equals(other.judge))
		{
			return false;
		}
		return true;
	}

	@XmlValue
	public int getMark()
	{
		return mark;
	}

	public void setMark(int mark)
	{
		this.mark = mark;
	}
}
