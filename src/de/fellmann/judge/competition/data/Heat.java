package de.fellmann.judge.competition.data;

import java.util.ArrayList;

public class Heat extends DataObject
{
	private Dance dance;
	private ArrayList<Heat> competitors = new ArrayList<Heat>();
	
	public Dance getDance()
	{
		return dance;
	}
	public void setDance(Dance dance)
	{
		this.dance = dance;
	}
	public ArrayList<Heat> getCompetitors()
	{
		return competitors;
	}
	public void setCompetitors(ArrayList<Heat> competitors)
	{
		this.competitors = competitors;
	}
}
