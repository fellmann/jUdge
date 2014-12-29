package de.fellmann.judge.competition.data;

public class Club extends DataObject
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
