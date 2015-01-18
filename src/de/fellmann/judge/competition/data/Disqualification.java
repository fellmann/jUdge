
package de.fellmann.judge.competition.data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlValue;

public class Disqualification
{
	private Competitor competitor;
	private String reason;

	@XmlIDREF
	@XmlAttribute
	public Competitor getCompetitor()
	{
		return competitor;
	}

	public void setCompetitor(Competitor competitor)
	{
		this.competitor = competitor;
	}

	@XmlValue
	public String getReason()
	{
		return reason;
	}

	public void setReason(String reason)
	{
		this.reason = reason;
	}
}
