package de.fellmann.judge.competition.data;

import de.fellmann.common.Nationality;

public class Club
{
	private String name;
	private Nationality nationality;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Nationality getNationality()
	{
		return nationality;
	}

	public void setNationality(Nationality nationality)
	{
		this.nationality = nationality;
	}
}
