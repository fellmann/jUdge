package de.fellmann.judge.competition.result;

import java.util.ArrayList;

import de.fellmann.judge.competition.data.Competitor;

public abstract class RoundResult
{
	private ArrayList<Result> place = new ArrayList<Result>();
	private ArrayList<Competitor> qualified = new ArrayList<Competitor>();
	private ArrayList<Competitor> notQualified = new ArrayList<Competitor>();
	private ArrayList<Competitor> disqualified = new ArrayList<Competitor>();
	private ArrayList<Competitor> participants = new ArrayList<Competitor>();
	
	public ArrayList<Competitor> getQualified()
	{
		return qualified;
	}
	public void setQualified(ArrayList<Competitor> qualified)
	{
		this.qualified = qualified;
	}
	public ArrayList<Competitor> getNotQualified()
	{
		return notQualified;
	}
	public void setNotQualified(ArrayList<Competitor> notQualified)
	{
		this.notQualified = notQualified;
	}
	public ArrayList<Competitor> getDisqualified()
	{
		return disqualified;
	}
	public void setDisqualified(ArrayList<Competitor> disqualified)
	{
		this.disqualified = disqualified;
	}
	public ArrayList<Competitor> getParticipants()
	{
		return participants;
	}
	public void setParticipants(ArrayList<Competitor> participants)
	{
		this.participants = participants;
	}
	public ArrayList<Result> getPlace()
	{
		return place;
	}
	public void setPlace(ArrayList<Result> place)
	{
		this.place = place;
	}
}
