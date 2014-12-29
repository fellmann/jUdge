package de.fellmann.judge.competition.data;

import javax.persistence.Entity;

public class Dance extends DataObject
{
	private String name, shortname;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getShortname()
	{
		return shortname;
	}

	public void setShortname(String shortname)
	{
		this.shortname = shortname;
	}
}
