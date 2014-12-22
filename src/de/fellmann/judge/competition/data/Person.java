package de.fellmann.judge.competition.data;

import de.fellmann.common.Nationality;

public class Person
{
	private String name = null;
	private String shortname = null;
	private boolean team = false;
	private Nationality nationality = null;
	private Club club = null;
	
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
	public boolean isTeam()
	{
		return team;
	}
	public void setTeam(boolean team)
	{
		this.team = team;
	}
	public Nationality getNationality()
	{
		return nationality;
	}
	public void setNationality(Nationality nationality)
	{
		this.nationality = nationality;
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
