package de.fellmann.judge.competition.data;

import java.util.ArrayList;

public class Event extends DataObject
{
	private ArrayList<Competition> competitions = new ArrayList<Competition>();

	public ArrayList<Competition> getCompetitions()
	{
		return competitions;
	}

	public void setCompetitions(ArrayList<Competition> competitions)
	{
		this.competitions = competitions;
	}
	
}
