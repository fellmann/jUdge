package de.fellmann.judge.competition.data;

import java.util.ArrayList;

public class League extends DataObject
{
	private ArrayList<Competitor> competitors = new ArrayList<Competitor>();
	private ArrayList<Competition> competitions = new ArrayList<Competition>();
	private String name = "";
	
	public ArrayList<Competitor> getCompetitors()
	{
		return competitors;
	}
	public void setCompetitors(ArrayList<Competitor> competitors)
	{
		this.competitors = competitors;
	}
	public ArrayList<Competition> getCompetitions()
	{
		return competitions;
	}
	public void setCompetitions(ArrayList<Competition> competitions)
	{
		this.competitions = competitions;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
}
